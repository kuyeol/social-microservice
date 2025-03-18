package org.acme.ext.android.model;

import org.acme.ext.android.entity.TopicEntity;

public class TopicModel
{
    public  String id;
    public  String name;
    public  String shortDescription;
    public  String longDescription;
    public  String imageUrl;
    public  String url;

public TopicModel(){}

    public TopicModel(TopicEntity entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.shortDescription = entity.getShortDescription();
        this.longDescription = entity.getLongDescription();
        this.imageUrl = entity.getImageUrl();
        this.url = entity.getUrl();

    }
static int count;
    public TopicEntity toEntity( TopicModel model) {
        TopicEntity entity = new TopicEntity(count++);
        entity.setName(model.name);
        entity.setShortDescription(model.shortDescription);
        entity.setLongDescription(model.longDescription);
        entity.setImageUrl(model.imageUrl);
        entity.setUrl(model.url);
        return entity;
    }





}
