package ru.mail.denis.service.util;

import ru.mail.denis.repositories.model.UserInformation;
import ru.mail.denis.service.model.UserDTO;
import ru.mail.denis.service.model.UserInformationDTO;

/**
 * Created by Denis Monich on 24.08.2017.
 */
public class UserInformationConverter {

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



}
