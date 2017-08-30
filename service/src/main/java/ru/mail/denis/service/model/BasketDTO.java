package ru.mail.denis.service.model;

import ru.mail.denis.repositories.model.User;

import java.io.Serializable;

/**
 * Created by Denis Monich on 27.07.2017.
 */
public class BasketDTO implements Serializable {

    private static final long serialVersionUID = -6176292714261029053L;
    private Integer basketId;
    private Integer bookId;
    private String bookName;
    private String bookQuantity;
    private String bookPrice;
    private User user;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getBasketId() {
        return basketId;
    }

    public void setBasketId(Integer basketId) {
        this.basketId = basketId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(String bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
