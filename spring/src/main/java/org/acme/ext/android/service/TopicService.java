package org.acme.ext.android.service;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.acme.core.database.DataAccess;
import org.acme.ext.android.entity.NewsEntity;
import org.acme.ext.android.entity.TopicEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class TopicService
{

    private static EntityManager em;

    public TopicService(EntityManager em) {
        this.em = em;
    }

    private static final ConcurrentMap<Object, Class<?>> entityMap = new ConcurrentHashMap<>();


    public final void registerEntity(Class<?> cl) {
        entityMap.put(cl.getSimpleName().toLowerCase(), cl);
    }


    @Transactional
    public void createTopic(TopicEntity topic) {

        em.persist(topic);

    }

    @Transactional
    public void createNews(String id) {

        TopicEntity topic = em.find(TopicEntity.class, id);

        NewsEntity news = new NewsEntity(topic);

        news.setContent("asdfsadf");

        topic.getNews().add(news);

        em.persist(topic);

    }


    @Transactional
    public NewsEntity getNews(String id) {

        NewsEntity news = em.find(NewsEntity.class, id);

        return news;
    }


}
