package org.acme.ext.android.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.acme.core.database.DataAccess;
import org.acme.ext.android.entity.NewsEntity;
import org.acme.ext.android.entity.TopicEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class TopicService extends DataAccess {

    private static final ConcurrentMap<Object, Class<?>> entityMap = new ConcurrentHashMap<>();

    protected TopicService(EntityManager em) {
        super(em);
        this.em = em;
        init();
    }

    public final void registerEntity(Class<?> cl) {
        entityMap.put(cl.getSimpleName().toLowerCase(), cl);
    }

    final void init() {
        registerEntity(TopicEntity.class);
        registerEntity(NewsEntity.class);

    }

    public void createTopic(TopicEntity topic) {

        save(topic);
    }

    public void createNews(NewsEntity news) {
        news.setTopic(news.getTopic());

        save(news);
    }


    private static EntityManager em;


    public Optional<List> getAllNews(String id) {
        setClazz(NewsEntity.class);
        return Optional.ofNullable(listAll());

    }


    public List<TopicEntity> getAllTopics() {
        setClazz(TopicEntity.class);

        return listAll();
    }

}
