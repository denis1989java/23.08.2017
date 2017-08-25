package ru.mail.denis.repositories;

import ru.mail.denis.repositories.model.OrderBooksTimes;

import java.util.List;

/**
 * Created by user on 09.08.2017.
 */
public interface OrderBooksTimesDAO extends GenericDao<OrderBooksTimes, Integer> {
    List<OrderBooksTimes> getOrderBooksTimesByOrderId(Integer orderId);

    OrderBooksTimes getOrderBooksTimesByOrderIdAndBookId(Integer orderId, Integer bookId);
}
