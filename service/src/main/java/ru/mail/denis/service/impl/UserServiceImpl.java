package ru.mail.denis.service.impl;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.repositories.*;
import ru.mail.denis.repositories.model.*;
import ru.mail.denis.service.modelDTO.UserDTO;
import ru.mail.denis.service.modelDTO.UserInformationDTO;
import ru.mail.denis.service.modelDTO.ViewDTO;
import ru.mail.denis.service.UserService;
import ru.mail.denis.service.util.UserConverter;
import ru.mail.denis.service.util.UserInformationConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final UserInformationDAO userInformationDAO;
    private final CatalogueDAO catalogueDAO;
    private final OrdersBooksDAO ordersBooksDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UserInformationDAO userInformationDAO, CatalogueDAO catalogueDAO, OrdersBooksDAO ordersBooksDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDAO = userDAO;
        this.userInformationDAO = userInformationDAO;
        this.catalogueDAO = catalogueDAO;
        this.ordersBooksDAO = ordersBooksDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public ViewDTO viewPageProfile(String userEmail) {
        UserDTO userDTO = UserConverter.converter(findByEmail(userEmail));
        UserInformationDTO userInformationDTO = UserInformationConverter.converter(getUserInformationByUserId(userDTO.getUserId()));
        Map<String, Object> map = new HashMap<>();
        map.put("userDTO", userDTO);
        map.put("userInformationDTO", userInformationDTO);
        ViewDTO viewDTO=new ViewDTO();
        viewDTO.setViewMap(map);
        return viewDTO;
    }

   @Override
   @Transactional
    public ViewDTO viewPageAllUsers(Integer page) {
        int total = 7;
        if (page != 0) {
            page = page * total;
        }
        List<UserDTO> userDTOS = UserConverter.converter(getUsersByParts(page, total));
        Integer usersDTOQuantity = findAll().size();
        List<Integer> pagination = new ArrayList();
        Integer pageQuantity = 0;
        if (usersDTOQuantity % total == 0) {
            pageQuantity = usersDTOQuantity / total;
        } else {
            pageQuantity = usersDTOQuantity / total + 1;
        }
        for (Integer i = 0; i < pageQuantity; i++) {
            pagination.add(i);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("users", userDTOS);
        map.put("pagination", pagination);
        ViewDTO viewDTO=new ViewDTO();
        viewDTO.setViewMap(map);
        return viewDTO;

    }


    @Override
    @Transactional
    public UserDTO getUserDTOByEmail(String email) {
        User user = findByEmail(email);
        if (user != null) {
            return UserConverter.converter(user);
        } else {
            return null;
        }

    }

    @Override
    @Transactional
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
        userDTO.setUserRole(Role.USER);
        userDTO.setUserStatus(Status.ACTIVE);
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
        userInformation = UserInformationConverter.setUserInformation(userInformation, userInformationDTO);
        updateUserInformation(userInformation);
    }


    @Override
    @Transactional
    public void changeUserDTOStatus(String userStatus, Integer userId) {
        Status status = null;
        if (userStatus.equals("ACTIVE")) {
            status = Status.ACTIVE;
        } else if (userStatus.equals("BLOCKED")) {
            status = Status.BLOCKED;
        }
        User user = findById(userId);
        user.setUserStatus(status);
        updateUser(user);
    }

    @Override
    @Transactional
    public void changeUserDTORole(String userRole, Integer userId) {
        Role role = null;
        if (userRole.equals("USER")) {
            role = Role.USER;
        } else if (userRole.equals("ADMIN")) {
            role = Role.ADMIN;
        } else if (userRole.equals("SUPER_ADMIN")) {
            role = Role.SUPER_ADMIN;
        }
        User user = findById(userId);
        user.setUserRole(role);
        updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUserDTO(String[] deletings) {
        for (String deleting : deletings) {
            Integer userId = Integer.valueOf(deleting);
            User user = findById(userId);
            List<OrdersBooks> ordersBooks = ordersBooksDAO.getOrdersBooksByUserId(userId);
            for (OrdersBooks ordersBook : ordersBooks) {
                ordersBooksDAO.delete(ordersBook);
                List<OrdersBooks> ordersBooks2 = ordersBooksDAO.getOrdersBooksByBookId(ordersBook.getBookId());
                Book book = catalogueDAO.findById(ordersBook.getBookId());
                if (ordersBooks2.isEmpty()) {
                    book.setChangable(Changable.CHANGABLE);
                } else {
                    book.setChangable(Changable.NOT_CHANGABLE);
                }
            }
            delete(user);
        }
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
