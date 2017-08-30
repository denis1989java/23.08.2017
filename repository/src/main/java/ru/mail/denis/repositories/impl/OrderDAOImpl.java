package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.OrderDAO;
import ru.mail.denis.repositories.model.Order;

import javax.persistence.NoResultException;
import java.util.*;

/**
 * Created by Denis Monich on 09.08.2017.
 */
@Repository
public class OrderDAOImpl extends GenericDaoImpl<Order, Integer> implements OrderDAO {
    private static final Logger logger = Logger.getLogger(OrderDAOImpl.class);
    @Autowired
    private Properties properties;


    @Override
    public List<Order> getOrderByUserId(Integer userId) {
        List<Order> orders = new ArrayList<>();
        Query query = getSession().createQuery(properties.getProperty("get.order.by.user.id"));
        query.setParameter("userId", userId);
        try {
            orders = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception getOrderByUserId:"+userId+" no orders");
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByParts(Integer pageId, Integer total) {
        Query query = getSession().createQuery(properties.getProperty("get.orders.by.parts"));
        query.setFirstResult(pageId);
        query.setMaxResults(total);
        List<Order> orders = new ArrayList<>();
        try {
            orders = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception getOrdersByParts: no orders");
        }
        return orders;
    }

    @Override
    public Long getOrdersQuantity(){
        Query query = getSession().createQuery(properties.getProperty("get.orders.quantity"));
        Long quantity= (Long) query.uniqueResult();
        return quantity;
    }
}
