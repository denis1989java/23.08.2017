package ru.mail.denis.service;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.models.Role;
import ru.mail.denis.models.Status;
import ru.mail.denis.models.User;
import ru.mail.denis.models.UserInformation;
import ru.mail.denis.repositories.DAO.UserDAO;
import ru.mail.denis.repositories.DAO.UserInformationDAO;
import ru.mail.denis.service.DTOmodels.UserDTO;
import ru.mail.denis.service.DTOmodels.UserInformationDTO;
import ru.mail.denis.service.util.UserConverter;
import ru.mail.denis.service.util.UserInformationConverter;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final UserInformationDAO userInformationDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UserInformationDAO userInformationDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDAO = userDAO;
        this.userInformationDAO = userInformationDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional()
    public UserDTO getUserDTOByEmail(String email) {
        User user = findByEmail(email);
        if (user != null) {
            return UserConverter.converter(user);
        } else {
            return null;
        }

    }

    @Override
    @Transactional()
    public UserInformationDTO getUserinformationDTOByUserId(Integer userId) {
        UserInformation userInformation = getUserInformationByUserId(userId);
        if (userInformation != null) {
            return UserInformationConverter.converter(userInformation);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void addUserDTO(UserDTO userDTO) {
        User user = UserConverter.converter(userDTO);
        user.setUserPassword(bCryptPasswordEncoder.encode(userDTO.getUserPassword()));
        UserInformation userInformation = UserInformationConverter.converter(userDTO);
        user.setUserInformation(userInformation);
        userInformation.setUser(user);
        saveUser(user);
    }

    @Override
    @Transactional
    public void updateUserDTO(UserDTO userDTO) {
        User user = UserConverter.converter(userDTO);
        user.setUserPassword(bCryptPasswordEncoder.encode(userDTO.getUserPassword()));
        updateUser(user);
    }

    @Override
    @Transactional
    public void updateUserInformationDTO(UserInformationDTO userInformationDTO, Integer userId) {
        UserInformation userInformation = getUserInformationByUserId(userId);
        userInformation=UserInformationConverter.setUserInformation(userInformation,userInformationDTO);
        updateUserInformation(userInformation);
    }

    @Override
    @Transactional()
    public List<UserDTO> getUsersDTO(int pageId, int total) {
        return UserConverter.converter(getUsersByParts(pageId, total));
    }


    @Override
    @Transactional()
    public Integer usersDTOQuantity() {
        return findAll().size();
    }

    @Override
    @Transactional()
    public void changeUserDTOStatus(Status status, Integer userId) {
        User user = findById(userId);
        user.setUserStatus(status);
        updateUser(user);
    }

    @Override
    @Transactional()
    public void changeUserDTORole(Role role, Integer userId) {
        User user = findById(userId);
        user.setUserRole(role);
        updateUser(user);
    }

    @Override
    @Transactional()
    public void deleteUserDTO(Integer userId) {
        delete(findById(userId));
    }


    private UserInformation getUserInformationByUserId(Integer userId) {
        logger.debug("Getting userInformation by User id");
        UserInformation userInformation = userInformationDAO.findUserInformationByUserId(userId);
        return userInformation;

    }

    private List<User> findAll() {
        logger.debug("Finding all users");
        List<User> users = userDAO.findAll();
        return users;
    }

    private void delete(User user) {
        logger.debug("Deleting user");
        userDAO.delete(user);
    }

    private List<User> getUsersByParts(int pageId, int total) {
        logger.debug("Getting Users by parts");
        List<User> users = userDAO.getUsersByParts(pageId, total);
        return users;
    }


    private void saveUser(User user) {
        logger.debug("Saving new User");
        userDAO.save(user);
    }

    private void updateUserInformation(UserInformation userInformation) {
        logger.debug("Updating UserInformation");
        userInformationDAO.update(userInformation);
    }

    private void updateUser(User user) {
        logger.debug("Updating User");
        userDAO.update(user);
    }

    private User findById(Integer id) {
        logger.debug("Finding User by id");
        User user = userDAO.findById(id);
        return user;
    }

    private User findByEmail(String email) {
        logger.debug("Finding User by email");
        User user = userDAO.findByEmail(email);
        return user;
    }
}
