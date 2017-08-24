package ru.mail.denis.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.models.News;
import ru.mail.denis.repositories.DAO.NewsDAO;
import ru.mail.denis.service.DTOmodels.NewsDTO;
import ru.mail.denis.service.util.NewsConverter;

import java.util.List;


/**
 * Created by user on 25.06.2017.
 */
@Service
public class NewServiceImpl implements NewService {

    private final NewsDAO newsDAO;
    private static final Logger logger = Logger.getLogger(NewServiceImpl.class);

    @Autowired
    public NewServiceImpl(NewsDAO newsDAO) {
        this.newsDAO = newsDAO;
    }

    @Override
    @Transactional
    public List<NewsDTO> getNews(int pageId, int total) {
        return NewsConverter.converter(getNewsByParts(pageId, total));
    }

    @Override
    @Transactional
    public NewsDTO getNewById(Integer newsId) {
        return NewsConverter.converter(findById(newsId));
    }

    @Override
    @Transactional
    public void deleteNew(Integer newsId) {
        delete(findById(newsId));
    }

    @Override
    @Transactional
    public void updateNew(NewsDTO newsDTO) {
        updateNews(NewsConverter.converter(newsDTO));
    }

    @Override
    @Transactional
    public void addNew(NewsDTO newsDTO) {
        saveNews(NewsConverter.converter(newsDTO));
    }

    @Override
    @Transactional
    public Integer newsQuantity() {
        return findAll().size();
    }


    private List<News> findAll() {
        logger.debug("Finding all news");
        List<News> news = newsDAO.findAll();
        return news;
    }

    private void delete(News news) {

        logger.debug("Deleting new");
        newsDAO.delete(news);
    }

    private void saveNews(News news) {

        logger.debug("Saving new");
        newsDAO.save(news);
    }

    private void updateNews(News news) {
        logger.debug("Updating new");
        newsDAO.update(news);
    }

    private News findById(Integer id) {

        logger.debug("Finding new by id");
        News news = newsDAO.findById(id);
        return news;
    }

    private List<News> getNewsByParts(Integer pageId, Integer total) {

        logger.debug("Getting news by parts");
        List<News> news = newsDAO.getNewsByParts(pageId, total);
        return news;
    }
}
