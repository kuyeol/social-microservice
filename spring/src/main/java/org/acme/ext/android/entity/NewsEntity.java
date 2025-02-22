package org.acme.ext.android.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NewsEntity {


    @Id
    private String        id;
    private String        title;
    private String        content;
    private String        url;
    private String        headerImageUrl;
    private LocalDateTime publishDate;
    private String        type;


    @ManyToOne(fetch = FetchType.LAZY)
    protected TopicEntity topic;

    public TopicEntity getTopic() {
        return topic;
    }

    public void setTopic(TopicEntity topicId) {
        this.topic = topicId;
    }

    private String authors;

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setHeaderImageUrl(String headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
