package ru.mail.denis.web.servlets.catalogue;

import ru.mail.denis.models.Changable;
import ru.mail.denis.models.Role;
import ru.mail.denis.service.BasketServiceImpl;
import ru.mail.denis.service.CatalogueServiceImpl;
import ru.mail.denis.service.OrderServiceImpl;
import ru.mail.denis.service.DTOmodels.BasketDTO;
import ru.mail.denis.service.DTOmodels.BookDTO;
import ru.mail.denis.service.DTOmodels.OrdersBooksDTO;
import ru.mail.denis.service.DTOmodels.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 25.06.2017.
 */
public class CatalogueServlet extends HttpServlet {
    private CatalogueServiceImpl catalogueServiceImpl;
    private BasketServiceImpl basketServiceImpl;
    private OrderServiceImpl orderServiceImpl;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String addToOrder = req.getParameter("addToOrder");
        String spageId;
        if (addToOrder != null) {
            req.setAttribute("addToOrder", 0);
            if (req.getParameter("page") != null) {
                spageId = req.getParameter("page");
            } else {
                spageId = "0";
            }
            String orderId = req.getParameter("orderId");
            req.setAttribute("orderId", orderId);
        } else {
            spageId = req.getParameter("page");
        }
        int pageId = Integer.parseInt(spageId);
        int total = 7;
        if (pageId != 0) {
            pageId = pageId * total;
        }
//        List<BookDTO> books = catalogueServiceImpl.getBooks(pageId, total);
//        for (int i = 0; i < books.size(); i++) {
//            Integer bookID = books.get(i).getBookId();
//            List<OrdersBooksDTO> list = orderServiceImpl.getOrderBooksDTOByBookId(bookID);
//            BookDTO bookDTO = books.get(i);
//            if (!list.isEmpty()) {
//                bookDTO.setChangable(Changable.NOT_CHANGABLE);
//            } else {
//                bookDTO.setChangable(Changable.CHANGABLE);
//            }
//            catalogueServiceImpl.updateBook(bookDTO);
//        }
        List<BookDTO> books1 = catalogueServiceImpl.getBooks(pageId, total);
        Integer booksQuantity = catalogueServiceImpl.booksQuantity();
        List<Integer> pagination = new ArrayList();
        Integer pageQuantity = 0;
        if (booksQuantity % total == 0) {
            pageQuantity = booksQuantity / total;
        } else {
            pageQuantity = booksQuantity / total + 1;
        }
        for (Integer i = 0; i < pageQuantity; i++) {
            pagination.add(i);
        }
        req.setAttribute("pagination", pagination);
        req.setAttribute("catalogue", books1);
        req.setAttribute("CHANGABLE", "CHANGABLE");
        req.setAttribute("page", spageId);
        if (req.getRequestURI().contains("user")) {
            req.setAttribute("roleUser", Role.USER);
        } else if (req.getRequestURI().contains("admin")) {
            req.setAttribute("roleAdmin", Role.ADMIN);
        } else if (req.getRequestURI().contains("superAdmin")) {
            req.setAttribute("roleSuperAdmin", Role.SUPER_ADMIN);
        }
        req.getRequestDispatcher("/WEB-INF/catalogue.jsp").forward(req, resp);
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String addToBasket = req.getParameter("addToBasket");
//        Integer bookId = Integer.valueOf(req.getParameter("bookId"));
//        int quantity = Integer.parseInt(req.getParameter("bookQuantity"));
//        String page = (String) req.getParameter("page");
//        HttpSession httpSession = req.getSession();
//        UserDTO userDTO = (UserDTO) httpSession.getAttribute("user");
//        if (addToBasket != null) {
//            Integer quantityInBasket = basketServiceImpl.BookQuantityInBasket(userDTO.getUserId(), bookId);
//            if (quantityInBasket != 0) {
//                basketServiceImpl.updateQuantityInBasket(bookId, userDTO.getUserId(), quantity);
//            } else {
//                BookDTO bookDTO = catalogueServiceImpl.getBookById(bookId);
//                BasketDTO basketDTO = new BasketDTO();
//                basketDTO.setBookName(bookDTO.getBookName());
//                basketDTO.setBookQuantity(quantity);
//                basketDTO.setBookPrice(bookDTO.getBookPrice());
//                basketDTO.setBookId(bookDTO.getBookId());
//                basketServiceImpl.saveBasketDTO(basketDTO, userDTO);
//            }
//            resp.sendRedirect(req.getContextPath() + "/user/catalogue?page=" + page);
//
//        }
//
//    }
}
