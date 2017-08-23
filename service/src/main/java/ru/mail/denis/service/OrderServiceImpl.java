package ru.mail.denis.service;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.models.*;
import ru.mail.denis.repositories.DAO.*;
import ru.mail.denis.service.DTOmodels.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static ru.mail.denis.repositories.HibernateUtil.sessionFactory;


/**
 * Created by user on 07.07.2017.
 */

@Service
public class OrderServiceImpl implements Orderservice {

    private final UserService userService;
    private final OrderDAO orderDAO;
    private final OrdersBooksDAO ordersBooksDAO;
    private final OrderBooksTimesDAO orderBooksTimesDAO;
    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(UserService userService, OrderDAO orderDAO, OrdersBooksDAO ordersBooksDAO, OrderBooksTimesDAO orderBooksTimesDAO) {
        this.userService = userService;
        this.orderDAO = orderDAO;
        this.ordersBooksDAO = ordersBooksDAO;
        this.orderBooksTimesDAO = orderBooksTimesDAO;
    }

    @Override
    @Transactional()
    public void insertToOrders(List<BasketDTO> baskets, UserDTO userDTO, Double orderPrice) {
        Order order = new Order();
        order.setOrderReceive(Receive.NOT_RECEIVED);
        order.setOrderDelivery(Delivery.NEW);
        order.setOrderPrice(orderPrice);
        //order.setUser(userService.userDTOToUser(userDTO));
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        order.setOrderDate(dateFormat.format(date));
        saveOrder(order);
        for (int i = 0; i < baskets.size(); i++) {
            OrdersBooks ordersBooks = new OrdersBooks();
            ordersBooks.setBookName(baskets.get(i).getBookName());
            ordersBooks.setBookPrice(baskets.get(i).getBookPrice());
            ordersBooks.setBookQuantity(baskets.get(i).getBookQuantity());
            ordersBooks.setBookId(baskets.get(i).getBookId());
            ordersBooks.setOrder(order);
            OrderBooksTimes orderBooksTimes = new OrderBooksTimes();
            orderBooksTimes.setBookPrice(baskets.get(i).getBookPrice());
            orderBooksTimes.setBookName(baskets.get(i).getBookName());
            orderBooksTimes.setBookid(baskets.get(i).getBookId());
            orderBooksTimes.setBookQuantity(baskets.get(i).getBookQuantity());
            orderBooksTimes.setOrder(order);
            saveOrderBooks(ordersBooks);
            saveOrderBooksTimes(orderBooksTimes);
        }
    }

