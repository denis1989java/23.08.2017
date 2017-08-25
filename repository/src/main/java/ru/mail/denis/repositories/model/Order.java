package ru.mail.denis.repositories.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * Created by user on 21.06.2017.
 */
public class Order implements Serializable {

    private static final long serialVersionUID = -2160067623152258782L;

    private Integer orderId;
    private BigDecimal orderPrice;
    private User user;
    private Delivery orderDelivery;
    private Receive orderReceive;
    private Set<OrdersBooks> orderBooks;
    private Set<OrderBooksTimes> orderBooksTimes;
    private String orderDate;

    public Order() {
    }

    public Set<OrderBooksTimes> getOrderBooksTimes() {
        return orderBooksTimes;
    }

    public void setOrderBooksTimes(Set<OrderBooksTimes> orderBooksTimes) {
        this.orderBooksTimes = orderBooksTimes;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Set<OrdersBooks> getOrderBooks() {
        return orderBooks;
    }

    public void setOrderBooks(Set<OrdersBooks> orderBooks) {
        this.orderBooks = orderBooks;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderPrice=" + orderPrice +
                ", user=" + user +
                ", orderDelivery=" + orderDelivery +
                ", orderReceive=" + orderReceive +
                ", orderBooks=" + orderBooks +
                ", orderDate=" + orderDate +
                '}';
    }
}
