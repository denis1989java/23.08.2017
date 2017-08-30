package ru.mail.denis.repositories.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Denis Monich on 29.06.2017.
 */
public class User implements Serializable {


    private static final long serialVersionUID = 6882594673502176751L;

    private Integer userId;
    private String userEmail;
    private String userPassword;
    private Role userRole;
    private Status userStatus;
    private UserInformation userInformation;
    private Set<Order> userOrders = new HashSet<Order>();
    private Set<Basket> userBooks = new HashSet<Basket>();

    public User() {
    }

    public User(Integer userId, String userEmail, String userPassword, Role userRole, Status userStatus, UserInformation userInformation, Set<Order> userOrders, Set<Basket> userBooks) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.userInformation = userInformation;
        this.userOrders = userOrders;
        this.userBooks = userBooks;
    }

    public Status getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Status userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public Set<Order> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(Set<Order> userOrders) {
        this.userOrders = userOrders;
    }

    public Set<Basket> getUserBooks() {
        return userBooks;
    }

    public void setUserBooks(Set<Basket> userBooks) {
        this.userBooks = userBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(userEmail, user.userEmail) &&
                Objects.equals(userPassword, user.userPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userEmail, userPassword);
    }
}
