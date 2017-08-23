package ru.mail.denis.web.servlets.news;

import ru.mail.denis.models.Role;
import ru.mail.denis.service.NewServiceImpl;
import ru.mail.denis.service.DTOmodels.NewsDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 01.08.2017.
 */


public class ChangeServlet extends HttpServlet {
    NewServiceImpl newService ;




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer newsId = Integer.parseInt(req.getParameter("newsId"));
        String page = req.getParameter("page");
        NewsDTO newsDTO = newService.getNewById(newsId);
        req.setAttribute("news", newsDTO);
        req.setAttribute("page", page);
        if (req.getRequestURI().contains("admin")) {
            req.setAttribute("roleAdmin", Role.ADMIN);
        } else if (req.getRequestURI().contains("superAdmin")) {
            req.setAttribute("roleSuperAdmin", Role.SUPER_ADMIN);
        }
        req.getRequestDispatcher("/WEB-INF/changeNew.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String change = req.getParameter("change");
        if (change != null) {
            String page = req.getParameter("page");
            Integer newsId = Integer.valueOf(req.getParameter("newsId"));
            String newsName = req.getParameter("newsName");
            String newsText = req.getParameter("newsText");
            NewsDTO newsDTO = new NewsDTO();
            newsDTO.setNewsName(newsName);
            newsDTO.setNewsId(newsId);
            newsDTO.setNewsText(newsText);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            newsDTO.setNewsDate(dateFormat.format(date));
            newService.updateNew(newsDTO);
            if (req.getRequestURI().contains("admin")) {
                resp.sendRedirect(req.getContextPath() + "/admin/news?page=" + page);
            } else if (req.getRequestURI().contains("superAdmin")) {
                resp.sendRedirect(req.getContextPath() + "/superAdmin/news?page=" + page);
            }
        }
    }
}
