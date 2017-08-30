package ru.mail.denis.repositories.model;


import java.io.Serializable;

/**
 * Created by Denis Monich on 25.06.2017.
 */
public class News implements Serializable {

    private static final long serialVersionUID = -981071246893364072L;

    private Integer newsId;
    private String newsName;
    private String newsText;
    private NewFoto foto;
    private String newsDate;


    public News() {
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

    public NewFoto getFoto() {
        return foto;
    }

    public void setFoto(NewFoto foto) {
        this.foto = foto;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsId=" + newsId +
                ", newsName='" + newsName + '\'' +
                ", newsText='" + newsText + '\'' +
                ", foto='" + foto + '\'' +
                ", newsDate='" + newsDate + '\'' +
                '}';
    }
}
