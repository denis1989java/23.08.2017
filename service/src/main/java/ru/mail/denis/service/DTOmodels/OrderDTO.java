package ru.mail.denis.service.DTOmodels;

import ru.mail.denis.models.Delivery;
import ru.mail.denis.models.Receive;

import java.io.Serializable;

/**
 * Created by user on 28.07.2017.
 */
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = -8679855819657017207L;
    private Integer orderId;
    private Double orderPrice;
    private Delivery orderDelivery;
    private Receive orderReceive;
    private String orderDate;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
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
