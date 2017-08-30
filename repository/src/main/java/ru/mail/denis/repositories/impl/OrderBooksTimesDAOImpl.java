package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.OrderBooksTimesDAO;
import ru.mail.denis.repositories.model.OrderBooksTimes;

import javax.persistence.NoResultException;
import java.util.*;

/**
 * Created by Denis Monich on 09.08.2017.
 */

@Repository
public class OrderBooksTimesDAOImpl extends GenericDaoImpl<OrderBooksTimes, Integer> implements OrderBooksTimesDAO {
    private static final Logger logger = Logger.getLogger(OrderBooksTimesDAOImpl.class);
    @Autowired
    private Properties properties;


    @Override
    public List<OrderBooksTimes> getOrderBooksTimesByOrderId(Integer orderId) {
        List<OrderBooksTimes> orderBooksTimes = new ArrayList<>();
        Query query = getSession().createQuery(properties.getProperty("get.orderBooksTimes.by.order.id"));
        query.setParameter("orderId", orderId);
        try {
            orderBooksTimes = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception getOrderBooksTimesByOrderId:"+orderId+" no orderBooksTimes");
        }
        return orderBooksTimes;
    }

    @Override
    public OrderBooksTimes getOrderBooksTimesByOrderIdAndBookId(Integer orderId, Integer bookId) {
        OrderBooksTimes orderBooksTimes = null;
        Query query = getSession().createQuery(properties.getProperty("get.orderBooksTimes.by.order.id.and.book.id"));
        query.setParameter("orderId", orderId);
        query.setParameter("bookId", bookId);
        try {
            orderBooksTimes = (OrderBooksTimes) query.uniqueResult();
        } catch (NoResultException nre) {
            logger.error("Exception findByr getOrderBooksTimesByOrderIdAndBookId:"+orderId+" AND "+bookId+" no orderBooksTime");
        }
        return orderBooksTimes;
    }
}
