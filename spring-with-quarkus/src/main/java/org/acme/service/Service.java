package org.acme.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import org.acme.dto.TDTO;
import org.acme.dto.mapper.UserMapper;
import org.acme.entity.User;

import static org.acme.utils.QueryUtil.closing;

@Transactional
@org.springframework.stereotype.Service
public class Service {


    @PersistenceContext
    private EntityManager em;

    public Service(EntityManager em) {
        this.em = em;
    }


    public void create(TDTO dto) {
        User user = UserMapper.toEntity(dto);
        em.persist(user);
    }


    public User find(TDTO dto) {


        TypedQuery<User> query = em.createNamedQuery("findByName", User.class);
        query.setParameter("name", dto.getFirstName());

        List<User> results = query.getResultList();

        User user = results.get(0);

        return user;
    }

    public List<User> findByName(String name) {
        TypedQuery<User> query = em.createNamedQuery("findByName", User.class);
        query.setParameter("name", name);
        return query.getResultList();
    }


    public User findByNameCol(String name) {
        TypedQuery<User> query = em.createNamedQuery("findByName", User.class);
        query.setParameter("name", name);
        List<User> results = query.getResultList();


        User user = results.get(0);

        return user;
    }

    public Stream<TDTO> searchForUserStream() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> queryBuilder = cb.createQuery(User.class);
        Root<User> root = queryBuilder.from(User.class);

        TypedQuery<User> query = em.createNamedQuery("findByName", User.class);
        //TypedQuery<User> query = em.createQuery(queryBuilder);
        String name="string";
        query.setParameter("name", name);




        CriteriaQuery<TDTO> c = cb.createQuery(TDTO.class);



        //TypedQuery<TDTO> q = em.createQuery(c);
        //for (TDTO t : q.getResultList()) {
        //    String id = t.getFirstName();
        //    String fullName = t.getLastName();
        //    System.out.println(id + " " + fullName);
        //}


        //Predicate pt;
        //pt = cb.equal(root.get("firstname"), "string");
        //System.out.println(pt);
        List<User> results = query.getResultList();





     return   closing(results.stream().filter(e->e.getId() != null).map(entity -> new TDTO(null,
            entity.getFirstName(), entity.getLastName(), null)).distinct()).filter(Objects::nonNull);


       //return results.stream().filter(user -> user.getId() != null).map(user -> new TDTO(null, user.getFirstName(),
       //    null, null));

