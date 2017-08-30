package ru.mail.denis.repositories.model;

import java.io.Serializable;

/**
 * Created by Denis Monich on 29.08.2017.
 */
public class NewFoto implements Serializable {

    private Integer newsId;
    private String fotoName;
    private String fotoLocation;
    private News news;

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getFotoName() {
        return fotoName;
    }

    public void setFotoName(String fotoName) {
        this.fotoName = fotoName;
    }

    public String getFotoLocation() {
        return fotoLocation;
    }

    public void setFotoLocation(String fotoLocation) {
        this.fotoLocation = fotoLocation;
    }
}
