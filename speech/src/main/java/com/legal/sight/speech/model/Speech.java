package com.legal.sight.speech.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "speeches")
public class Speech {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String author;
    private String keywords;
    private LocalDate date;

    // Constructors
    public Speech() {
    }

    public Speech(String text, String author, String keywords, LocalDate date) {
        this.text = text;
        this.author = author;
        this.keywords = keywords;
        this.date = date;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
