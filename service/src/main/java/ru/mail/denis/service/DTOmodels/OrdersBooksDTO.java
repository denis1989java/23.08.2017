package ru.mail.denis.service.DTOmodels;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by user on 28.07.2017.
 */
public class OrdersBooksDTO implements Serializable {
    private static final long serialVersionUID = 261868607798885037L;

    private Integer ordersBooksId;
    private String bookName;
    private Integer bookId;
    private BigDecimal bookPrice;
    private Integer bookQuantity;

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

    public BigDecimal getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
    }

    public Integer getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(Integer bookQuantity) {
        this.bookQuantity = bookQuantity;
    }
}


