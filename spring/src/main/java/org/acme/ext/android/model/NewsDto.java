package org.acme.ext.android.model;


import org.acme.ext.android.entity.NewsEntity;
import org.acme.ext.android.entity.TopicEntity;

import java.time.LocalDateTime;

public record NewsDto(String id,
                      String topicId,
                      String title,
                      String author,
                      String content,
                      String url,
                      String headerImageUrl,
                      LocalDateTime publishDate,
                      String type)
{

    public static NewsDto toDto(NewsEntity entity)
    {
        NewsDto result = new NewsDto(entity.getId(),
                                     entity.getTopic().getId(),
                                     entity.getTitle(),
                                     entity.getAuthors(),
                                     entity.getContent(),
                                     entity.getUrl(),
                                     entity.getHeaderImageUrl(),
                                     entity.getPublishDate(),
                                     entity.getType());
        return result;
    }

    public static NewsEntity toEntity(NewsDto dto,TopicEntity topicEntity) {
        NewsEntity result = new NewsEntity(topicEntity);
        result.setAuthors(dto.author());
        result.setTitle(dto.title());
        result.setContent(dto.content());
        result.setUrl(dto.url());
        result.setHeaderImageUrl(dto.headerImageUrl());
        result.setPublishDate(dto.publishDate());
        result.setType(dto.type());
        return result;
    }


}
