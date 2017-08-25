package ru.mail.denis.service.DTOmodels;



import ru.mail.denis.repositories.model.Role;
import ru.mail.denis.repositories.model.Status;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by user on 13.07.2017.
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 6109099688333299592L;

    private Integer userId;
    private String userEmail;
    @Size(min = 8)
    private String userPassword;
    private Role userRole;
    private Status userStatus;
    @Size(min = 3, max= 25, message = "must have lenght 3-25 simbols")
    private String userName;
    @Size(min = 3, max= 25, message = "must have lenght 3-25 simbols")
    private String userSurname;
    @Size(min = 3, max= 25, message = "must have lenght 3-25 simbols")
    private String userSecondName;
    @Size(min = 7, max= 15, message = "must have lenght 7-15 simbols")
    private String userPhoneNumber;
    @Size(min = 5, max= 40, message = "must have lenght 5-40 simbols")
    private String userAddress;
    @Size( max= 100, message = "must have lenght 3-100 simbols")
    private String userAdditionalInfo;
    private String repeatePassword;

    public UserDTO() {
    }

    public UserDTO(Integer userId, String userEmail, String userPassword, Role userRole, Status userStatus, String userName, String userSurname, String userSecondName, String userPhoneNumber, String userAddress, String userAdditionalInfo, String repeatePassword) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userSecondName = userSecondName;
        this.userPhoneNumber = userPhoneNumber;
        this.userAddress = userAddress;
        this.userAdditionalInfo = userAdditionalInfo;
        this.repeatePassword = repeatePassword;
    }

    public String getRepeatePassword() {
        return repeatePassword;
    }

    public void setRepeatePassword(String repeatePassword) {
        this.repeatePassword = repeatePassword;
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

    public Status getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Status userStatus) {
        this.userStatus = userStatus;
    }

}
