package ru.mail.denis.repositories;

import ru.mail.denis.repositories.model.Order;

import java.util.List;

/**
 * Created by user on 09.08.2017.
 */
public interface OrderDAO extends GenericDao<Order, Integer> {
    List<Order> getOrderByUserId(Integer userId);

    List<Order> getOrdersByParts(Integer pageId, Integer total);

}
