package ru.mail.denis.repositories.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by user on 28.07.2017.
 */
public class OrderBooksTimes implements Serializable {
    private static final long serialVersionUID = 1342688136445692504L;
    private Integer ordersBooksTimesId;
    private String bookName;
    private Integer bookid;
    private BigDecimal bookPrice;
    private Integer bookQuantity;
    private Order order;

    public Integer getOrdersBooksTimesId() {
        return ordersBooksTimesId;
    }

    public void setOrdersBooksTimesId(Integer ordersBooksTimesId) {
        this.ordersBooksTimesId = ordersBooksTimesId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookTimesNAME) {
        this.bookName = bookTimesNAME;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookTimesID) {
        this.bookid = bookTimesID;
    }

    public BigDecimal getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
    }

    public Integer getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(Integer bookTimesQUANTITY) {
        this.bookQuantity = bookTimesQUANTITY;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}