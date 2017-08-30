package ru.mail.denis.service;

import ru.mail.denis.service.model.*;

import java.util.List;

/**
 * Created by Denis Monich on 07.07.2017.
 */
public interface Orderservice {


    ViewDTO viewPageOrders(String orderId, String userEmail);

    ViewDTO viewPageAllOrders(String orderId, Integer page);

    ViewDTO viewPageChangeOrder(OrderDTO orderDTO, String orderId);

    void addOrder(String userEmail, String fullPrice);

    void changeReceiveStatus(Integer orderId);

    OrderDTO getOrderById(Integer orderId);

    void deleteFromOrdersBooksTimesById(String[] deletings);

    void changeOrdersBooksTimesQuantity(String newQuantity, Integer ordersBooksTimesId);

    void deleteOrder(Integer orderId);

    void addOrderBookTimes(Integer bookid, Integer orderId, String bookQuantity);

    void updateOrderAndOrdersBooks(Integer orderId, String fullPrice);

    List<OrdersBooksDTO> getOrderBooksDTOByBookId(Integer bookId);

    void updateOrderDeliveryStatus(String deliveryStatus, Integer orderId);

}
