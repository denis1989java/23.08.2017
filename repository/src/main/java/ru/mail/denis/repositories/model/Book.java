package ru.mail.denis.repositories.model;

import java.io.Serializable;

/**
 * Created by Denis Monich on 26.05.2017.
 */

public class Book implements Serializable {
    private static final long serialVersionUID = 1411030854187488740L;
    private Integer bookId;
    private String bookName;
    private String bookAuthor;
    private String bookQuantity;
    private String bookPrice;
    private String bookDescription;
    private Changable changable;


    public Book() {
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

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
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

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public Changable getChangable() {
        return changable;
    }

    public void setChangable(Changable changable) {
        this.changable = changable;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookQuantity=" + bookQuantity +
                ", bookPrice=" + bookPrice +
                ", bookDescription='" + bookDescription + '\'' +
                '}';
    }
}
