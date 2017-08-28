package ru.mail.denis.service;

import ru.mail.denis.service.modelDTO.NewsDTO;
import ru.mail.denis.service.modelDTO.ViewDTO;

/**
 * Created by user on 05.07.2017.
 */
public interface NewService {


    ViewDTO viewPage(Integer page);

    NewsDTO getNewById(Integer newsId);

    void deleteNew(Integer newsId);

    void updateNew(NewsDTO newsDTO);

    void addNew(NewsDTO newsDTO);

}
