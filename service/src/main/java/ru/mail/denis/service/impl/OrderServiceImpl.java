package ru.mail.denis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.repositories.*;
import ru.mail.denis.repositories.model.*;
import ru.mail.denis.service.DTOmodels.*;
import ru.mail.denis.service.Orderservice;
import ru.mail.denis.service.util.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by user on 07.07.2017.
 */

@Service
public class OrderServiceImpl implements Orderservice {
    private final UserDAO userDAO;
    private final CatalogueDAO catalogueDAO;
    private final BasketDAO basketDAO;
    private final OrderDAO orderDAO;
    private final OrdersBooksDAO ordersBooksDAO;
    private final OrderBooksTimesDAO orderBooksTimesDAO;
    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(UserDAO userDAO, CatalogueDAO catalogueDAO, BasketDAO basketDAO, OrderDAO orderDAO, OrdersBooksDAO ordersBooksDAO, OrderBooksTimesDAO orderBooksTimesDAO) {
        this.userDAO = userDAO;
        this.catalogueDAO = catalogueDAO;
        this.basketDAO = basketDAO;
        this.orderDAO = orderDAO;
        this.ordersBooksDAO = ordersBooksDAO;
        this.orderBooksTimesDAO = orderBooksTimesDAO;
    }

    @Override
    @Transactional
    public void addOrder(String userEmail, BigDecimal fullPrice) {
        UserDTO userDTO = UserConverter.converter(userDAO.findByEmail(userEmail));
        List<BasketDTO> basketDTOS = BasketConverter.converter(basketDAO.getBasketByUserId(userDTO.getUserId()));
        Order order = new Order();
        order.setOrderReceive(Receive.NOT_RECEIVED);
        order.setOrderDelivery(Delivery.NEW);
        order.setOrderPrice(fullPrice);
        order.setUser(UserConverter.converter(userDTO));
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        order.setOrderDate(dateFormat.format(date));
        saveOrder(order);
        for (int i = 0; i < basketDTOS.size(); i++) {
            saveOrderBooks(OrderBooksConverter.setOrdersBooks(order, basketDTOS.get(i)));
            saveOrderBooksTimes(OrdersBooksTimesConverter.setOrdersBooks(order, basketDTOS.get(i)));
        }
        List<Basket> baskets = basketDAO.getBasketByUserId(userDTO.getUserId());
        for (Basket basket : baskets) {
            basketDAO.delete(basket);
        }
    }


