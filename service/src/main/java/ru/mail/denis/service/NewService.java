package ru.mail.denis.service;

import ru.mail.denis.service.model.NewsDTO;
import ru.mail.denis.service.model.ViewDTO;

import java.io.File;

/**
 * Created by Denis Monich on 05.07.2017.
 */
public interface NewService {


    ViewDTO viewPage(Integer page);

    NewsDTO getNewById(Integer newsId);

    void deleteNew(Integer newsId);

    void updateNew(NewsDTO newsDTO);

    File findNewsFotoById(Integer id);

    void addNew(NewsDTO newsDTO);

}
