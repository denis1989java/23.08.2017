package ru.mail.denis.repositories;

import ru.mail.denis.repositories.model.News;

import java.util.List;

/**
 * Created by user on 08.08.2017.
 */
public interface NewsDAO extends GenericDao<News, Integer> {

    List<News> getNewsByParts(Integer pageId, Integer total);
}
