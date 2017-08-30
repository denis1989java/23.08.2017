package ru.mail.denis.service.model;

import java.io.Serializable;

/**
 * Created by Denis Monich on 28.07.2017.
 */
public class OrderBookTimesDTO implements Serializable {
    private static final long serialVersionUID = -5191602038996254994L;
    private Integer ordersBooksTimesId;
    private String bookName;
    private Integer bookid;
    private String bookPrice;
    private String bookQuantity;

    public Integer getOrdersBooksTimesId() {
        return ordersBooksTimesId;
    }

    public void setOrdersBooksTimesId(Integer ordersBooksTimesId) {
        this.ordersBooksTimesId = ordersBooksTimesId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
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
