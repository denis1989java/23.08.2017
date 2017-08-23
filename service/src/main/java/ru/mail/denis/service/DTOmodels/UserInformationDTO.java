package ru.mail.denis.service.DTOmodels;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * Created by user on 13.07.2017.
 */
public class UserInformationDTO implements Serializable {

    private String userName;
    private String userSurname;
    private String userSecondName;
    private String userPhoneNumber;
    private String userAddress;
    private String userAdditionalInfo;

    public UserInformationDTO() {
    }

    public UserInformationDTO(String userName, String userSurname, String userSecondName, String userPhoneNumber, String userAddress, String userAdditionalInfo) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.userSecondName = userSecondName;
        this.userPhoneNumber = userPhoneNumber;
        this.userAddress = userAddress;
        this.userAdditionalInfo = userAdditionalInfo;
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
