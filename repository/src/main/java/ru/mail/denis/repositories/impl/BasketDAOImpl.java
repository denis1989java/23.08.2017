package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.BasketDAO;
import ru.mail.denis.repositories.model.Basket;

import javax.persistence.NoResultException;
import java.util.*;

/**
 * Created by Denis Monich on 08.08.2017.
 */

@Repository
public class BasketDAOImpl extends GenericDaoImpl<Basket, Integer> implements BasketDAO {
    private static final Logger logger = Logger.getLogger(BasketDAOImpl.class);

    @Autowired
    private Properties properties;


    @Override
    public List<Basket> getBasketByUserId(Integer userId) {
        List<Basket> baskets = new ArrayList<>();

        Query query = getSession().createQuery(properties.getProperty("get.basket.by.user.id"));
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
        Query query = getSession().createQuery(properties.getProperty("get.basket.by.user.id.and.book.id"));
        query.setParameter("userId", userId);
        query.setParameter("bookId", bookId);
        try {
            basket = (Basket) query.uniqueResult();
        } catch (NoResultException nre) {
            logger.error("Exception getBasketByUserIdAndBookId:"+userId+" AND "+bookId+" no basket");
        }
        return basket;
    }
}
