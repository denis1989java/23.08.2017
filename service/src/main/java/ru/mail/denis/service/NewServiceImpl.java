package ru.mail.denis.service;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.models.News;
import ru.mail.denis.repositories.DAO.NewsDAO;
import ru.mail.denis.repositories.DAO.NewsDAOImpl;
import ru.mail.denis.service.DTOmodels.NewsDTO;

import java.util.ArrayList;
import java.util.List;

import static ru.mail.denis.repositories.HibernateUtil.sessionFactory;

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
        List<News> news = getNewsByParts(pageId, total);
        List<NewsDTO> newsDTOS = new ArrayList<>();
        for (News new1 : news) {
            NewsDTO newsDTO = newsToNewsDTO(new1);
            newsDTOS.add(newsDTO);
        }
        return newsDTOS;
    }

    @Override
    @Transactional
    public NewsDTO getNewById(Integer newsId) {
        NewsDTO newsDTO = newsToNewsDTO(findById(newsId));
        return newsDTO;
    }

    @Override
    @Transactional
    public void deleteNew(Integer newsId) {
        News news = findById(newsId);
        delete(news);
    }

    @Override
    @Transactional
    public void updateNew(NewsDTO newsDTO) {
        updateNews(newsDTOToNews(newsDTO));
    }

    @Override
    @Transactional
    public void addNew(NewsDTO newsDTO) {
        saveNews(newsDTOToNews(newsDTO));
    }

    @Override
    @Transactional
    public Integer newsQuantity() {
        Integer quantity = findAll().size();
        return quantity;
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
        System.out.println(news.getNewsId()+news.getNewsDate()+news.getNewsName()+news.getNewsText()+"QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
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

    private News newsDTOToNews(NewsDTO newsDTO) {
        News news = new News();
        news.setNewsDate(newsDTO.getNewsDate());
        news.setNewsName(newsDTO.getNewsName());
        news.setNewsText(newsDTO.getNewsText());
        news.setNewsId(newsDTO.getNewsId());
        news.setNewsFoto(newsDTO.getNewsFoto());
        return news;
    }

    private NewsDTO newsToNewsDTO(News news) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setNewsDate(news.getNewsDate());
        newsDTO.setNewsName(news.getNewsName());
        newsDTO.setNewsText(news.getNewsText());
        newsDTO.setNewsFoto(news.getNewsFoto());
        newsDTO.setNewsId(news.getNewsId());
        return newsDTO;
    }
}
