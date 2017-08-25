package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.OrdersBooksDAO;
import ru.mail.denis.repositories.model.OrdersBooks;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by user on 09.08.2017.
 */

@Repository
public class OrdersBooksDAOImpl extends GenericDaoImpl<OrdersBooks, Integer> implements OrdersBooksDAO {
    private static final Logger logger = Logger.getLogger(OrdersBooksDAOImpl.class);
    ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("query");

    @Override
    public List<OrdersBooks> getOrdersBooksByOrderId(Integer orderId) {
        List<OrdersBooks> ordersBooks = null;
        String hql=resourceBundle.getString("getOrdersBooksByOrderId");
        Query query = getSession().createQuery(hql);
        query.setParameter("orderId", orderId);
        try {
            ordersBooks = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception getOrdersBooksByOrderId: no ordersBooks");
        }
        return ordersBooks;
    }

    @Override
    public List<OrdersBooks> getOrdersBooksByBookId(Integer bookId) {
        List<OrdersBooks> ordersBooks = null;
        String hql=resourceBundle.getString("getOrdersBooksByBookId");
        Query query = getSession().createQuery(hql);
        query.setParameter("bookId", bookId);
        try {
            ordersBooks = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception getOrdersBooksByBookId: no ordersBooks");
        }
        return ordersBooks;
    }
}
