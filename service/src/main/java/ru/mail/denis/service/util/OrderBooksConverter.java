package ru.mail.denis.service.util;

import ru.mail.denis.repositories.model.Order;
import ru.mail.denis.repositories.model.OrderBooksTimes;
import ru.mail.denis.repositories.model.OrdersBooks;
import ru.mail.denis.service.DTOmodels.BasketDTO;
import ru.mail.denis.service.DTOmodels.OrdersBooksDTO;

import java.util.List;

/**
 * Created by user on 24.08.2017.
 */
public class OrderBooksConverter {

    public OrderBooksConverter() {
    }

    public static OrdersBooks converter(OrdersBooksDTO ordersBooksDTO){
        OrdersBooks ordersBooks = new OrdersBooks();
        ordersBooks.setBookPrice(ordersBooksDTO.getBookPrice());
        ordersBooks.setBookName(ordersBooksDTO.getBookName());
        ordersBooks.setBookId(ordersBooksDTO.getBookId());
        ordersBooks.setBookQuantity(ordersBooksDTO.getBookQuantity());
        ordersBooks.setOrdersBooksId(ordersBooksDTO.getOrdersBooksId());
        return ordersBooks;
    }

    public static OrdersBooksDTO converter(OrdersBooks ordersBooks){
        OrdersBooksDTO ordersBooksDTO = new OrdersBooksDTO();
        ordersBooksDTO.setBookName(ordersBooks.getBookName());
        ordersBooksDTO.setOrdersBooksId(ordersBooks.getOrdersBooksId());
        ordersBooksDTO.setBookQuantity(ordersBooks.getBookQuantity());
        ordersBooksDTO.setBookPrice(ordersBooks.getBookPrice());
        ordersBooksDTO.setBookId(ordersBooks.getBookId());
        return ordersBooksDTO;
    }

    public static OrdersBooks setOrdersBooks(Order order, BasketDTO basketDTO){
        OrdersBooks ordersBooks = new OrdersBooks();
        ordersBooks.setBookName(basketDTO.getBookName());
        ordersBooks.setBookPrice(basketDTO.getBookPrice());
        ordersBooks.setBookQuantity(basketDTO.getBookQuantity());
        ordersBooks.setBookId(basketDTO.getBookId());
        ordersBooks.setOrder(order);
        return ordersBooks;
    }

    public static List<OrdersBooksDTO> converter(List<OrdersBooks> orderBooks, List<OrdersBooksDTO> orderBooksDTO){
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
    public static OrdersBooks converter (OrderBooksTimes orderBooksTimes){
        OrdersBooks ordersBooks = new OrdersBooks();
        ordersBooks.setBookQuantity(orderBooksTimes.getBookQuantity());
        ordersBooks.setBookId(orderBooksTimes.getBookid());
        ordersBooks.setBookName(orderBooksTimes.getBookName());
        ordersBooks.setBookPrice(orderBooksTimes.getBookPrice());
        return ordersBooks;
    }
}
