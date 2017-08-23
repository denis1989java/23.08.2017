package ru.mail.denis.web.servlets.catalogue;

import ru.mail.denis.models.Role;
import ru.mail.denis.service.CatalogueServiceImpl;
import ru.mail.denis.service.DTOmodels.BookDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by user on 30.07.2017.
 */
public class ChangeServlet extends HttpServlet {
    private CatalogueServiceImpl catalogueService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer bookId = Integer.valueOf(req.getParameter("bookId"));
        String page = req.getParameter("page");
        String copy = req.getParameter("copy");
        String change = req.getParameter("update");
        BookDTO bookDTO = catalogueService.getBookById(bookId);
        req.setAttribute("book", bookDTO);
        req.setAttribute("page", page);
        if (copy != null) {
            req.setAttribute("pageName", "Copy Book");
            req.setAttribute("button", 1);
        }
        if (change != null) {
            req.setAttribute("pageName", "Change Book");
            req.setAttribute("button", 0);

        }
        if (req.getRequestURI().contains("admin")) {
            req.setAttribute("roleAdmin", Role.ADMIN);
        } else if (req.getRequestURI().contains("superAdmin")) {
            req.setAttribute("roleSuperAdmin", Role.SUPER_ADMIN);
        }
        req.getRequestDispatcher("/WEB-INF/changeBook.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String changeBook = req.getParameter("changeBook");
        if (changeBook != null) {
            String page = req.getParameter("page");
            String bookName = req.getParameter("bookName");
            String bookAuthor = req.getParameter("bookAuthor");
            Integer bookQuantity = Integer.valueOf(req.getParameter("bookQuantity"));
            Double bookPrice = Double.valueOf(req.getParameter("bookPrice"));
            String bookDescription = req.getParameter("bookDescription");
            Integer bookId = Integer.valueOf(req.getParameter("bookId"));
            BookDTO bookDTO = new BookDTO();
            bookDTO.setBookQuantity(bookQuantity);
            bookDTO.setBookDescription(bookDescription);
            bookDTO.setBookId(bookId);
            bookDTO.setBookAuthor(bookAuthor);
            bookDTO.setBookPrice(bookPrice);
            bookDTO.setBookName(bookName);
            catalogueService.updateBook(bookDTO);
            if (req.getRequestURI().contains("admin")) {
                resp.sendRedirect(req.getContextPath() + "/admin/catalogue?page=" + page);
            } else if (req.getRequestURI().contains("superAdmin")) {
                resp.sendRedirect(req.getContextPath() + "/superAdmin/catalogue?page=" + page);
            }
        }
        String saveBook = req.getParameter("saveBook");
        if (saveBook != null) {
            String page = req.getParameter("page");
            String bookName = req.getParameter("bookName");
            String bookAuthor = req.getParameter("bookAuthor");
            Integer bookQuantity = Integer.valueOf(req.getParameter("bookQuantity"));
            Double bookPrice = Double.valueOf(req.getParameter("bookPrice"));
            String bookDescription = req.getParameter("bookDescription");
            Integer bookId = Integer.valueOf(req.getParameter("bookId"));
            BookDTO bookDTO = new BookDTO();
            bookDTO.setBookQuantity(bookQuantity);
            bookDTO.setBookDescription(bookDescription);
            bookDTO.setBookId(bookId);
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
