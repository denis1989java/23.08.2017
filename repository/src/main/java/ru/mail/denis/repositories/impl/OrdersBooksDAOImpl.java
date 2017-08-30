package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.OrdersBooksDAO;
import ru.mail.denis.repositories.model.OrdersBooks;

import javax.persistence.NoResultException;
import java.util.*;

/**
 * Created by Denis Monich on 09.08.2017.
 */

@Repository
public class OrdersBooksDAOImpl extends GenericDaoImpl<OrdersBooks, Integer> implements OrdersBooksDAO {
    private static final Logger logger = Logger.getLogger(OrdersBooksDAOImpl.class);
    @Autowired
    private Properties properties;
    @Override
    public List<OrdersBooks> getOrdersBooksByOrderId(Integer orderId) {
        List<OrdersBooks> ordersBooks = new ArrayList<>();
        Query query = getSession().createQuery(properties.getProperty("get.ordersBooks.by.order.id"));
        query.setParameter("orderId", orderId);
        try {
            ordersBooks = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception getOrdersBooksByOrderId:"+orderId+" no ordersBooks");
        }
        return ordersBooks;
    }

    @Override
    public List<OrdersBooks> getOrdersBooksByBookId(Integer bookId) {
        List<OrdersBooks> ordersBooks = new ArrayList<>();
        Query query = getSession().createQuery(properties.getProperty("get.ordersBooks.by.book.id"));
        query.setParameter("bookId", bookId);
        try {
            ordersBooks = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception getOrdersBooksByBookId:"+bookId+" no ordersBooks");
        }
        return ordersBooks;
    }
    @Override
    public List<OrdersBooks> getOrdersBooksByUserId(Integer userId){
        List<OrdersBooks> ordersBooks = new ArrayList<>();
        Query query = getSession().createQuery(properties.getProperty("get.ordersBooks.by.user.id"));
        query.setParameter("userId", userId);
        try {
            ordersBooks = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception getOrdersBooksByUserkId:"+userId+" no ordersBooks");
        }
        return ordersBooks;
    }
}
