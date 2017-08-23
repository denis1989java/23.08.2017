package ru.mail.denis.web.servlets.news;

import ru.mail.denis.models.Role;
import ru.mail.denis.service.NewServiceImpl;
import ru.mail.denis.service.DTOmodels.NewsDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by user on 25.06.2017.
 */
public class NewServlet extends HttpServlet {
    NewServiceImpl newService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");
        Integer newsId = Integer.parseInt(req.getParameter("newsId"));
        NewsDTO news = newService.getNewById(newsId);
        req.setAttribute("news", news);
        req.setAttribute("page", page);
        req.setAttribute("path",news.getNewsFoto());
        if (req.getRequestURI().contains("user")) {
            req.setAttribute("roleUser", Role.USER);
        } else if (req.getRequestURI().contains("admin")) {
            req.setAttribute("roleAdmin", Role.ADMIN);
        } else if (req.getRequestURI().contains("superAdmin")) {
            req.setAttribute("roleSuperAdmin", Role.SUPER_ADMIN);
        }
        req.getRequestDispatcher("/WEB-INF/new.jsp").forward(req, resp);

    }
}
