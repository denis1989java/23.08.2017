package ru.mail.denis.repositories.DAO;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.mail.denis.models.Book;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by user on 09.08.2017.
 */

@Repository
public class CatalogueDAOImpl extends GenericDaoImpl<Book, Integer> implements CatalogueDAO {
    private static final Logger logger = Logger.getLogger(CatalogueDAOImpl.class);
    ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("query");


    @Override
    public List<Book> getCatalogueByParts(Integer pageId, Integer total) {
        String hql=resourceBundle.getString("getCatalogueByParts");
        Query query = getSession().createQuery(hql);
        query.setFirstResult(pageId);
        query.setMaxResults(total);
        List<Book> books = null;
        try {
            books = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception findByr getCatalogueByParts: no books");
        }
        return books;
    }
}
