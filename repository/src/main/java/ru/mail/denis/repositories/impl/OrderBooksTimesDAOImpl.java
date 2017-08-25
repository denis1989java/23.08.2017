package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.OrderBooksTimesDAO;
import ru.mail.denis.repositories.model.OrderBooksTimes;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by user on 09.08.2017.
 */

@Repository
public class OrderBooksTimesDAOImpl extends GenericDaoImpl<OrderBooksTimes, Integer> implements OrderBooksTimesDAO {
    private static final Logger logger = Logger.getLogger(OrderBooksTimesDAOImpl.class);
    ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("query");


    @Override
    public List<OrderBooksTimes> getOrderBooksTimesByOrderId(Integer orderId) {
        List<OrderBooksTimes> orderBooksTimes = null;
        String hql=resourceBundle.getString("getOrderBooksTimesByOrderId");
        Query query = getSession().createQuery(hql);
        query.setParameter("orderId", orderId);
        try {
            orderBooksTimes = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception getOrderBooksTimesByOrderId: no orderBooksTimes");
        }
        return orderBooksTimes;
    }

    @Override
    public OrderBooksTimes getOrderBooksTimesByOrderIdAndBookId(Integer orderId, Integer bookId) {
        OrderBooksTimes orderBooksTimes = null;
        String hql=resourceBundle.getString("getOrderBooksTimesByOrderIdAndBookId");
        Query query = getSession().createQuery(hql);
        query.setParameter("orderId", orderId);
        query.setParameter("bookId", bookId);
        try {
            orderBooksTimes = (OrderBooksTimes) query.uniqueResult();
        } catch (NoResultException nre) {
            logger.error("Exception findByr getOrderBooksTimesByOrderIdAndBookId: no orderBooksTime");
        }
        return orderBooksTimes;
    }
}
