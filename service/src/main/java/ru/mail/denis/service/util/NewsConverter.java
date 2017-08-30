package ru.mail.denis.service.util;

import ru.mail.denis.repositories.model.News;
import ru.mail.denis.service.model.NewsDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis Monich on 24.08.2017.
 */
public class NewsConverter {


    public static News converter(NewsDTO newsDTO) {
        News news = new News();
        news.setNewsDate(newsDTO.getNewsDate());
        news.setNewsName(newsDTO.getNewsName());
        news.setNewsText(newsDTO.getNewsText());
        news.setNewsId(newsDTO.getNewsId());
        return news;
    }

    public static NewsDTO converter(News news) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setNewsDate(news.getNewsDate());
        newsDTO.setNewsName(news.getNewsName());
        newsDTO.setNewsText(news.getNewsText());
        newsDTO.setNewsId(news.getNewsId());
        return newsDTO;
    }


}
