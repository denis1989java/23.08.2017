package ru.mail.denis.service.model;

import ru.mail.denis.repositories.model.Delivery;
import ru.mail.denis.repositories.model.Receive;

import java.io.Serializable;

/**
 * Created by Denis Monich on 28.07.2017.
 */
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = -8679855819657017207L;
    private Integer orderId;
    private String orderPrice;
    private Delivery orderDelivery;
    private Receive orderReceive;
    private String orderDate;
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Delivery getOrderDelivery() {
        return orderDelivery;
    }

    public void setOrderDelivery(Delivery orderDelivery) {
        this.orderDelivery = orderDelivery;
    }

    public Receive getOrderReceive() {
        return orderReceive;
    }

    public void setOrderReceive(Receive orderReceive) {
        this.orderReceive = orderReceive;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
