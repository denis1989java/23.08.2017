package ru.mail.denis.service;

import ru.mail.denis.service.model.UserDTO;
import ru.mail.denis.service.model.UserInformationDTO;
import ru.mail.denis.service.model.ViewDTO;

/**
 * Created by Denis Monich on 05.07.2017.
 */
public interface UserService {


    ViewDTO viewPageProfile(String userEmail);

    ViewDTO viewPageAllUsers(Integer page);

    UserDTO getUserDTOByEmail(String email);

    UserInformationDTO getUserinformationDTOByUserId();

    void addUserDTO(UserDTO userDTO);

    void updateUserDTO(String newPasword);

    void updateUserInformationDTO(UserInformationDTO userInformationDTO);


    void changeUserDTOStatus(String userStatus, Integer userId);


    void changeUserDTORole(String userRole, Integer userId);

    void deleteUserDTO(String[] deletings);

    boolean checkPassword(String userPassword);
}
