package ru.mail.denis.repositories.DAO;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.mail.denis.models.Basket;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by user on 08.08.2017.
 */

@Repository
public class BasketDAOImpl extends GenericDaoImpl<Basket, Integer> implements BasketDAO {
    private static final Logger logger = Logger.getLogger(BasketDAOImpl.class);
    ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("query");



    @Override
    public List<Basket> getBasketByUserId(Integer userId) {
        List<Basket> baskets = null;
        String hql=resourceBundle.getString("getBasketByUserId");
        Query query = getSession().createQuery(hql);
        query.setParameter("userId", userId);
        try {
            baskets = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception getBasketByUserId: no basket");
        }
        return baskets;
    }

    @Override
    public Basket getBasketByUserIdAndBookId(Integer userId, Integer bookId) {
        Basket basket = null;
        String hql=resourceBundle.getString("getBasketByUserIdAndBookId");
        Query query = getSession().createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("bookId", bookId);
        try {
            basket = (Basket) query.uniqueResult();
        } catch (NoResultException nre) {
            logger.error("Exception getBasketByUserIdAndBookId: no basket");
        }
        return basket;
    }
}
