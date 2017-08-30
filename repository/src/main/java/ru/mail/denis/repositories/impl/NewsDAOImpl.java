package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.NewsDAO;
import ru.mail.denis.repositories.model.News;

import javax.persistence.NoResultException;
import java.util.*;

/**
 * Created by Denis Monich on 08.08.2017.
 */

@Repository
public class NewsDAOImpl extends GenericDaoImpl<News, Integer> implements NewsDAO {
    private static final Logger logger = Logger.getLogger(NewsDAOImpl.class);

    @Autowired
    private Properties properties;

    @Override
    public List<News> getNewsByParts(Integer pageId, Integer total) {
        Query query = getSession().createQuery(properties.getProperty("get.news.by.parts"));
        query.setFirstResult(pageId);
        query.setMaxResults(total);
        List<News> news = new ArrayList<>();
        try {
            news = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception findByr getNewsByParts: no news");
        }
        return news;
    }
    @Override
    public Long getNewsQuantity(){
        Query query = getSession().createQuery(properties.getProperty("get.news.quantity"));
        Long quantity= (Long) query.uniqueResult();
        return quantity;
    }
}
