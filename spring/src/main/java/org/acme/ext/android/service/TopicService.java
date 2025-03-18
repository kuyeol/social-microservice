package org.acme.ext.android.service;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.acme.ext.android.entity.NewsEntity;
import org.acme.ext.android.entity.TopicEntity;
import org.acme.ext.android.model.NewsDto;
import org.acme.ext.android.model.TopicModel;
import org.hibernate.sql.ast.tree.select.QuerySpec;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

@Component
public class TopicService
{

    private static EntityManager em;

    public TopicService(EntityManager em) {
        TopicService.em = em;
    }

    private static final ConcurrentMap<Object, Class<?>> entityMap = new ConcurrentHashMap<>();


    public final void registerEntity(Class<?> cl) {
        entityMap.put(cl.getSimpleName().toLowerCase(), cl);
    }


    @Transactional
    public TopicModel createTopic(TopicModel topic) {

        TopicEntity entity = topic.toEntity(topic);

        em.persist(entity);
        return new TopicModel(entity);
    }


    @Transactional
    public NewsDto createNews(NewsDto dto) {

        TopicEntity topic = em.find(TopicEntity.class, dto.topicId());

        NewsEntity news = NewsDto.toEntity(dto,topic);

        topic.getNews().add(news);

        em.persist(topic);
        em.flush();
        return NewsDto.toDto(news);

    }

    public Optional<NewsDto> getNews(String id) {
        NewsEntity nn =em.find(NewsEntity.class, id);

      NewsDto dto=  NewsDto.toDto(nn);
        return Optional.of(dto);
    }

    public TopicModel getTopic(String id) {

        TopicEntity entity = em.find(TopicEntity.class, id);

        TopicModel tm = new TopicModel(entity);

        return tm;
    }

    public List<TopicModel> getTopicList() {

      List<TopicModel> list = new ArrayList<>();

      List<TopicEntity> tlist = em.createQuery("from TopicEntity", TopicEntity.class).getResultList();

      for(TopicEntity t: tlist) {
          TopicModel tm = new TopicModel(t);
          list.add(tm);
      }
      return list;
    }


    //todo: dto or model create
}
