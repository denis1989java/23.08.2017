package ru.mail.denis.service.DTOmodels;

import java.io.Serializable;

/**
 * Created by user on 28.07.2017.
 */
public class OrderBookTimesDTO implements Serializable {
    private static final long serialVersionUID = -5191602038996254994L;
    private Integer ordersBooksTimesId;
    private String bookName;
    private Integer bookid;
    private Double bookPrice;
    private Integer bookQuantity;

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

    public Double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public Integer getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(Integer bookQuantity) {
        this.bookQuantity = bookQuantity;
    }
}
