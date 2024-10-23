package org.acme.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
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
import org.acme.dto.UserDto;
import org.acme.dto.UserModel;
import org.acme.dto.mapper.UserMapper;
import org.acme.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import static org.acme.utils.PaginationUtils.paginateQuery;
import static org.acme.utils.QueryUtil.closing;

@Transactional
@org.springframework.stereotype.Service
public class Service {

    private static final String EMAIL = "email";
    private static final String EMAIL_VERIFIED = "emailVerified";
    private static final String USERNAME = "username";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final char ESCAPE_BACKSLASH = '\\';

    @PersistenceContext
    private EntityManager em;




    public Service(EntityManager em) {
        this.em = em;


    }


    public void create(TDTO dto) {
        User user = UserMapper.toEntity(dto);
        em.persist(user);
    }


    public UserDto projectionEntity(String id) throws Exception {

        User user = em.find(User.class, id);

        return UserMapper.toUserDt(user);
    }



    public List<UserDto> getAllEntities() throws Exception {
        // JPQL 쿼리 작성: YourEntity는 조회하려는 엔터티 클래스


        String jpql = "SELECT e FROM User e";

        TypedQuery<User> query = em.createQuery(jpql, User.class);

        List<UserDto> dtos = new ArrayList<>();

        for (User user : query.getResultList()) {
            if (user.getFirstName()==null) return null;
            if (user.getFirstName()!=null && user.getLastName()!=null) {

                dtos.add(UserMapper.toUserDt(user));
            }

        }

        return dtos;
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

    public TDTO getUserById(TDTO realm, String id) {
        User userEntity = em.find(User.class, id);
        if (userEntity == null || !realm.getId().equals(userEntity.getId())) {
            return null;
        }

        return new TDTO(id, realm.getFirstName(), realm.getFirstName(), "");
    }

    public Stream<TDTO> searchForUserStream(TDTO realm, Map<String, String> params, Integer firstResult,
        Integer maxResults) {
        return Stream.empty();
    }


    public Stream<TDTO> searchForUserStream(TDTO model, Integer firstResult, Integer maxResults) {


        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> queryBuilder = builder.createQuery(User.class);
        Root<User> root = queryBuilder.from(User.class);
        CriteriaQuery<TDTO> criteria = builder.createQuery(TDTO.class);
        Root<User> roo = criteria.from(User.class);


        Map<String, String> searchAttributes = new HashMap<>();
        searchAttributes.put(FIRST_NAME, model.getFirstName());
        List<Predicate> predicates = predicates(searchAttributes, root);

        predicates.add(builder.equal(root.get("Id"), model.getId()));
        queryBuilder.where(predicates.toArray(Predicate[]::new)).orderBy(builder.asc(root.get(TDTO.FIRST_NAME)));
        TypedQuery<User> query = em.createQuery(queryBuilder);

        return closing(paginateQuery(query, firstResult, maxResults).getResultStream()).map(
            userEntity -> getUserById(model, userEntity.getId())).filter(Objects::nonNull);

    }


    //TODO: DDD
    //@GET
    //@Path("author/search")
    //@Transactional
    //public List<Author> searchAuthors(@RestQuery String pattern,
    //    @RestQuery Optional<Integer> size) {
    //    return searchSession.search(Author.class)
    //        .where(f ->
    //            pattern == null || pattern.trim().isEmpty() ?
    //                f.matchAll() :
    //                f.simpleQueryString()
    //                    .fields("firstName", "lastName", "books.title").matching(pattern)
    //        )
    //        .sort(f -> f.field("lastName_sort").then().field("firstName_sort"))
    //        .fetchHits(size.orElse(20));
    //}

    private static final String LOCALE = "locale";
    private static final String ENABLED = "enabled";
    private static final String IDP_ALIAS = "keycloak.session.realm.users.query.idp_alias";
    private static final String IDP_USER_ID = "keycloak.session.realm.users.query.idp_user_id";
    private static final String INCLUDE_SERVICE_ACCOUNT = "keycloak.session.realm.users.query.include_service_account";
    private static final String GROUPS = "keycloak.session.realm.users.query.groups";
    private static final String SEARCH = "keycloak.session.realm.users.query.search";
    private static final String EXACT = "keycloak.session.realm.users.query.exact";
    private static final String DISABLED_REASON = "disabledReason";

    static enum RequiredAction {
        VERIFY_EMAIL, UPDATE_PROFILE, CONFIGURE_TOTP, CONFIGURE_RECOVERY_AUTHN_CODES, UPDATE_PASSWORD,
        TERMS_AND_CONDITIONS, VERIFY_PROFILE, UPDATE_EMAIL
    }

    private List<Predicate> predicates(Map<String, String> attributes, Root<User> root) {
        CriteriaBuilder builder = em.getCriteriaBuilder();

        List<Predicate> predicates = new ArrayList<>();


        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value == null) {
                continue;
            }

            switch (key) {

                case FIRST_NAME:
                case LAST_NAME:
                    if (Boolean.parseBoolean(attributes.get(TDTO.EXACT))) {
                        predicates.add(builder.equal(builder.lower(root.get(key)), value.toLowerCase()));
                    } else {
                        predicates.add(builder.like(builder.lower(root.get(key)), "%" + value.toLowerCase() + "%"));
                    }
                    break;
                case USERNAME:
                case EMAIL:
                    if (Boolean.parseBoolean(attributes.get(TDTO.EXACT))) {
                        predicates.add(builder.equal(root.get(key), value.toLowerCase()));
                    } else {
                        predicates.add(builder.like(root.get(key), "%" + value.toLowerCase() + "%"));
                    }
                    break;
                case EMAIL_VERIFIED:
                    predicates.add(builder.equal(root.get(key), Boolean.valueOf(value.toLowerCase())));
                    break;
                case UserModel.ENABLED:
                    predicates.add(builder.equal(root.get(key), Boolean.valueOf(value)));
                    break;

                case UserModel.EXACT:
                    break;
                // All unknown attributes will be assumed as custom attributes

                case UserModel.INCLUDE_SERVICE_ACCOUNT: {
                    if (!attributes.containsKey(UserModel.INCLUDE_SERVICE_ACCOUNT) ||
                        !Boolean.parseBoolean(attributes.get(UserModel.INCLUDE_SERVICE_ACCOUNT))) {
                        predicates.add(root.get("serviceAccountClientLink").isNull());
                    }
                    break;
                }
            }
        }
        return predicates;
    }


    private Predicate[] getSearchOptionPredicateArray(String value, CriteriaBuilder builder, From<?, User> from) {
        value = value.toLowerCase();

        List<Predicate> orPredicates = new ArrayList<>();

        if (value.length() >= 2 && value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') {
            // exact search
            value = value.substring(1, value.length() - 1);

            orPredicates.add(builder.equal(from.get(USERNAME), value));
            orPredicates.add(builder.equal(from.get(EMAIL), value));
            orPredicates.add(builder.equal(builder.lower(from.get(FIRST_NAME)), value));
            orPredicates.add(builder.equal(builder.lower(from.get(LAST_NAME)), value));
        } else {
            value = value.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
            value = value.replace("*", "%");
            if (value.isEmpty() || value.charAt(value.length() - 1) != '%') {
                value += "%";
            }

            orPredicates.add(builder.like(from.get(USERNAME), value, ESCAPE_BACKSLASH));
            orPredicates.add(builder.like(from.get(EMAIL), value, ESCAPE_BACKSLASH));
            orPredicates.add(builder.like(builder.lower(from.get(FIRST_NAME)), value, ESCAPE_BACKSLASH));
            orPredicates.add(builder.like(builder.lower(from.get(LAST_NAME)), value, ESCAPE_BACKSLASH));
        }

        return orPredicates.toArray(Predicate[]::new);
    }


    private User userInEntityManagerContext(String id) {
        User user = em.getReference(User.class, id);
        return em.contains(user) ? user : null;
    }


}
