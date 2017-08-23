package ru.mail.denis.web.servlets.basket;

import ru.mail.denis.service.BasketServiceImpl;
import ru.mail.denis.service.OrderServiceImpl;
import ru.mail.denis.service.DTOmodels.BasketDTO;
import ru.mail.denis.service.DTOmodels.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by user on 26.06.2017.
 */
public class BasketServlet extends HttpServlet {
    private BasketServiceImpl basketServiceImpl;
    private OrderServiceImpl orderServiceImpl;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        UserDTO userDTO = (UserDTO) httpSession.getAttribute("user");
        List<BasketDTO> basket = basketServiceImpl.getBooksFromBasketByUser(userDTO.getUserId());
        Double summ = 0.00;
        for (int i = 0; i < basket.size(); i++) {
            Double price = basket.get(i).getBookPrice();
            Double quantity = Double.valueOf(basket.get(i).getBookQuantity());
            summ = summ + price * quantity;
        }
        if (basket.isEmpty()) {
            req.setAttribute("emptybasket", 0);
        } else {
            req.setAttribute("summ", summ);
            req.setAttribute("basket", basket);
        }
        req.getRequestDispatcher("/WEB-INF/basket.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String delete = req.getParameter("delete");
        if (delete != null) {
            String[] deletings = req.getParameterValues("deleting");
            if (deletings == null) {
                resp.sendRedirect(req.getContextPath() + "/user/basket");
            } else {
                for (int i = 0; i < deletings.length; i++) {
                    Integer basketBookId = Integer.valueOf(deletings[i]);
                    basketServiceImpl.deleteBookFromBasker(basketBookId);
                }
                resp.sendRedirect(req.getContextPath() + "/user/basket");


            }
        }
        String changeQuantity = req.getParameter("change");
        if (changeQuantity != null) {
            String newQuantity1 = req.getParameter("bookQuantity");
            if (newQuantity1 == "") {
                resp.sendRedirect(req.getContextPath() + "/user/basket");
            } else {
                Integer newQuantity = Integer.valueOf(newQuantity1);
                Integer basketBookDTOId = Integer.valueOf(req.getParameter("basketId"));
                basketServiceImpl.changeBookQuantityInBasket(newQuantity, basketBookDTOId);
                resp.sendRedirect(req.getContextPath() + "/user/basket");
            }
        }


    }
}