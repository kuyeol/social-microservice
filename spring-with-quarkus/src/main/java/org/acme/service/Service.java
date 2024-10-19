package org.acme.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.acme.dto.TDTO;
import org.acme.dto.mapper.UserMapper;
import org.acme.entity.User;

import static jakarta.persistence.FlushModeType.COMMIT;

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


    public List<User> findBook(TDTO dto) {
        var ul = em.createQuery("where firstName like :firstName type = :lastname", User.class)
            .setParameter("title", dto.getFirstName())
            .setParameter("type", dto.getLastName())
            .setFlushMode(COMMIT)
            .setMaxResults(100)
            .getResultList();

        return ul;
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
