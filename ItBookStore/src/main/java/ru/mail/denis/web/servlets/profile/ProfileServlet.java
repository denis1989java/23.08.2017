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
 * Created by user on 06.07.2017.
 */
public class ProfileServlet extends HttpServlet {
    private UserServiceImpl userService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        UserInformationDTO userInformationDTO = userService.getUserinformationDTOByUserId(userDTO.getUserId());
        req.setAttribute("userDTO", userDTO);
        req.setAttribute("userInformationDTO", userInformationDTO);
        if (userDTO.getUserRole().equals(Role.USER)) {
            req.setAttribute("roleUser", Role.USER);
        } else if (userDTO.getUserRole().equals(Role.ADMIN)) {
            req.setAttribute("roleAdmin", Role.ADMIN);
        } else if (userDTO.getUserRole().equals(Role.SUPER_ADMIN)) {
            req.setAttribute("roleSuperAdmin", Role.SUPER_ADMIN);
        }
        req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
    }
}
