package ru.mail.denis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.repositories.*;
import ru.mail.denis.repositories.model.*;
import ru.mail.denis.service.model.*;
import ru.mail.denis.service.Orderservice;
import ru.mail.denis.service.util.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Denis Monich on 07.07.2017.
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
    public ViewDTO viewPageOrders(String orderId, String userEmail) {
        UserDTO userDTO = UserConverter.converter(userDAO.findByEmail(userEmail));
        List<OrderDTO> orderDTOS = OrderConverter.converter(getOrderByUserId(userDTO.getUserId()));
        Map<String, Object> map = new HashMap<>();
        if (orderId != null) {
            List<OrdersBooks> orderBooks = getOrdersBooksByOrderId(Integer.valueOf(orderId));
            List<OrdersBooksDTO> orderBooksDTO = new ArrayList<>();
            OrderBooksConverter.converter(orderBooks, orderBooksDTO);
            map.put("ordersBooksDTO", orderBooksDTO);
            map.put("orderID", orderId);
        }
        map.put("orders", orderDTOS);
        ViewDTO viewDTO = new ViewDTO();
        viewDTO.setViewMap(map);
        return viewDTO;
    }

    @Override
    @Transactional
    public ViewDTO viewPageAllOrders(String orderId, Integer page) {
        int total = 7;
        int pageNumber=page;
        if (page != 0) {
            page = page * total;
        }
        Long orderDTOQuantity = ordersQuantity();
        List<Long> pagination = new ArrayList();
        Long pageQuantity = Long.valueOf(0);
        if (orderDTOQuantity % total == 0) {
            pageQuantity = orderDTOQuantity / total;
        } else {
            pageQuantity = orderDTOQuantity / total + 1;
        }
        for (Long i = Long.valueOf(0); i < pageQuantity; i++) {
            pagination.add(i);
        }
        List<OrderDTO> orders = OrderConverter.converter(getOrdersByParts(page, total));
        Map<String, Object> map = new HashMap<>();
        if (orderId != null) {
            List<OrdersBooks> orderBooks = getOrdersBooksByOrderId(Integer.valueOf(orderId));
            List<OrdersBooksDTO> orderBooksDTO = new ArrayList<>();
            OrderBooksConverter.converter(orderBooks, orderBooksDTO);
            map.put("ordersBooksDTO", orderBooksDTO);
            map.put("orderID", orderId);
        }
        map.put("page",pageNumber);
        map.put("orders", orders);
        map.put("pagination", pagination);
        ViewDTO viewDTO = new ViewDTO();
        viewDTO.setViewMap(map);
        return viewDTO;
    }

    @Override
    @Transactional
    public ViewDTO viewPageChangeOrder(OrderDTO orderDTO, String orderId) {
        List<OrderBookTimesDTO> bookTimesDTOS = converter(getOrderBooksTimesByOrderId(Integer.valueOf(orderId)));
        BigDecimal summ = BigDecimal.ZERO;
        for (OrderBookTimesDTO bookTimesDTO : bookTimesDTOS) {
            BigDecimal price = new BigDecimal(bookTimesDTO.getBookPrice()) ;
            String quantity = bookTimesDTO.getBookQuantity();
            summ = summ.add(price.multiply(new BigDecimal(quantity)));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("orderDTO", orderDTO);
        map.put("summ", summ);
        map.put("OrderBookTimesDTO", bookTimesDTOS);
        ViewDTO viewDTO = new ViewDTO();
        viewDTO.setViewMap(map);
        return viewDTO;

    }


    @Override
    @Transactional
    public void addOrder(String userEmail, String fullPrice) {
        User user = userDAO.findByEmail(userEmail);
        List<BasketDTO> basketDTOS = BasketConverter.converter(basketDAO.getBasketByUserId(user.getUserId()));
        Order order = new Order();
        order.setOrderReceive(Receive.NOT_RECEIVED);
        order.setOrderDelivery(Delivery.NEW);
        order.setOrderPrice(fullPrice);
        order.setUser(user);
        order.setOrderDate(DateConverter.converter());
        saveOrder(order);
        for (int i = 0; i < basketDTOS.size(); i++) {
            saveOrderBooks(setOrdersBooks(order, basketDTOS.get(i)));
            saveOrderBooksTimes(setOrdersBook(order, basketDTOS.get(i)));
            Book book = catalogueDAO.findById(basketDTOS.get(i).getBookId());
            book.setChangable(Changable.NOT_CHANGABLE);
        }
        List<Basket> baskets = basketDAO.getBasketByUserId(user.getUserId());
        for (Basket basket : baskets) {
            basketDAO.delete(basket);
        }
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
    public void deleteFromOrdersBooksTimesById(String[] deletings) {
        for (String deleting : deletings) {
            Integer orderBookTimesId = Integer.valueOf(deleting);
            OrderBooksTimes orderBooksTimes = findOrderBooksTimesById(orderBookTimesId);
            deleteOrderBooksTimes(orderBooksTimes);
        }

    }

    @Override
    @Transactional
    public void changeOrdersBooksTimesQuantity(String newQuantity, Integer ordersBooksTimesId) {
        OrderBooksTimes orderBooksTimes = findOrderBooksTimesById(ordersBooksTimesId);
        orderBooksTimes.setBookQuantity(newQuantity);
        updateOrderBooksTimes(orderBooksTimes);
    }

    @Override
    @Transactional
    public void deleteOrder(Integer orderId) {
        Order order = findOrderById(orderId);
        List<OrdersBooks> ordersBooks = getOrdersBooksByOrderId(orderId);
        for (OrdersBooks ordersBook : ordersBooks) {
            deleteOrdersBooks(ordersBook);
            List<OrdersBooksDTO> ordersBooks2 = getOrderBooksDTOByBookId(ordersBook.getBookId());
            Book book = catalogueDAO.findById(ordersBook.getBookId());
            if (ordersBooks2.isEmpty()) {
                book.setChangable(Changable.CHANGABLE);
            } else {
                book.setChangable(Changable.NOT_CHANGABLE);
            }
            deleteOrder(order);
        }
    }


    @Override
    @Transactional
    public void addOrderBookTimes(Integer bookid, Integer orderId, String bookQuantity) {
        OrderBooksTimes orderBooksTimes = getOrderBooksTimesByOrderIdAndBookId(orderId, bookid);
        if (orderBooksTimes != null) {
            orderBooksTimes.setBookQuantity(String.valueOf(Integer.parseInt(orderBooksTimes.getBookQuantity()) +Integer.parseInt(bookQuantity) ));
            updateOrderBooksTimes(orderBooksTimes);
        } else {
            BookDTO bookDTO = BookConverter.converter(catalogueDAO.findById(bookid));
            bookDTO.setBookQuantity(bookQuantity);
            Order order = findOrderById(orderId);
            saveOrderBooksTimes(setOrdersBooks(order, bookDTO));
        }
    }

    @Override
    @Transactional
    public void updateOrderAndOrdersBooks(Integer orderId, String fullPrice) {
        List<OrderBooksTimes> orderBooksTimes = getOrderBooksTimesByOrderId(orderId);
        Order order = findOrderById(orderId);
        order.setOrderPrice(fullPrice);
        order.setOrderDate(DateConverter.converter());
        updateOrder(order);
        List<OrdersBooks> ordersBooks = getOrdersBooksByOrderId(orderId);
        for (OrdersBooks ordersBook : ordersBooks) {
            deleteOrdersBooks(ordersBook);
            List<OrdersBooksDTO> ordersBooks2 = getOrderBooksDTOByBookId(ordersBook.getBookId());
            Book book = catalogueDAO.findById(ordersBook.getBookId());
            if (ordersBooks2.isEmpty()) {
                book.setChangable(Changable.CHANGABLE);
            } else {
                book.setChangable(Changable.NOT_CHANGABLE);
            }
        }
        for (OrderBooksTimes orderBooksTime : orderBooksTimes) {
            OrdersBooks ordersBooks1 = converter(orderBooksTime);
            Book book = catalogueDAO.findById(orderBooksTime.getBookid());
            book.setChangable(Changable.NOT_CHANGABLE);
            ordersBooks1.setOrder(order);
            saveOrderBooks(ordersBooks1);
        }
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
        logger.debug("Getting Order by User Id"+ userId);
        List<Order> orders = orderDAO.getOrderByUserId(userId);
        return orders;
    }

    private List<OrderBooksTimes> getOrderBooksTimesByOrderId(Integer orderId) {
        logger.debug("Getting OrderBooksTimes by Order id"+ orderId);
        List<OrderBooksTimes> orderBooksTimes = orderBooksTimesDAO.getOrderBooksTimesByOrderId(orderId);
        return orderBooksTimes;
    }

    private List<OrdersBooks> getOrdersBooksByOrderId(Integer orderId) {
        logger.debug("Getting OrdersBooks by order id" +orderId);
        List<OrdersBooks> ordersBooks = ordersBooksDAO.getOrdersBooksByOrderId(orderId);
        return ordersBooks;
    }


    private List<OrdersBooks> getOrdersBooksByBookId(Integer bookId) {
        logger.debug("Getting OrdersBooks by Book Id"+ bookId);
        List<OrdersBooks> ordersBooks = ordersBooksDAO.getOrdersBooksByBookId(bookId);
        return ordersBooks;
    }

    private List<Order> getOrdersByParts(Integer pageId, Integer total) {

        logger.debug("Getting Orders by parts");
        List<Order> orders = orderDAO.getOrdersByParts(pageId, total);
        return orders;
    }

    private Order findOrderById(Integer id) {

        logger.debug("Finding order by Id"+ id);
        Order order = orderDAO.findById(id);
        return order;
    }

    private OrderBooksTimes findOrderBooksTimesById(Integer id) {

        logger.debug("Finding OrderBookstimes by Id"+ id);
        OrderBooksTimes orderBooksTimes = orderBooksTimesDAO.findById(id);
        return orderBooksTimes;
    }

    private OrderBooksTimes getOrderBooksTimesByOrderIdAndBookId(Integer orderId, Integer bookId) {

        logger.debug("Getting OrderBooksTimes by Order id and Book id"+orderId +" AND "+bookId);
        OrderBooksTimes orderBooksTimes = orderBooksTimesDAO.getOrderBooksTimesByOrderIdAndBookId(orderId, bookId);
        return orderBooksTimes;
    }

    private void updateOrder(Order order) {

        logger.debug("Updating Order" + order.getOrderId());
        orderDAO.update(order);
    }

    private void deleteOrderBooksTimes(OrderBooksTimes orderBooksTimes) {

        logger.debug("Deleting OrdersBooksTimes"+orderBooksTimes.getOrdersBooksTimesId());
        orderBooksTimesDAO.delete(orderBooksTimes);
    }

    private void deleteOrdersBooks(OrdersBooks ordersBooks) {

        logger.debug("Deleting OrdersBooks"+ordersBooks.getOrdersBooksId());
        ordersBooksDAO.delete(ordersBooks);
    }

    private void deleteOrder(Order order) {

        logger.debug("Deleting Order"+order.getOrderId());
        orderDAO.delete(order);
    }

    private void updateOrderBooksTimes(OrderBooksTimes orderBooksTimes) {

        logger.debug("Updating OrderBookTimes" + orderBooksTimes.getOrdersBooksTimesId());
        orderBooksTimesDAO.update(orderBooksTimes);
    }

    private Long ordersQuantity () {
        logger.debug("Finding orders quantity");
        Long quantity = orderDAO.getOrdersQuantity();
        return quantity;
    }

    private OrdersBooks setOrdersBooks(Order order, BasketDTO basketDTO){
        OrdersBooks ordersBooks = new OrdersBooks();
        ordersBooks.setBookName(basketDTO.getBookName());
        ordersBooks.setBookPrice(basketDTO.getBookPrice());
        ordersBooks.setBookQuantity(basketDTO.getBookQuantity());
        ordersBooks.setBookId(basketDTO.getBookId());
        ordersBooks.setOrder(order);
        return ordersBooks;
    }
    private OrderBooksTimes setOrdersBook(Order order, BasketDTO basketDTO){
        OrderBooksTimes orderBooksTimes = new OrderBooksTimes();
        orderBooksTimes.setBookPrice(basketDTO.getBookPrice());
        orderBooksTimes.setBookName(basketDTO.getBookName());
        orderBooksTimes.setBookid(basketDTO.getBookId());
        orderBooksTimes.setBookQuantity(basketDTO.getBookQuantity());
        orderBooksTimes.setOrder(order);
        return orderBooksTimes;
    }

    private OrdersBooks converter (OrderBooksTimes orderBooksTimes){
        OrdersBooks ordersBooks = new OrdersBooks();
        ordersBooks.setBookQuantity(orderBooksTimes.getBookQuantity());
        ordersBooks.setBookId(orderBooksTimes.getBookid());
        ordersBooks.setBookName(orderBooksTimes.getBookName());
        ordersBooks.setBookPrice(orderBooksTimes.getBookPrice());
        return ordersBooks;
    }

    private OrderBookTimesDTO convert(OrderBooksTimes orderBooksTimes){
        OrderBookTimesDTO orderBookTimesDTO = new OrderBookTimesDTO();
        orderBookTimesDTO.setBookid(orderBooksTimes.getBookid());
        orderBookTimesDTO.setOrdersBooksTimesId(orderBooksTimes.getOrdersBooksTimesId());
        orderBookTimesDTO.setBookQuantity(orderBooksTimes.getBookQuantity());
        orderBookTimesDTO.setBookPrice(orderBooksTimes.getBookPrice());
        orderBookTimesDTO.setBookName(orderBooksTimes.getBookName());
        return orderBookTimesDTO;
    }

   private List<OrderBookTimesDTO> converter(List <OrderBooksTimes> orderBooksTimes){
        List<OrderBookTimesDTO> orderBookTimesDTOS = new ArrayList<>();
        for (OrderBooksTimes orderBooksTime : orderBooksTimes) {
            OrderBookTimesDTO orderBookTimesDTO = convert(orderBooksTime);
            orderBookTimesDTOS.add(orderBookTimesDTO);
        }
        return orderBookTimesDTOS;
    }


    private OrderBooksTimes setOrdersBooks(Order order, BookDTO bookDTO){
        OrderBooksTimes orderBooksTimes = new OrderBooksTimes();
        orderBooksTimes.setBookQuantity(bookDTO.getBookQuantity());
        orderBooksTimes.setBookid(bookDTO.getBookId());
        orderBooksTimes.setBookName(bookDTO.getBookName());
        orderBooksTimes.setBookPrice(bookDTO.getBookPrice());
        orderBooksTimes.setOrder(order);
        return orderBooksTimes;
    }
}
