package ru.mail.denis.service.modelDTO;

import ru.mail.denis.repositories.model.User;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by user on 27.07.2017.
 */
public class BasketDTO implements Serializable {

    private static final long serialVersionUID = -6176292714261029053L;
    private Integer basketId;
    private Integer bookId;
    private String bookName;
    private Integer bookQuantity;
    private BigDecimal bookPrice;
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

    public Integer getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(Integer bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public BigDecimal getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigDecimal bookPrice) {
        this.bookPrice = bookPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