        //return closing(paginateQuery(query, firstResult, maxResults).getResultStream())
        //    // following check verifies that there are no collisions with hashes
        //    .map(userEntity -> root.getModel())
        //    .filter(Objects::nonNull);

    }

    //private List<Predicate> predicates(Map<String, String> attributes, Root<User> root,
    //    Map<String, String> customLongValueSearchAttributes) {
    //    CriteriaBuilder builder = em.getCriteriaBuilder();
    //
    //    List<Predicate> predicates = new ArrayList<>();
    //    List<Predicate> attributePredicates = new ArrayList<>();
    //
    //    Join<Object, Object> federatedIdentitiesJoin = null;
    //
    //    for (Map.Entry<String, String> entry : attributes.entrySet()) {
    //        String key = entry.getKey();
    //        String value = entry.getValue();
    //
    //        if (value == null) {
    //            continue;
    //        }
    //
    //        switch (key) {
    //            case User.SEARCH:
    //                for (String stringToSearch : value.trim().split("\\s+")) {
    //                    predicates.add(builder.or(getSearchOptionPredicateArray(stringToSearch, builder, root)));
    //                }
    //                break;
    //            case FIRST_NAME:
    //            case LAST_NAME:
    //                if (Boolean.parseBoolean(attributes.get(UserModel.EXACT))) {
    //                    predicates.add(builder.equal(builder.lower(root.get(key)), value.toLowerCase()));
    //                } else {
    //                    predicates.add(builder.like(builder.lower(root.get(key)), "%" + value.toLowerCase() + "%"));
    //                }
    //                break;
    //            case USERNAME:
    //            case EMAIL:
    //                if (Boolean.parseBoolean(attributes.get(UserModel.EXACT))) {
    //                    predicates.add(builder.equal(root.get(key), value.toLowerCase()));
    //                } else {
    //                    predicates.add(builder.like(root.get(key), "%" + value.toLowerCase() + "%"));
    //                }
    //                break;
    //            case EMAIL_VERIFIED:
    //                predicates.add(builder.equal(root.get(key), Boolean.valueOf(value.toLowerCase())));
    //                break;
    //            case UserModel.ENABLED:
    //                predicates.add(builder.equal(root.get(key), Boolean.valueOf(value)));
    //                break;
    //            case UserModel.IDP_ALIAS:
    //                if (federatedIdentitiesJoin == null) {
    //                    federatedIdentitiesJoin = root.join("federatedIdentities");
    //                }
    //                predicates.add(builder.equal(federatedIdentitiesJoin.get("identityProvider"), value));
    //                break;
    //            case UserModel.IDP_USER_ID:
    //                if (federatedIdentitiesJoin == null) {
    //                    federatedIdentitiesJoin = root.join("federatedIdentities");
    //                }
    //                predicates.add(builder.equal(federatedIdentitiesJoin.get("userId"), value));
    //                break;
    //            case UserModel.EXACT:
    //                break;
    //            // All unknown attributes will be assumed as custom attributes
    //            default:
    //                //Join<User, UserAttributeEntity> attributesJoin = root.join("attributes", JoinType.LEFT);
    //                if (value.length() > 255) {
    //                    customLongValueSearchAttributes.put(key, value);
    //
    //                } else {
    //                    if (Boolean.parseBoolean(attributes.get(User.EXACT))) {
    //                        attributePredicates.add(builder.and(
    //                            builder.equal(attributesJoin.get("name"), key),
    //                            builder.equal(builder.lower(attributesJoin.get("value")), value.toLowerCase())));
    //                    } else {
    //                        attributePredicates.add(builder.and(
    //                            builder.equal(attributesJoin.get("name"), key),
    //                            builder.like(builder.lower(attributesJoin.get("value")),
    //                                "%" + value.toLowerCase() + "%")));
    //                    }
    //                }
    //                break;
    //            case UserModel.INCLUDE_SERVICE_ACCOUNT: {
    //                if (!attributes.containsKey(UserModel.INCLUDE_SERVICE_ACCOUNT)
    //                    || !Boolean.parseBoolean(attributes.get(UserModel.INCLUDE_SERVICE_ACCOUNT))) {
    //                    predicates.add(root.get("serviceAccountClientLink").isNull());
    //                }
    //                break;
    //            }
    //        }
    //    }
    //
    //    if (!attributePredicates.isEmpty()) {
    //        predicates.add(builder.and(attributePredicates.toArray(Predicate[]::new)));
    //    }
    //
    //    return predicates;
    //}


    //
    //public Venue getVenueById(String id) {
    //    return venueRepository.findById(id);
    //}
    //
    //public Venue registerUser(Dto userDTO, String id) {
    //    venueRepository.findById(userDTO.getSize()).ifPresent(existingUser -> {
    //        if (existingUser == null) {
    //            throw new RuntimeException();
    //        }
    //    });
    //
    //    Venue venue = new Venue();
    //    venue.setId(id);
    //    venue.setVenueName(userDTO.getName());
    //    venue.setSize(userDTO.getSize());
    //
    //
    //    if (userDTO.getName() != null) {
    //        venue.setVenueName(userDTO.getName().toLowerCase());
    //    }
    //    //newUser.setImageUrl(userDTO.getImageUrl());
    //    //newUser.setLangKey(userDTO.getLangKey());
    //    //// new user is not active
    //    //newUser.setActivated(false);
    //    //// new user gets registration key
    //    //newUser.setActivationKey(RandomUtil.generateActivationKey());
    //    //Set<Authority> authorities = new HashSet<>();
    //    //authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
    //    //newUser.setAuthorities(authorities);
    //    venueRepository.save(venue);
    //
    //
    //    return venue;
    //}


}
