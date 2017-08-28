package ru.mail.denis.service.modelDTO;

import java.io.Serializable;

/**
 * Created by user on 31.07.2017.
 */
public class NewsDTO implements Serializable {
    private static final long serialVersionUID = -1904720902209766948L;
    private Integer newsId;
    private String newsName;
    private String newsText;
    private String newsFoto;
    private String newsDate;

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

    public String getNewsFoto() {
        return newsFoto;
    }

    public void setNewsFoto(String newsFoto) {
        this.newsFoto = newsFoto;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }
}
