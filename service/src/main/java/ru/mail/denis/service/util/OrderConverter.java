package ru.mail.denis.service.util;

import ru.mail.denis.repositories.model.Order;
import ru.mail.denis.service.model.OrderDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis Monich on 24.08.2017.
 */
public class OrderConverter {

    public static OrderDTO converter(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderPrice(order.getOrderPrice());
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderDelivery(order.getOrderDelivery());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setOrderReceive(order.getOrderReceive());
        orderDTO.setUserId(order.getUser().getUserId());
        return orderDTO;
    }

    public static List<OrderDTO> converter(List <Order> orders){
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = OrderConverter.converter(order);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

}
