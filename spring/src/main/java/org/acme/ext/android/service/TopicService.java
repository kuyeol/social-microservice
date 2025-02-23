package org.acme.ext.android.service;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.acme.ext.android.entity.NewsEntity;
import org.acme.ext.android.entity.TopicEntity;
import org.acme.ext.android.model.NewsDto;
import org.acme.ext.android.model.TopicModel;
import org.springframework.stereotype.Component;

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
    public void createTopic(TopicModel topic) {


        // TopicModel  newTopic = new TopicModel();
        //   TopicEntity entity   = newTopic.toEntity(topic);
        TopicEntity entity = topic.toEntity(topic);


        em.persist(entity);

    }


    @Transactional
    public void createNews(NewsDto dto) {

        TopicEntity topic = em.find(TopicEntity.class, dto.topicId());


        NewsEntity news = NewsDto.toEntity(dto,topic);

        topic.getNews().add(news);

        em.persist(topic);

    }

    public Optional<NewsDto> getNews(String id) {
        NewsEntity nn = em.find(NewsEntity.class, id);


        NewsDto.toDto(nn);
        return Optional.of(NewsDto.toDto(nn));
    }

    public TopicModel getTopic(String id) {


        TopicEntity entity = em.find(TopicEntity.class, id);

        TopicModel tm = new TopicModel(entity);

        return tm;
    }


    //todo: dto or model create
}
