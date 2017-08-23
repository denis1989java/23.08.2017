package ru.mail.denis.web.servlets.catalogue;

import ru.mail.denis.models.Role;
import ru.mail.denis.service.CatalogueServiceImpl;
import ru.mail.denis.service.DTOmodels.BookDTO;
import ru.mail.denis.service.DTOmodels.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by user on 30.07.2017.
 */
public class AddServlet extends HttpServlet {
    private CatalogueServiceImpl catalogueService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");
        req.setAttribute("page", page);
        HttpSession httpSession = req.getSession();
        UserDTO user = (UserDTO) httpSession.getAttribute("user");
        req.setAttribute("user", user);
        if (user.getUserRole().equals(Role.ADMIN)) {
            req.setAttribute("roleAdmin", Role.ADMIN);
        } else if (user.getUserRole().equals(Role.SUPER_ADMIN)) {
            req.setAttribute("roleSuperAdmin", Role.SUPER_ADMIN);
        }
        req.getRequestDispatcher("/WEB-INF/addBook.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String addBook = req.getParameter("addBook");
        if (addBook != null) {
            String page = req.getParameter("page");
            String bookName = req.getParameter("bookName");
            String bookAuthor = req.getParameter("bookAuthor");
            Integer bookQuantity = Integer.valueOf(req.getParameter("bookQuantity"));
            Double bookPrice = Double.valueOf(req.getParameter("bookPrice"));
            String bookDescription = req.getParameter("bookDescription");
            BookDTO bookDTO = new BookDTO();
            bookDTO.setBookQuantity(bookQuantity);
            bookDTO.setBookDescription(bookDescription);
            bookDTO.setBookAuthor(bookAuthor);
            bookDTO.setBookPrice(bookPrice);
            bookDTO.setBookName(bookName);
            catalogueService.saveBook(bookDTO);
            if (req.getRequestURI().contains("admin")) {
                resp.sendRedirect(req.getContextPath() + "/admin/catalogue?page=" + page);
            } else if (req.getRequestURI().contains("superAdmin")) {
                resp.sendRedirect(req.getContextPath() + "/superAdmin/catalogue?page=" + page);
            }
        }
    }
}
