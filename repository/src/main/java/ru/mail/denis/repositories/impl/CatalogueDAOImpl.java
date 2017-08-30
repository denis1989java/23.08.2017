package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.CatalogueDAO;
import ru.mail.denis.repositories.model.Book;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Denis Monich on 09.08.2017.
 */

@Repository
public class CatalogueDAOImpl extends GenericDaoImpl<Book, Integer> implements CatalogueDAO {
    private static final Logger logger = Logger.getLogger(CatalogueDAOImpl.class);

    @Autowired
    private Properties properties;

    @Override
    public List<Book> getCatalogueByParts(Integer pageId, Integer total) {
        Query query = getSession().createQuery(properties.getProperty("get.catalogue.by.parts"));
        query.setFirstResult(pageId);
        query.setMaxResults(total);
        List<Book> books = new ArrayList<>();
        try {
            books = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception findByr getCatalogueByParts: no books");
        }
        return books;
    }

    @Override
    public Long getCatalogueQuantity(){
        Query query = getSession().createQuery(properties.getProperty("get.catalogue.quantity"));
        Long quantity= (Long) query.uniqueResult();
        return quantity;
    }
}
