package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.NewsDAO;
import ru.mail.denis.repositories.model.News;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by user on 08.08.2017.
 */

@Repository
public class NewsDAOImpl extends GenericDaoImpl<News, Integer> implements NewsDAO {
    private static final Logger logger = Logger.getLogger(NewsDAOImpl.class);
    ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("query");


    @Override
    public List<News> getNewsByParts(Integer pageId, Integer total) {
        String hql=resourceBundle.getString("getNewsByParts");
        Query query = getSession().createQuery(hql);
        query.setFirstResult(pageId);
        query.setMaxResults(total);
        List<News> news = null;
        try {
            news = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception findByr getNewsByParts: no news");
        }
        return news;
    }
}
