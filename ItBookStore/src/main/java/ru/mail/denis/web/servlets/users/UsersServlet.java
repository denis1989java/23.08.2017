package ru.mail.denis.web.servlets.users;

import ru.mail.denis.models.Role;
import ru.mail.denis.models.Status;
import ru.mail.denis.service.UserServiceImpl;
import ru.mail.denis.service.DTOmodels.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 08.08.2017.
 */
public class UsersServlet extends HttpServlet {
    UserServiceImpl userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String spageId = req.getParameter("page");
        int pageId = Integer.parseInt(spageId);
        int total = 7;
        if (pageId != 0) {
            pageId = pageId * total;
        }
        List<UserDTO> userDTOS = userService.getUsersDTO(pageId, total);
        req.setAttribute("users", userDTOS);
        Integer newsQuantity = userService.usersDTOQuantity();
        req.setAttribute("page", spageId);
        List<Integer> pagination = new ArrayList();
        Integer pageQuantity = 0;
        if (newsQuantity % total == 0) {
            pageQuantity = newsQuantity / total;
        } else {
            pageQuantity = newsQuantity / total + 1;
        }
        for (Integer i = 0; i < pageQuantity; i++) {
            pagination.add(i);
        }
        req.setAttribute("pagination", pagination);

        req.getRequestDispatcher("/WEB-INF/users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = Integer.valueOf(req.getParameter("userId"));
        String page = req.getParameter("page");
        String changeStatus = req.getParameter("changeStatus");
        if (changeStatus != null) {
            String userStatus = req.getParameter("userStatus");
            Status status = null;
            if (userStatus.equals("ACTIVE")) {
                status = Status.ACTIVE;
            } else if (userStatus.equals("BLOCKED")) {
                status = Status.BLOCKED;
            }
            userService.changeUserDTOStatus(status, userId);
            resp.sendRedirect(req.getContextPath() + "/superAdmin/users?page=" + page);
        }
        String changeRole = req.getParameter("changeRole");
        if (changeRole != null) {
            String userRole = req.getParameter("userRole");
            Role role = null;
            if (userRole.equals("USER")) {
                role = Role.USER;
            } else if (userRole.equals("ADMIN")) {
                role = Role.ADMIN;
            } else if (userRole.equals("SUPER_ADMIN")) {
                role = Role.SUPER_ADMIN;
            }
            userService.changeUserDTORole(role, userId);
            resp.sendRedirect(req.getContextPath() + "/superAdmin/users?page=" + page);
        }
        String delete = req.getParameter("delete");
        if (delete != null) {
            String[] deletings = req.getParameterValues("deleting");
            if (deletings == null) {
                resp.sendRedirect(req.getContextPath() + "/superAdmin/users?page=" + page);
            } else {
                for (int i = 0; i < deletings.length; i++) {
                    Integer userId1 = Integer.valueOf(deletings[i]);
                    userService.deleteUserDTO(userId1);
                }
                resp.sendRedirect(req.getContextPath() + "/superAdmin/users?page=" + page);
            }
        }
    }
}
