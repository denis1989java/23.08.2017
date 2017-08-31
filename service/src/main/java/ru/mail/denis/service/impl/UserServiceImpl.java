package ru.mail.denis.service.impl;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.repositories.*;
import ru.mail.denis.repositories.model.*;
import ru.mail.denis.service.model.AppUserPrincipal;
import ru.mail.denis.service.model.UserDTO;
import ru.mail.denis.service.model.UserInformationDTO;
import ru.mail.denis.service.model.ViewDTO;
import ru.mail.denis.service.UserService;
import ru.mail.denis.service.util.UserConverter;
import ru.mail.denis.service.util.UserInformationConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Denis Monich on 07.07.2017.
 */

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
        int pageNumber=page;
        if (page != 0) {
            page = page * total;
        }
        List<UserDTO> userDTOS = converter(getUsersByParts(page, total));
       if (userDTOS.isEmpty()){
           page=0;
           userDTOS=converter(getUsersByParts(page, total));
       }
        Long usersDTOQuantity = userQuantity();
        List<Long> pagination = new ArrayList();
       Long pageQuantity = Long.valueOf(0);
        if (usersDTOQuantity % total == 0) {
            pageQuantity = usersDTOQuantity / total;
        } else {
            pageQuantity = usersDTOQuantity / total + 1;
        }
        for (Long i = Long.valueOf(0); i < pageQuantity; i++) {
            pagination.add(i);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("page",pageNumber);
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
    public UserInformationDTO getUserinformationDTOByUserId() {
        UserDTO userDTO=getUser();
        UserInformation userInformation = getUserInformationByUserId(userDTO.getUserId());
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
        UserInformation userInformation = converter(userDTO);
        user.setUserInformation(userInformation);
        saveUser(user);
    }

    @Override
    @Transactional
    public void updateUserDTO(String newPassword) {
       UserDTO userDTO=getUser();
        userDTO.setUserPassword(bCryptPasswordEncoder.encode(newPassword));
        mergeUser(UserConverter.converter(userDTO));
    }

    @Override
    @Transactional
    public void updateUserInformationDTO(UserInformationDTO userInformationDTO) {
        UserDTO userDTO=getUser();
        UserInformation userInformation = getUserInformationByUserId(userDTO.getUserId());
        userInformation = setUserInformation(userInformation, userInformationDTO);
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


    @Override
    @Transactional
    public boolean checkPassword(String userPassword){
        UserDTO userDTO=getUser();
        if (BCrypt.checkpw(userPassword, userDTO.getUserPassword())) {
            return true;
        }else {
            return false;
        }
    }


    private UserInformation getUserInformationByUserId(Integer userId) {
        logger.debug("Getting userInformation by User id"+userId);
        UserInformation userInformation = userInformationDAO.findUserInformationByUserId(userId);
        return userInformation;

    }

    private void delete(User user) {
        logger.debug("Deleting user with Id" + user.getUserId());
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
        logger.debug("Updating UserInformation with user Id "+ userInformation.getUser().getUserId());
        userInformationDAO.update(userInformation);
    }

    private void updateUser(User user) {
        logger.debug("Updating User");
        userDAO.update(user);
    }

    private void mergeUser(User user){
        userDAO.merge(user);
    }

    private User findById(Integer id) {
        logger.debug("Finding User by id"+ id);
        User user = userDAO.findById(id);
        return user;
    }

    private User findByEmail(String email) {
        logger.debug("Finding User by email"+ email);
        User user = userDAO.findByEmail(email);
        return user;
    }
    private Long userQuantity () {
        logger.debug("Finding users quantity");
        Long quantity = userDAO.getUsersQuantity();
        return quantity;
    }
    private UserDTO getUser() {
        AppUserPrincipal principal = (AppUserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return UserConverter.converter(userDAO.findByEmail(principal.getUsername())) ;
    }
    private List<UserDTO> converter(List <User> users){
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = UserConverter.converter(user);
            usersDTO.add(userDTO);
        }
        return usersDTO;
    }
    private UserInformation converter (UserDTO userDTO){
        UserInformation userInformation = new UserInformation();
        userInformation.setUserAddress(userDTO.getUserAddress());
        userInformation.setUserAdditionalInfo(userDTO.getUserAdditionalInfo());
        userInformation.setUserSurname(userDTO.getUserSurname());
        userInformation.setUserPhoneNumber(userDTO.getUserPhoneNumber());
        userInformation.setUserSecondName(userDTO.getUserSecondName());
        userInformation.setUserName(userDTO.getUserName());
        return userInformation;
    }
    private UserInformation setUserInformation(UserInformation userInformation, UserInformationDTO userInformationDTO){
        userInformation.setUserAddress(userInformationDTO.getUserAddress());
        userInformation.setUserAdditionalInfo(userInformationDTO.getUserAdditionalInfo());
        userInformation.setUserSurname(userInformationDTO.getUserSurname());
        userInformation.setUserPhoneNumber(userInformationDTO.getUserPhoneNumber());
        userInformation.setUserSecondName(userInformationDTO.getUserSecondName());
        userInformation.setUserName(userInformationDTO.getUserName());
        return userInformation;
    }
}
