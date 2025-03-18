package org.acme.ext.android.entity;

import java.util.*;

import jakarta.persistence.*;
import org.apache.avro.generic.GenericData;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.repository.cdi.Eager;

import java.util.Collection;
import java.util.LinkedList;

@Entity
public class TopicEntity
{

    @Id
    private String id;
    private String name;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;
    private String url;

    static int count = 0;

    public TopicEntity() {
        this.id = String.valueOf(count++);
    }
public TopicEntity(int  id){
        this.id = String.valueOf(id);
}

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "topic")
    @BatchSize(size = 20)
    private List<NewsEntity> news =new ArrayList<>();


    public List<NewsEntity> getNews() {
        return news;
    }


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

    @Override
    public String toString() {
        return "TopicEntity{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", shortDescription='" + shortDescription + '\'' + ", longDescription='" + longDescription + '\'' + ", imageUrl='" + imageUrl + '\'' + ", url='" + url + '\'' + ", news=" + news + '}';
    }
}
