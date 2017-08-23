package ru.mail.denis.web.servlets.profile;

import ru.mail.denis.models.Role;
import ru.mail.denis.service.UserServiceImpl;
import ru.mail.denis.service.DTOmodels.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by user on 08.08.2017.
 */
public class ChangePasswordServlet extends HttpServlet {
    private UserServiceImpl userService ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String error = req.getParameter("error");
        req.setAttribute("error", error);
        if (req.getRequestURI().contains("user")) {
            req.setAttribute("roleUser", Role.USER);
        } else if (req.getRequestURI().contains("admin")) {
            req.setAttribute("roleAdmin", Role.ADMIN);
        } else if (req.getRequestURI().contains("superAdmin")) {
            req.setAttribute("roleSuperAdmin", Role.SUPER_ADMIN);
        }
        req.getRequestDispatcher("/WEB-INF/changePassword.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String savePassword = req.getParameter("savePassword");
        if (savePassword != null) {
            String password = req.getParameter("userPassword");
            String newPassword = req.getParameter("newPassword");
            String repeatPassword = req.getParameter("repeatPassword");
            HttpSession session = req.getSession();
            UserDTO userDTO = (UserDTO) session.getAttribute("user");
            if (userDTO.getUserPassword().equals(password)) {
                if (newPassword.equals(repeatPassword)) {
                    userDTO.setUserPassword(newPassword);
                    userService.updateUserDTO(userDTO);
                    if (userDTO.getUserRole().equals(Role.USER)) {
                        resp.sendRedirect(req.getContextPath() + "/user/profile");
                    } else if (userDTO.getUserRole().equals(Role.ADMIN)) {
                        resp.sendRedirect(req.getContextPath() + "/admin/profile");
                    } else if (userDTO.getUserRole().equals(Role.SUPER_ADMIN)) {
                        resp.sendRedirect(req.getContextPath() + "/superAdmin/profile");
                    }
                } else {
                    String error = "Repeat password entered incorrectly";
                    if (userDTO.getUserRole().equals(Role.USER)) {
                        resp.sendRedirect(req.getContextPath() + "/user/changePassword?error=" + error);
                    } else if (userDTO.getUserRole().equals(Role.ADMIN)) {
                        resp.sendRedirect(req.getContextPath() + "/admin/changePassword?error=" + error);
                    } else if (userDTO.getUserRole().equals(Role.SUPER_ADMIN)) {
                        resp.sendRedirect(req.getContextPath() + "/superAdmin/changePassword?error=" + error);
                    }
                }
            } else {
                String error = "Password entered incorrectly";
                if (userDTO.getUserRole().equals(Role.USER)) {
                    resp.sendRedirect(req.getContextPath() + "/user/changePassword?error=" + error);
                } else if (userDTO.getUserRole().equals(Role.ADMIN)) {
                    resp.sendRedirect(req.getContextPath() + "/admin/changePassword?error=" + error);
                } else if (userDTO.getUserRole().equals(Role.SUPER_ADMIN)) {
                    resp.sendRedirect(req.getContextPath() + "/superAdmin/changePassword?error=" + error);
                }
            }
        }
    }
}