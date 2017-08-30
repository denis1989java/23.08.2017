package ru.mail.denis.service.util;

import ru.mail.denis.repositories.model.Order;
import ru.mail.denis.repositories.model.OrderBooksTimes;
import ru.mail.denis.repositories.model.OrdersBooks;
import ru.mail.denis.service.model.BasketDTO;
import ru.mail.denis.service.model.OrdersBooksDTO;

import java.util.List;

/**
 * Created by Denis Monich on 24.08.2017.
 */
public class OrderBooksConverter {



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

}
