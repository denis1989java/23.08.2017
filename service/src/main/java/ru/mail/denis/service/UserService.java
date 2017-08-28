package ru.mail.denis.service;

import ru.mail.denis.service.modelDTO.UserDTO;
import ru.mail.denis.service.modelDTO.UserInformationDTO;
import ru.mail.denis.service.modelDTO.ViewDTO;

/**
 * Created by user on 05.07.2017.
 */
public interface UserService {


    ViewDTO viewPageProfile(String userEmail);

    ViewDTO viewPageAllUsers(Integer page);

    UserDTO getUserDTOByEmail(String email);

    UserInformationDTO getUserinformationDTOByUserId(Integer userId);

    void addUserDTO(UserDTO userDTO);

    void updateUserDTO(UserDTO userDTO);

    void updateUserInformationDTO(UserInformationDTO userInformationDTO, Integer userId);


    void changeUserDTOStatus(String userStatus, Integer userId);


    void changeUserDTORole(String userRole, Integer userId);

    void deleteUserDTO(String[] deletings);
}
