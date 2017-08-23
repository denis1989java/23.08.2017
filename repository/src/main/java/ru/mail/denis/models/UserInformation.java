package ru.mail.denis.models;

import java.io.Serializable;

/**
 * Created by user on 30.06.2017.
 */
public class UserInformation implements Serializable {

    private static final long serialVersionUID = 3368224509327606009L;

    private Integer userId;
    private User user;
    private String userName;
    private String userSurname;
    private String userSecondName;
    private String userPhoneNumber;
    private String userAddress;
    private String userAdditionalInfo;

    public UserInformation() {
    }

    public UserInformation(Integer userId, User user, String userName, String userSurname, String userSecondName, String userPhoneNumber, String userAddress, String userAdditionalInfo) {
        this.userId = userId;
        this.user = user;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userSecondName = userSecondName;
        this.userPhoneNumber = userPhoneNumber;
        this.userAddress = userAddress;
        this.userAdditionalInfo = userAdditionalInfo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserSecondName() {
        return userSecondName;
    }

    public void setUserSecondName(String userSecondName) {
        this.userSecondName = userSecondName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserAdditionalInfo() {
        return userAdditionalInfo;
    }

    public void setUserAdditionalInfo(String userAdditionalInfo) {
        this.userAdditionalInfo = userAdditionalInfo;
    }


}
