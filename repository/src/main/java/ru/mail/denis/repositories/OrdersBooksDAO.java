package ru.mail.denis.repositories;

import ru.mail.denis.repositories.model.OrdersBooks;

import java.util.List;

/**
 * Created by user on 09.08.2017.
 */
public interface OrdersBooksDAO extends GenericDao<OrdersBooks, Integer> {
    List<OrdersBooks> getOrdersBooksByOrderId(Integer orderId);

    List<OrdersBooks> getOrdersBooksByBookId(Integer bookId);

    List<OrdersBooks> getOrdersBooksByUserId(Integer userId);
}
