package ru.mail.denis.service.modelDTO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by user on 28.07.2017.
 */
public class OrderBookTimesDTO implements Serializable {
    private static final long serialVersionUID = -5191602038996254994L;
    private Integer ordersBooksTimesId;
    private String bookName;
    private Integer bookid;
    private BigDecimal bookPrice;
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
