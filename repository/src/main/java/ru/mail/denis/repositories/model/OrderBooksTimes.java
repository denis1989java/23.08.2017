package ru.mail.denis.repositories.model;

import java.io.Serializable;

/**
 * Created by Denis Monich on 28.07.2017.
 */
public class OrderBooksTimes implements Serializable {
    private static final long serialVersionUID = 1342688136445692504L;
    private Integer ordersBooksTimesId;
    private String bookName;
    private Integer bookid;
    private String bookPrice;
    private String bookQuantity;
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

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(String bookTimesQUANTITY) {
        this.bookQuantity = bookTimesQUANTITY;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
