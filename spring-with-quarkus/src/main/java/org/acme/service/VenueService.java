package org.acme.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.acme.mapper.VenueMapper;
import org.acme.mdoel.VenueModel;
@Transactional
@org.springframework.stereotype.Service
public class VenueService {



    @PersistenceContext
    private EntityManager em;

    public VenueService(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void createArtikel(VenueModel model) {
        //Venue entity = new Venue();
        //entity = VenueMapper.toEntity(model);
        var entity = VenueMapper.toEntity(model);
        em.persist(entity);
        VenueMapper.toModel(entity);
    }



}
