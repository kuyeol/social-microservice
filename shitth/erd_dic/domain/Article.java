package com.packt.cantata.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Article {

    @Id
    @GeneratedValue
    private int id;

    private String title;
    private String author;
    private String content;

    public Article() {
        super();
    }





}
