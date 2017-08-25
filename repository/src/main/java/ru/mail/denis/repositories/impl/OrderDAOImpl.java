package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.OrderDAO;
import ru.mail.denis.repositories.model.Order;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by user on 09.08.2017.
 */
@Repository
public class OrderDAOImpl extends GenericDaoImpl<Order, Integer> implements OrderDAO {
    private static final Logger logger = Logger.getLogger(OrderDAOImpl.class);
    ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("query");


    @Override
    public List<Order> getOrderByUserId(Integer userId) {
        List<Order> orders = null;
        String hql=resourceBundle.getString("getOrderByUserId");
        Query query = getSession().createQuery(hql);
        query.setParameter("userId", userId);
        try {
            orders = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception getOrderByUserId: no orders");
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByParts(Integer pageId, Integer total) {
        String hql=resourceBundle.getString("getOrdersByParts");
        Query query = getSession().createQuery(hql);
        query.setFirstResult(pageId);
        query.setMaxResults(total);
        List<Order> orders = null;
        try {
            orders = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception getOrdersByParts: no orders");
        }
        return orders;
    }
}
