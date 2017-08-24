package ru.mail.denis.service;

import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.models.Role;
import ru.mail.denis.models.Status;
import ru.mail.denis.models.User;
import ru.mail.denis.service.DTOmodels.UserDTO;
import ru.mail.denis.service.DTOmodels.UserInformationDTO;

import java.util.List;

/**
 * Created by user on 05.07.2017.
 */
public interface UserService {

    UserDTO getUserDTOByEmail(String email);

    UserInformationDTO getUserinformationDTOByUserId(Integer userId);

    void addUserDTO(UserDTO userDTO);

    void updateUserDTO(UserDTO userDTO);

    void updateUserInformationDTO(UserInformationDTO userInformationDTO, Integer userId);

    List<UserDTO> getUsersDTO(int pageId, int total);

    Integer usersDTOQuantity();

    void changeUserDTOStatus(Status status, Integer userId);

    void changeUserDTORole(Role role, Integer userId);

    void deleteUserDTO(Integer userId);

}
