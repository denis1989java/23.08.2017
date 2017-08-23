package ru.mail.denis.service;

import ru.mail.denis.models.Delivery;
import ru.mail.denis.models.OrdersBooks;
import ru.mail.denis.service.DTOmodels.*;

import java.util.List;

/**
 * Created by user on 07.07.2017.
 */
public interface Orderservice {

    void insertToOrders(List<BasketDTO> baskets, UserDTO userDTO, Double orderPrice);

    List<OrderDTO> getOrdersByUser(Integer userId);

    List<OrderDTO> getOrders(int pageId, int total);

    void changeReceiveStatus(Integer orderId);

    OrderDTO getOrderById(Integer orderId);

    List<OrderBookTimesDTO> getOrderBooksTimesDTOByOrderId(Integer orderId);

    void deleteFromOrdersBooksTimesById(Integer ordersBooksTimesId);

    void changeOrdersBooksTimesQuantity(Integer newQuantity, Integer ordersBooksTimesId);

    void deleteOrder(Integer orderId);

    Integer bookQuantityInOrdersBooksTimes(Integer bookId, Integer orderId);

    void updateQuantityInOrderBooksTimes(Integer bookId, Integer orderId, Integer quantity);

    void insertToOrdersBooksTimes(BookDTO bookDTO, Integer orderId);

    void updateOrderAndOrdersBooks(Integer orderId, Double fullPrice);

    List<OrdersBooksDTO> getOrderBooksDTOByOrderId(Integer orderId);

    List<OrdersBooksDTO> getOrderBooksDTOByBookId(Integer bookId);

    void updateOrderDeliveryStatus(Delivery delivery, Integer orderId);

    Integer orderQuantity();
}