    @Override
    @Transactional
    public List<OrderDTO> getOrdersByUser(String userEmail) {
        UserDTO userDTO = UserConverter.converter(userDAO.findByEmail(userEmail));
        List<Order> orders = getOrderByUserId(userDTO.getUserId());
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = OrderConverter.converter(order);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    @Override
    @Transactional
    public List<OrderDTO> getOrders(int pageId, int total) {
        List<Order> orders = getOrdersByParts(pageId, total);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = OrderConverter.converter(order);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    @Override
    @Transactional
    public void changeReceiveStatus(Integer orderId) {
        Order order = findOrderById(orderId);
        Receive currentReceive = order.getOrderReceive();
        if (currentReceive == Receive.NOT_RECEIVED) {
            order.setOrderReceive(Receive.RECEIVED);
        } else {
            order.setOrderReceive(Receive.NOT_RECEIVED);
        }
        updateOrder(order);
    }

    @Override
    @Transactional
    public OrderDTO getOrderById(Integer orderId) {
        OrderDTO orderDTO = OrderConverter.converter(findOrderById(orderId));
        return orderDTO;
    }

    @Override
    @Transactional
    public List<OrderBookTimesDTO> getOrderBooksTimesDTOByOrderId(Integer orderId) {
        List<OrderBooksTimes> orderBooksTimes = getOrderBooksTimesByOrderId(orderId);
        List<OrderBookTimesDTO> orderBookTimesDTOS = new ArrayList<>();
        for (OrderBooksTimes orderBooksTime : orderBooksTimes) {
            OrderBookTimesDTO orderBookTimesDTO = OrdersBooksTimesConverter.converter(orderBooksTime);
            orderBookTimesDTOS.add(orderBookTimesDTO);
        }
        return orderBookTimesDTOS;
    }

    @Override
    @Transactional
    public void deleteFromOrdersBooksTimesById(Integer ordersBooksTimesId) {
        OrderBooksTimes orderBooksTimes = findOrderBooksTimesById(ordersBooksTimesId);
        deleteOrderBooksTimes(orderBooksTimes);
    }

    @Override
    @Transactional
    public void changeOrdersBooksTimesQuantity(Integer newQuantity, Integer ordersBooksTimesId) {
        OrderBooksTimes orderBooksTimes = findOrderBooksTimesById(ordersBooksTimesId);
        orderBooksTimes.setBookQuantity(newQuantity);
        updateOrderBooksTimes(orderBooksTimes);
    }

    @Override
    @Transactional
    public void deleteOrder(Integer orderId) {
        Order order = findOrderById(orderId);
        deleteOrder(order);
    }

    @Override
    @Transactional
    public Integer bookQuantityInOrdersBooksTimes(Integer bookId, Integer orderId) {
        OrderBooksTimes orderBooksTimes = getOrderBooksTimesByOrderIdAndBookId(bookId, orderId);
        Integer quantity;
        if (orderBooksTimes != null) {
            quantity = orderBooksTimes.getBookQuantity();
        } else {
            quantity = 0;
        }
        return quantity;
    }

    @Override
    @Transactional
    public void updateQuantityInOrderBooksTimes(Integer bookId, Integer orderId, Integer quantity) {
        OrderBooksTimes orderBooksTimes = getOrderBooksTimesByOrderIdAndBookId(bookId, orderId);
        orderBooksTimes.setBookQuantity(quantity);
        updateOrderBooksTimes(orderBooksTimes);
    }

    @Override
    @Transactional
    public void addOrderBookTimes(Integer bookid, Integer orderId, Integer bookQuantity) {
        OrderBooksTimes orderBooksTimes=getOrderBooksTimesByOrderIdAndBookId(orderId,bookid);
        if (orderBooksTimes != null) {
            orderBooksTimes.setBookQuantity(orderBooksTimes.getBookQuantity()+bookQuantity);
            updateOrderBooksTimes(orderBooksTimes);
        } else {
            BookDTO bookDTO=BookConverter.converter(catalogueDAO.findById(bookid)) ;
            bookDTO.setBookQuantity(bookQuantity);
            Order order = findOrderById(orderId);
            saveOrderBooksTimes(OrdersBooksTimesConverter.setOrdersBooks(order,bookDTO));
        }
    }

    @Override
    @Transactional
    public void updateOrderAndOrdersBooks(Integer orderId, BigDecimal fullPrice) {
        List<OrderBooksTimes> orderBooksTimes = getOrderBooksTimesByOrderId(orderId);
        Order order = findOrderById(orderId);
        order.setOrderPrice(fullPrice);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        order.setOrderDate(dateFormat.format(date));
        updateOrder(order);
        List<OrdersBooks> ordersBooks = getOrdersBooksByOrderId(orderId);
        for (OrdersBooks ordersBook : ordersBooks) {
            deleteOrdersBooks(ordersBook);
        }
        for (OrderBooksTimes orderBooksTime : orderBooksTimes) {
            OrdersBooks ordersBooks1 = OrderBooksConverter.converter(orderBooksTime);
            ordersBooks1.setOrder(order);
            saveOrderBooks(ordersBooks1);
        }
    }

    @Override
    @Transactional
    public List<OrdersBooksDTO> getOrderBooksDTOByOrderId(Integer orderId) {
        List<OrdersBooks> orderBooks = getOrdersBooksByOrderId(orderId);
        List<OrdersBooksDTO> orderBooksDTO = new ArrayList<>();
        OrderBooksConverter.converter(orderBooks, orderBooksDTO);
        return orderBooksDTO;
    }


    @Override
    @Transactional
    public List<OrdersBooksDTO> getOrderBooksDTOByBookId(Integer bookId) {
        List<OrdersBooks> orderBooks = getOrdersBooksByBookId(bookId);
        List<OrdersBooksDTO> orderBooksDTO = new ArrayList<>();
        OrderBooksConverter.converter(orderBooks, orderBooksDTO);
        return orderBooksDTO;
    }

    @Override
    @Transactional
    public void updateOrderDeliveryStatus(String deliveryStatus, Integer orderId) {
        Delivery delivery = null;
        if (deliveryStatus.equals("NEW")) {
            delivery = Delivery.NEW;
        } else if (deliveryStatus.equals("REWIWING")) {
            delivery = Delivery.REVIWING;
        } else if (deliveryStatus.equals("IN_PROGRESS")) {
            delivery = Delivery.IN_PROGRESS;
        } else if (deliveryStatus.equals("DELIVERED")) {
            delivery = Delivery.DELIVERED;
        }
        Order order = findOrderById(orderId);
        order.setOrderDelivery(delivery);
        updateOrder(order);
    }

    @Override
    @Transactional
    public Integer orderQuantity() {
        Integer quantity = findAllOrders().size();
        return quantity;
    }

    private void saveOrder(Order order) {
        logger.debug("Saving new order");
        orderDAO.save(order);
    }

    private void saveOrderBooks(OrdersBooks ordersBooks) {

        logger.debug("Saving new OrdersBooks");
        ordersBooksDAO.save(ordersBooks);
    }

    private void saveOrderBooksTimes(OrderBooksTimes orderBooksTimes) {

        logger.debug("Saving OrderBooksTimes");
        orderBooksTimesDAO.save(orderBooksTimes);
    }

    private List<Order> getOrderByUserId(Integer userId) {
        logger.debug("Getting Order by User Id");
        List<Order> orders = orderDAO.getOrderByUserId(userId);
        return orders;
    }

    private List<OrderBooksTimes> getOrderBooksTimesByOrderId(Integer orderId) {
        logger.debug("Getting OrderBooksTimes by Order id");
        List<OrderBooksTimes> orderBooksTimes = orderBooksTimesDAO.getOrderBooksTimesByOrderId(orderId);
        return orderBooksTimes;
    }

    private List<OrdersBooks> getOrdersBooksByOrderId(Integer orderId) {
        logger.debug("Getting OrdersBooks by order id");
        List<OrdersBooks> ordersBooks = ordersBooksDAO.getOrdersBooksByOrderId(orderId);
        return ordersBooks;
    }

    private List<OrdersBooks> getOrdersBooksByBookId(Integer bookId) {
        logger.debug("Getting OrdersBooks by Book Id");
        List<OrdersBooks> ordersBooks = ordersBooksDAO.getOrdersBooksByBookId(bookId);
        return ordersBooks;
    }

    private List<Order> getOrdersByParts(Integer pageId, Integer total) {

        logger.debug("Getting Orders by parts");
        List<Order> orders = orderDAO.getOrdersByParts(pageId, total);
        return orders;
    }

    private Order findOrderById(Integer id) {

        logger.debug("Finding order by Id");
        Order order = orderDAO.findById(id);
        return order;
    }

    private OrderBooksTimes findOrderBooksTimesById(Integer id) {

        logger.debug("Finding OrderBookstimes by Id");
        OrderBooksTimes orderBooksTimes = orderBooksTimesDAO.findById(id);
        return orderBooksTimes;
    }

    private OrderBooksTimes getOrderBooksTimesByOrderIdAndBookId(Integer orderId, Integer bookId) {

        logger.debug("Getting OrderBooksTimes by Order id and Book id");
        OrderBooksTimes orderBooksTimes = orderBooksTimesDAO.getOrderBooksTimesByOrderIdAndBookId(orderId, bookId);
        return orderBooksTimes;
    }

    private void updateOrder(Order order) {

        logger.debug("Updating Order");
        orderDAO.update(order);
    }

    private void deleteOrderBooksTimes(OrderBooksTimes orderBooksTimes) {

        logger.debug("Deleting OrdersBooksTimes");
        orderBooksTimesDAO.delete(orderBooksTimes);
    }

    private void deleteOrdersBooks(OrdersBooks ordersBooks) {

        logger.debug("Deleting OrdersBooks");
        ordersBooksDAO.delete(ordersBooks);
    }

    private void deleteOrder(Order order) {

        logger.debug("Deleting Order");
        orderDAO.delete(order);
    }

    private void updateOrderBooksTimes(OrderBooksTimes orderBooksTimes) {

        logger.debug("Updating OrderBookTimes");
        orderBooksTimesDAO.update(orderBooksTimes);
    }

    private List<Order> findAllOrders() {

        logger.debug("Finding all orders");
        List<Order> orders = orderDAO.findAll();
        return orders;
    }
}
