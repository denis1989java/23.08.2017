package ru.mail.denis.models;

import java.io.Serializable;

/**
 * Created by user on 13.07.2017.
 */
public class Basket implements Serializable {
    private static final long serialVersionUID = 2011429622027669449L;
    private Integer BasketId;
    private Integer bookId;
    private String bookName;
    private Integer bookQuantity;
    private Double bookPrice;
    private User user;

    public Integer getBasketId() {
        return BasketId;
    }

    public void setBasketId(Integer basketId) {
        BasketId = basketId;
    }

    public Basket() {

    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }


    public Integer getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(Integer bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public Double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "BasketId=" + BasketId +
                ", bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookQuantity=" + bookQuantity +
                ", bookPrice=" + bookPrice +
                ", user=" + user +
                '}';
    }
}
