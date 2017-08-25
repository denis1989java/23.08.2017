package ru.mail.denis.service;

import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.service.DTOmodels.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by user on 07.07.2017.
 */
public interface Orderservice {

    void addOrder(String userEmail, BigDecimal fullPrice);

    List<OrderDTO> getOrdersByUser(String userEmail);

    List<OrderDTO> getOrders(int pageId, int total);

    void changeReceiveStatus(Integer orderId);

    OrderDTO getOrderById(Integer orderId);

    List<OrderBookTimesDTO> getOrderBooksTimesDTOByOrderId(Integer orderId);

    void deleteFromOrdersBooksTimesById(Integer ordersBooksTimesId);

    void changeOrdersBooksTimesQuantity(Integer newQuantity, Integer ordersBooksTimesId);

    void deleteOrder(Integer orderId);

    Integer bookQuantityInOrdersBooksTimes(Integer bookId, Integer orderId);

    void updateQuantityInOrderBooksTimes(Integer bookId, Integer orderId, Integer quantity);

    void addOrderBookTimes(Integer bookid, Integer orderId, Integer bookQuantity);

    void updateOrderAndOrdersBooks(Integer orderId, BigDecimal fullPrice);

    List<OrdersBooksDTO> getOrderBooksDTOByOrderId(Integer orderId);

    List<OrdersBooksDTO> getOrderBooksDTOByBookId(Integer bookId);

    void updateOrderDeliveryStatus(String deliveryStatus, Integer orderId);

    Integer orderQuantity();
}
