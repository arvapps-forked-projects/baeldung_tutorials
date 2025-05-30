package com.baeldung.hibernate.exception.persistentobject.entity;

import jakarta.persistence.*;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne(optional = false)
    private Author author;

    public Article(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
