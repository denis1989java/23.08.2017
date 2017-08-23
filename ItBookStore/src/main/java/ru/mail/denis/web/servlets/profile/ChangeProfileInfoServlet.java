package ru.mail.denis.web.servlets.profile;

import ru.mail.denis.models.Role;
import ru.mail.denis.service.UserServiceImpl;
import ru.mail.denis.service.DTOmodels.UserDTO;
import ru.mail.denis.service.DTOmodels.UserInformationDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by user on 08.08.2017.
 */
public class ChangeProfileInfoServlet extends HttpServlet {
    private UserServiceImpl userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        String name = req.getParameter("userName");
        String surname = req.getParameter("userSurname");
        String secondName = req.getParameter("userSecondName");
        String address = req.getParameter("userAddress");
        String additionalInfo = req.getParameter("userAdditionalInfo");
        String error = req.getParameter("error");
        UserInformationDTO userInformationDTO;
        if (name == null) {
            userInformationDTO = userService.getUserinformationDTOByUserId(userDTO.getUserId());
        } else {
            String phoneNumber = "+" + req.getParameter("userPhoneNumber").trim();
            userInformationDTO = setUserInformation(name, surname, secondName, phoneNumber, address, additionalInfo);
            req.setAttribute("error", error);
        }
        req.setAttribute("userDTO", userDTO);
        req.setAttribute("userInformationDTO", userInformationDTO);
        if (userDTO.getUserRole().equals(Role.USER)) {
            req.setAttribute("roleUser", Role.USER);
        } else if (userDTO.getUserRole().equals(Role.ADMIN)) {
            req.setAttribute("roleAdmin", Role.ADMIN);
        } else if (userDTO.getUserRole().equals(Role.SUPER_ADMIN)) {
            req.setAttribute("roleSuperAdmin", Role.SUPER_ADMIN);
        }
        req.getRequestDispatcher("/WEB-INF/changeProfileInfo.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String saveInfo = req.getParameter("saveInfo");
        if (saveInfo != null) {
            String name = req.getParameter("userName");
            String surname = req.getParameter("userSurname");
            String secondName = req.getParameter("userSecondName");
            String email = req.getParameter("userEmail");
            String phoneNumber = req.getParameter("userPhoneNumber");
            String address = req.getParameter("userAddress");
            String additionalInfo = req.getParameter("userAdditionalInfo");
            HttpSession session = req.getSession();
            UserDTO userDTO = (UserDTO) session.getAttribute("user");
            UserDTO userDTO1 = userService.getUserDTOByEmail(email);
            UserInformationDTO userInformationDTO = new UserInformationDTO();
            userInformationDTO.setUserSurname(surname);
            userInformationDTO.setUserSecondName(secondName);
            userInformationDTO.setUserPhoneNumber(phoneNumber);
            userInformationDTO.setUserAddress(address);
            userInformationDTO.setUserAdditionalInfo(additionalInfo);
            userInformationDTO.setUserName(name);
            if (userDTO1 != null) {
                if (userDTO.getUserEmail().equals(email)) {
                    userService.updateUserInformationDTO(userInformationDTO, userDTO.getUserId());
                    if (userDTO.getUserRole().equals(Role.USER)) {
                        resp.sendRedirect(req.getContextPath() + "/user/profile");
                    } else if (userDTO.getUserRole().equals(Role.ADMIN)) {
                        resp.sendRedirect(req.getContextPath() + "/admin/profile");
                    } else if (userDTO.getUserRole().equals(Role.SUPER_ADMIN)) {
                        resp.sendRedirect(req.getContextPath() + "/superAdmin/profile");
                    }
                } else {
                    String error = "User with this e-mail exist allready";
                    if (userDTO.getUserRole().equals(Role.USER)) {
                        resp.sendRedirect(req.getContextPath() + "/user/changeProfileInfo?name=" + name + "&surname=" + surname + "&secondName=" + secondName + "&phoneNumber=" + phoneNumber + "&address=" + address + "&additionalInfo=" + additionalInfo + "&error=" + error);
                    } else if (userDTO.getUserRole().equals(Role.ADMIN)) {
                        resp.sendRedirect(req.getContextPath() + "/admin/changeProfileInfo?name=" + name + "&surname=" + surname + "&secondName=" + secondName + "&phoneNumber=" + phoneNumber + "&address=" + address + "&additionalInfo=" + additionalInfo + "&error=" + error);
                    } else if (userDTO.getUserRole().equals(Role.SUPER_ADMIN)) {
                        resp.sendRedirect(req.getContextPath() + "/superAdmin/changeProfileInfo?name=" + name + "&surname=" + surname + "&secondName=" + secondName + "&phoneNumber=" + phoneNumber + "&address=" + address + "&additionalInfo=" + additionalInfo + "&error=" + error);
                    }

                }
            } else {
                userService.updateUserInformationDTO(userInformationDTO, userDTO.getUserId());
                userDTO.setUserEmail(email);
                userService.updateUserDTO(userDTO);
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/home?success=Login changed successfully");
            }
        }
    }

    private UserInformationDTO setUserInformation(String name, String surname, String secondName, String phoneNumber, String address, String additionalInfo) {
        UserInformationDTO userInformationDTO = new UserInformationDTO();
        userInformationDTO.setUserName(name);
        userInformationDTO.setUserAdditionalInfo(additionalInfo);
        userInformationDTO.setUserAddress(address);
        userInformationDTO.setUserPhoneNumber(phoneNumber);
        userInformationDTO.setUserSecondName(secondName);
        userInformationDTO.setUserSurname(surname);
        return userInformationDTO;
    }
}
