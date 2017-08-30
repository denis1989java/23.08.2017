package ru.mail.denis.service.util;

import ru.mail.denis.repositories.model.User;
import ru.mail.denis.service.model.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis Monich on 24.08.2017.
 */
public class UserConverter {


    public static User converter (UserDTO userDTO){
        User user = new User();
        user.setUserEmail(userDTO.getUserEmail());
        user.setUserStatus(userDTO.getUserStatus());
        user.setUserRole(userDTO.getUserRole());
        user.setUserId(userDTO.getUserId());
        user.setUserPassword(userDTO.getUserPassword());
        return user;
    }

    public static UserDTO converter(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserPassword(user.getUserPassword());
        userDTO.setUserEmail(user.getUserEmail());
        userDTO.setUserStatus(user.getUserStatus());
        userDTO.setUserRole(user.getUserRole());
        userDTO.setUserId(user.getUserId());
        return userDTO;
    }


}
