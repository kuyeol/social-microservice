package org.acme.ext.android.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NewsEntity
{

    @Id
    private String id;

    private String        title;
    private String        content;
    private String        url;
    private String        headerImageUrl;
    private LocalDateTime publishDate;
    private String        type;
    private String        authors;
    static  int           count = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private TopicEntity topic;



    public NewsEntity(TopicEntity topic) {
        this.topic = topic;
        if (topic.getId().equals("-1")) {
              this.id    = "0";
        }else {
            this.id    = String.valueOf(++count);
        }
    }

    public NewsEntity() {
this.id="0";
    }


    public String getId() {
        return id;
    }

    public TopicEntity getTopic() {
        return topic;
    }


    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
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


    @Override
    public String toString() {
        return "NewsEntity{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", content='" + content + '\'' + ", url='" + url + '\'' + ", headerImageUrl='" + headerImageUrl + '\'' + ", publishDate=" + publishDate + ", type='" + type + '\'' + ", topic=" + topic + ", authors='" + authors + '\'' + '}';
    }
}
