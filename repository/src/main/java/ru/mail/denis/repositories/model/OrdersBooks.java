package ru.mail.denis.repositories.model;

import java.io.Serializable;

/**
 * Created by Denis Monich on 27.07.2017.
 */
public class OrdersBooks implements Serializable {
    private static final long serialVersionUID = -6719451348724897914L;
    private Integer ordersBooksId;
    private String bookName;
    private Integer bookId;
    private String bookPrice;
    private String bookQuantity;
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

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(String bookQUANTITY) {
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
