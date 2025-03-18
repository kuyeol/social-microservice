package org.acme.ext.android.model;


import jakarta.annotation.Nullable;
import org.acme.ext.android.entity.NewsEntity;
import org.acme.ext.android.entity.TopicEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

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

    public static NewsEntity toEntity(NewsDto dto, TopicEntity topicEntity) {
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


    public static final class Builder
    {

        private           TopicEntity entity;
        private @Nullable String      entityId;

        public Builder() {

        }

        Builder(TopicEntity entity) {
            this.entity   = entity;
            this.entityId = entity.getId();
        }


        public Builder addTopicEntity(TopicEntity entity) {

            this.entity = entity;

            if (this.entityId == null) {
                this.entityId = entity.getId();
            }

            return this;
        }

        public Builder addTopicId(String id) {

            Objects.requireNonNull(id, "TopicId == null");

            if (this.entityId == null) {
                throw new IllegalArgumentException();
            }

            this.entityId = id;
            return this;

        }


        public NewsDto build() {

            LocalDate localDate = LocalDate.now();

            return new NewsDto(entityId,
                               "topicId",
                               "   title",
                               " author",
                               " content",
                               "  url",
                               " headerImageUrl",
                               localDate.atStartOfDay(),
                               "typ"

            );
        }


    }


}
