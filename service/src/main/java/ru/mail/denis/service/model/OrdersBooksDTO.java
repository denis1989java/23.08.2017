package ru.mail.denis.service.model;

import java.io.Serializable;

/**
 * Created by Denis Monich on 28.07.2017.
 */
public class OrdersBooksDTO implements Serializable {
    private static final long serialVersionUID = 261868607798885037L;

    private Integer ordersBooksId;
    private String bookName;
    private Integer bookId;
    private String bookPrice;
    private String bookQuantity;

    public Integer getOrdersBooksId() {
        return ordersBooksId;
    }

    public void setOrdersBooksId(Integer ordersBooksId) {
        this.ordersBooksId = ordersBooksId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
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

    public void setBookQuantity(String bookQuantity) {
        this.bookQuantity = bookQuantity;
    }
}


