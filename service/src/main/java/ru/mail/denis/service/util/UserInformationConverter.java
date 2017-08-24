package ru.mail.denis.service.util;

import ru.mail.denis.models.UserInformation;
import ru.mail.denis.service.DTOmodels.UserDTO;
import ru.mail.denis.service.DTOmodels.UserInformationDTO;

/**
 * Created by user on 24.08.2017.
 */
public class UserInformationConverter {

    public UserInformationConverter() {
    }

    public static UserInformation converter (UserInformationDTO userInformationDTO){
        UserInformation userInformation = new UserInformation();
        userInformation.setUserAddress(userInformationDTO.getUserAddress());
        userInformation.setUserAdditionalInfo(userInformationDTO.getUserAdditionalInfo());
        userInformation.setUserSurname(userInformationDTO.getUserSurname());
        userInformation.setUserPhoneNumber(userInformationDTO.getUserPhoneNumber());
        userInformation.setUserSecondName(userInformationDTO.getUserSecondName());
        userInformation.setUserName(userInformationDTO.getUserName());
        return userInformation;
    }

    public static UserInformationDTO converter (UserInformation userInformation){
        UserInformationDTO userInformationDTO = new UserInformationDTO();
        userInformationDTO.setUserAdditionalInfo(userInformation.getUserAdditionalInfo());
        userInformationDTO.setUserAddress(userInformation.getUserAddress());
        userInformationDTO.setUserName(userInformation.getUserName());
        userInformationDTO.setUserPhoneNumber(userInformation.getUserPhoneNumber());
        userInformationDTO.setUserSecondName(userInformation.getUserSecondName());
        userInformationDTO.setUserSurname(userInformation.getUserSurname());
        return userInformationDTO;
    }

    public static UserInformation converter (UserDTO userDTO){
        UserInformation userInformation = new UserInformation();
        userInformation.setUserAddress(userDTO.getUserAddress());
        userInformation.setUserAdditionalInfo(userDTO.getUserAdditionalInfo());
        userInformation.setUserSurname(userDTO.getUserSurname());
        userInformation.setUserPhoneNumber(userDTO.getUserPhoneNumber());
        userInformation.setUserSecondName(userDTO.getUserSecondName());
        userInformation.setUserName(userDTO.getUserName());
        return userInformation;
    }
    public static UserInformation setUserInformation(UserInformation userInformation, UserInformationDTO userInformationDTO){
        userInformation.setUserAddress(userInformationDTO.getUserAddress());
        userInformation.setUserAdditionalInfo(userInformationDTO.getUserAdditionalInfo());
        userInformation.setUserSurname(userInformationDTO.getUserSurname());
        userInformation.setUserPhoneNumber(userInformationDTO.getUserPhoneNumber());
        userInformation.setUserSecondName(userInformationDTO.getUserSecondName());
        userInformation.setUserName(userInformationDTO.getUserName());
        return userInformation;
    }

}
