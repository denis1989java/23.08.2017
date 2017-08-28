package ru.mail.denis.service.util;

import ru.mail.denis.repositories.model.Order;
import ru.mail.denis.service.modelDTO.OrderDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 24.08.2017.
 */
public class OrderConverter {
    public OrderConverter() {
    }

    public static Order converter (OrderDTO orderDTO){
        Order order = new Order();
        order.setOrderDelivery(orderDTO.getOrderDelivery());
        order.setOrderPrice(orderDTO.getOrderPrice());
        order.setOrderReceive(orderDTO.getOrderReceive());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setOrderId(orderDTO.getOrderId());
        return order;
    }

    public static OrderDTO converter(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderPrice(order.getOrderPrice());
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderDelivery(order.getOrderDelivery());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setOrderReceive(order.getOrderReceive());
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
