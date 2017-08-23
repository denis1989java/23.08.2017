package ru.mail.denis.service;

import ru.mail.denis.service.DTOmodels.NewsDTO;

import java.util.List;

/**
 * Created by user on 05.07.2017.
 */
public interface NewService {


    List<NewsDTO> getNews(int pageId, int total);


    NewsDTO getNewById(Integer newsId);

    void deleteNew(Integer newsId);

    void updateNew(NewsDTO newsDTO);

    void addNew(NewsDTO newsDTO);

    Integer newsQuantity();
}
