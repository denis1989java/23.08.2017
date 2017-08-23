package ru.mail.denis.web.servlets.news;


import ru.mail.denis.models.Role;
import ru.mail.denis.service.DTOmodels.NewsDTO;
import ru.mail.denis.service.NewServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 31.07.2017.
 */

public class AddServlet extends HttpServlet {

    private NewServiceImpl newService ;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("page", req.getParameter("page"));
        HttpSession httpSession = req.getSession();
        if (req.getRequestURI().contains("admin")) {
            req.setAttribute("roleAdmin", Role.ADMIN);
        } else if (req.getRequestURI().contains("superAdmin")) {
            req.setAttribute("roleSuperAdmin", Role.SUPER_ADMIN);
        }
        req.getRequestDispatcher("/WEB-INF/addNew.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String addNew = req.getParameter("addNew");
        if(addNew!=null){
            String newName = req.getParameter("newName");
            String newText = req.getParameter("newText");
            NewsDTO news = new NewsDTO();
            news.setNewsText(newText);
            news.setNewsName(newName);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            news.setNewsDate(dateFormat.format(date));
            Part partImage = req.getPart("file");
            String part = partImage.getHeader("content-disposition");
            System.out.println(part);
            DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
            String fileName = dateFormat1.format(date).toString()+".png";
            InputStream fileData = partImage.getInputStream();
            File fileuploaded = new File(getServletContext().getRealPath("/")+"/resources/images/");
            File file = new File(fileuploaded, fileName);
            try {
                Files.copy(fileData, file.toPath());
            } catch (Exception e) {
                System.out.println("Error");
            }
            news.setNewsFoto(fileName);
            newService.addNew(news);
            if (req.getRequestURI().contains("admin")) {
                resp.sendRedirect(req.getContextPath() + "/admin/news?page=0");
            } else if (req.getRequestURI().contains("superAdmin")) {
                resp.sendRedirect(req.getContextPath() + "/superAdmin/news?page=0");
            }
        }
    }
}



