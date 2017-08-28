package ru.mail.denis.service.util;

import ru.mail.denis.repositories.model.Order;
import ru.mail.denis.repositories.model.OrderBooksTimes;
import ru.mail.denis.service.modelDTO.BasketDTO;
import ru.mail.denis.service.modelDTO.BookDTO;
import ru.mail.denis.service.modelDTO.OrderBookTimesDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 24.08.2017.
 */
public class OrdersBooksTimesConverter {
    public OrdersBooksTimesConverter() {
    }

    public static OrderBooksTimes converter(OrderBookTimesDTO orderBookTimesDTO){
        OrderBooksTimes orderBooksTimes = new OrderBooksTimes();
        orderBooksTimes.setBookQuantity(orderBookTimesDTO.getBookQuantity());
        orderBooksTimes.setBookid(orderBookTimesDTO.getBookid());
        orderBooksTimes.setBookName(orderBookTimesDTO.getBookName());
        orderBooksTimes.setBookPrice(orderBookTimesDTO.getBookPrice());
        orderBooksTimes.setOrdersBooksTimesId(orderBookTimesDTO.getOrdersBooksTimesId());
        return orderBooksTimes;
    }

    public static OrderBookTimesDTO converter(OrderBooksTimes orderBooksTimes){
        OrderBookTimesDTO orderBookTimesDTO = new OrderBookTimesDTO();
        orderBookTimesDTO.setBookid(orderBooksTimes.getBookid());
        orderBookTimesDTO.setOrdersBooksTimesId(orderBooksTimes.getOrdersBooksTimesId());
        orderBookTimesDTO.setBookQuantity(orderBooksTimes.getBookQuantity());
        orderBookTimesDTO.setBookPrice(orderBooksTimes.getBookPrice());
        orderBookTimesDTO.setBookName(orderBooksTimes.getBookName());
        return orderBookTimesDTO;
    }

    public static OrderBooksTimes setOrdersBooks(Order order, BasketDTO basketDTO){
        OrderBooksTimes orderBooksTimes = new OrderBooksTimes();
        orderBooksTimes.setBookPrice(basketDTO.getBookPrice());
        orderBooksTimes.setBookName(basketDTO.getBookName());
        orderBooksTimes.setBookid(basketDTO.getBookId());
        orderBooksTimes.setBookQuantity(basketDTO.getBookQuantity());
        orderBooksTimes.setOrder(order);
        return orderBooksTimes;
    }
    public static OrderBooksTimes setOrdersBooks(Order order, BookDTO bookDTO){
        OrderBooksTimes orderBooksTimes = new OrderBooksTimes();
        orderBooksTimes.setBookQuantity(bookDTO.getBookQuantity());
        orderBooksTimes.setBookid(bookDTO.getBookId());
        orderBooksTimes.setBookName(bookDTO.getBookName());
        orderBooksTimes.setBookPrice(bookDTO.getBookPrice());
        orderBooksTimes.setOrder(order);
        return orderBooksTimes;
    }

    public static List<OrderBookTimesDTO> converter(List <OrderBooksTimes> orderBooksTimes){
        List<OrderBookTimesDTO> orderBookTimesDTOS = new ArrayList<>();
        for (OrderBooksTimes orderBooksTime : orderBooksTimes) {
            OrderBookTimesDTO orderBookTimesDTO = OrdersBooksTimesConverter.converter(orderBooksTime);
            orderBookTimesDTOS.add(orderBookTimesDTO);
        }
        return orderBookTimesDTOS;
    }
}