    @Override
    @Transactional()
    public List<OrderDTO> getOrdersByUser(Integer userId) {
        List<Order> orders = getOrderByUserId(userId);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = orderToOrderDTO(order);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    @Override
    @Transactional()
    public List<OrderDTO> getOrders(int pageId, int total) {
        List<Order> orders = getOrdersByParts(pageId, total);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = orderToOrderDTO(order);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    @Override
    @Transactional()
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
    @Transactional()
    public OrderDTO getOrderById(Integer orderId) {
        OrderDTO orderDTO=orderToOrderDTO(findOrderById(orderId));
        return orderDTO;
    }

    @Override
    @Transactional()
    public List<OrderBookTimesDTO> getOrderBooksTimesDTOByOrderId(Integer orderId) {
        List<OrderBooksTimes> orderBooksTimes = getOrderBooksTimesByOrderId(orderId);
        List<OrderBookTimesDTO> orderBookTimesDTOS = new ArrayList<>();
        for (OrderBooksTimes orderBooksTime : orderBooksTimes) {
            OrderBookTimesDTO orderBookTimesDTO = orderBookTimesToOrderBookTimesDTO(orderBooksTime);
            orderBookTimesDTOS.add(orderBookTimesDTO);
        }
        return orderBookTimesDTOS;
    }

    @Override
    @Transactional()
    public void deleteFromOrdersBooksTimesById(Integer ordersBooksTimesId) {
        OrderBooksTimes orderBooksTimes = findOrderBooksTimesById(ordersBooksTimesId);
        deleteOrderBooksTimes(orderBooksTimes);
    }

    @Override
    @Transactional()
    public void changeOrdersBooksTimesQuantity(Integer newQuantity, Integer ordersBooksTimesId) {
        OrderBooksTimes orderBooksTimes = findOrderBooksTimesById(ordersBooksTimesId);
        orderBooksTimes.setBookQuantity(newQuantity);
        updateOrderBooksTimes(orderBooksTimes);
    }

    @Override
    @Transactional()
    public void deleteOrder(Integer orderId) {
        Order order = findOrderById(orderId);
        deleteOrder(order);
    }

    @Override
    @Transactional()
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
    @Transactional()
    public void updateQuantityInOrderBooksTimes(Integer bookId, Integer orderId, Integer quantity) {
        OrderBooksTimes orderBooksTimes = getOrderBooksTimesByOrderIdAndBookId(bookId, orderId);
        orderBooksTimes.setBookQuantity(quantity);
        updateOrderBooksTimes(orderBooksTimes);
    }

    @Override
    @Transactional()
    public void insertToOrdersBooksTimes(BookDTO bookDTO, Integer orderId) {
        Order order = findOrderById(orderId);
        OrderBooksTimes orderBooksTimes = new OrderBooksTimes();
        orderBooksTimes.setBookQuantity(bookDTO.getBookQuantity());
        orderBooksTimes.setBookid(bookDTO.getBookId());
        orderBooksTimes.setBookName(bookDTO.getBookName());
        orderBooksTimes.setBookPrice(bookDTO.getBookPrice());
        orderBooksTimes.setOrder(order);
        saveOrderBooksTimes(orderBooksTimes);
    }

    @Override
    @Transactional()
    public void updateOrderAndOrdersBooks(Integer orderId, Double fullPrice) {
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
            OrdersBooks ordersBooks1 = new OrdersBooks();
            ordersBooks1.setBookQuantity(orderBooksTime.getBookQuantity());
            ordersBooks1.setBookId(orderBooksTime.getBookid());
            ordersBooks1.setBookName(orderBooksTime.getBookName());
            ordersBooks1.setBookPrice(orderBooksTime.getBookPrice());
            ordersBooks1.setOrder(order);
            saveOrderBooks(ordersBooks1);
        }
    }

    @Override
    @Transactional()
    public List<OrdersBooksDTO> getOrderBooksDTOByOrderId(Integer orderId) {
        List<OrdersBooks> orderBooks = getOrdersBooksByOrderId(orderId);
        List<OrdersBooksDTO> orderBooksDTO = new ArrayList<>();
        ListOrdersBooksToListOrdersBooksDTO(orderBooks, orderBooksDTO);
        return orderBooksDTO;
    }



    @Override
    @Transactional()
    public List<OrdersBooksDTO> getOrderBooksDTOByBookId(Integer bookId) {
        List<OrdersBooks> orderBooks = getOrdersBooksByBookId(bookId);
        List<OrdersBooksDTO> orderBooksDTO = new ArrayList<>();
        ListOrdersBooksToListOrdersBooksDTO(orderBooks, orderBooksDTO);
        return orderBooksDTO;
    }

    @Override
    @Transactional()
    public void updateOrderDeliveryStatus(Delivery delivery, Integer orderId) {
        Order order = findOrderById(orderId);
        order.setOrderDelivery(delivery);
        updateOrder(order);
    }

    @Override
    @Transactional()
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

    private OrderBookTimesDTO orderBookTimesToOrderBookTimesDTO(OrderBooksTimes orderBooksTimes) {
        OrderBookTimesDTO orderBookTimesDTO = new OrderBookTimesDTO();
        orderBookTimesDTO.setBookid(orderBooksTimes.getBookid());
        orderBookTimesDTO.setOrdersBooksTimesId(orderBooksTimes.getOrdersBooksTimesId());
        orderBookTimesDTO.setBookQuantity(orderBooksTimes.getBookQuantity());
        orderBookTimesDTO.setBookPrice(orderBooksTimes.getBookPrice());
        orderBookTimesDTO.setBookName(orderBooksTimes.getBookName());
        return orderBookTimesDTO;
    }


    private OrderBooksTimes orderBooksTimesDTOToOrderBooksTimes(OrderBookTimesDTO orderBookTimesDTO) {
        OrderBooksTimes orderBooksTimes = new OrderBooksTimes();
        orderBooksTimes.setBookQuantity(orderBookTimesDTO.getBookQuantity());
        orderBooksTimes.setBookid(orderBookTimesDTO.getBookid());
        orderBooksTimes.setBookName(orderBookTimesDTO.getBookName());
        orderBooksTimes.setBookPrice(orderBookTimesDTO.getBookPrice());
        orderBooksTimes.setOrdersBooksTimesId(orderBookTimesDTO.getOrdersBooksTimesId());
        return orderBooksTimes;
    }

    private OrdersBooksDTO ordersBooksDTOToOrdersBooks(OrdersBooks ordersBooks) {
        OrdersBooksDTO ordersBooksDTO = new OrdersBooksDTO();
        ordersBooksDTO.setBookName(ordersBooks.getBookName());
        ordersBooksDTO.setOrdersBooksId(ordersBooks.getOrdersBooksId());
        ordersBooksDTO.setBookQuantity(ordersBooks.getBookQuantity());
        ordersBooksDTO.setBookPrice(ordersBooks.getBookPrice());
        ordersBooksDTO.setBookId(ordersBooks.getBookId());
        return ordersBooksDTO;
    }

    private OrdersBooks ordersBooksToOrderBooksDTO(OrdersBooksDTO ordersBooksDTO) {
        OrdersBooks ordersBooks = new OrdersBooks();
        ordersBooks.setBookPrice(ordersBooksDTO.getBookPrice());
        ordersBooks.setBookName(ordersBooksDTO.getBookName());
        ordersBooks.setBookId(ordersBooksDTO.getBookId());
        ordersBooks.setBookQuantity(ordersBooksDTO.getBookQuantity());
        ordersBooks.setOrdersBooksId(ordersBooksDTO.getOrdersBooksId());
        return ordersBooks;
    }

    private OrderDTO orderToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderPrice(order.getOrderPrice());
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderDelivery(order.getOrderDelivery());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setOrderReceive(order.getOrderReceive());
        return orderDTO;
    }

    private Order orderDTOToOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderDelivery(orderDTO.getOrderDelivery());
        order.setOrderPrice(orderDTO.getOrderPrice());
        order.setOrderReceive(orderDTO.getOrderReceive());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setOrderId(orderDTO.getOrderId());
        return order;
    }
    public List<OrdersBooksDTO> ListOrdersBooksToListOrdersBooksDTO(List<OrdersBooks> orderBooks, List<OrdersBooksDTO> orderBooksDTO) {
        for (OrdersBooks orderBook : orderBooks) {
            OrdersBooksDTO ordersBookDTO = new OrdersBooksDTO();
            ordersBookDTO.setBookId(orderBook.getBookId());
            ordersBookDTO.setBookPrice(orderBook.getBookPrice());
            ordersBookDTO.setBookQuantity((orderBook.getBookQuantity()));
            ordersBookDTO.setOrdersBooksId(orderBook.getOrdersBooksId());
            ordersBookDTO.setBookName(orderBook.getBookName());
            orderBooksDTO.add(ordersBookDTO);
        }
        return orderBooksDTO;
    }
}
