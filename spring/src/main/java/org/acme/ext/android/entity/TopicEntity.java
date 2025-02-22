package org.acme.ext.android.entity;

import java.util.*;
import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.repository.cdi.Eager;

import java.util.Collection;
import java.util.LinkedList;

@Entity
public class TopicEntity {

    @Id
    private String id;

    private String name;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;
    private String url;


    public Set<NewsEntity> getNews() {
        return news;
    }

    public void setNews(Set<NewsEntity> news) {
        this.news = news;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy ="topic")
    @BatchSize(size = 20)
    private Set<NewsEntity> news;

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
