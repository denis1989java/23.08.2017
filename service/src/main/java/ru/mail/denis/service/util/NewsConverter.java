package ru.mail.denis.service.util;

import ru.mail.denis.repositories.model.News;
import ru.mail.denis.service.DTOmodels.NewsDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 24.08.2017.
 */
public class NewsConverter {
    public NewsConverter() {
    }

    public static News converter(NewsDTO newsDTO) {
        News news = new News();
        news.setNewsDate(newsDTO.getNewsDate());
        news.setNewsName(newsDTO.getNewsName());
        news.setNewsText(newsDTO.getNewsText());
        news.setNewsId(newsDTO.getNewsId());
        news.setNewsFoto(newsDTO.getNewsFoto());
        return news;
    }

    public static NewsDTO converter(News news) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setNewsDate(news.getNewsDate());
        newsDTO.setNewsName(news.getNewsName());
        newsDTO.setNewsText(news.getNewsText());
        newsDTO.setNewsFoto(news.getNewsFoto());
        newsDTO.setNewsId(news.getNewsId());
        return newsDTO;
    }

    public static List<NewsDTO> converter(List<News>news){
        List<NewsDTO> newsDTOS = new ArrayList<>();
        for (News new1 : news) {
            NewsDTO newsDTO = NewsConverter.converter(new1);
            newsDTOS.add(newsDTO);
        }
        return newsDTOS;
    }
}
