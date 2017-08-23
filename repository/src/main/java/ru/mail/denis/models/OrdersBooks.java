package ru.mail.denis.models;

import java.io.Serializable;

/**
 * Created by user on 27.07.2017.
 */
public class OrdersBooks implements Serializable {
    private static final long serialVersionUID = -6719451348724897914L;
    private Integer ordersBooksId;
    private String bookName;
    private Integer bookId;
    private Double bookPrice;
    private Integer bookQuantity;
    private Order order;

    public Integer getOrdersBooksId() {
        return ordersBooksId;
    }

    public void setOrdersBooksId(Integer ordersBooksId) {
        this.ordersBooksId = ordersBooksId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookNAME) {
        this.bookName = bookNAME;
    }

    public Double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Double bookPRICE) {
        this.bookPrice = bookPRICE;
    }

    public Integer getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(Integer bookQUANTITY) {
        this.bookQuantity = bookQUANTITY;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookID) {
        this.bookId = bookID;
    }
}
