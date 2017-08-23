package ru.mail.denis.web.servlets.order;

import ru.mail.denis.service.BasketServiceImpl;
import ru.mail.denis.service.DTOmodels.BasketDTO;
import ru.mail.denis.service.OrderServiceImpl;
import ru.mail.denis.service.DTOmodels.OrderDTO;
import ru.mail.denis.service.DTOmodels.OrdersBooksDTO;
import ru.mail.denis.service.DTOmodels.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by user on 07.07.2017.
 */
public class OrdersServlet extends HttpServlet {
    private OrderServiceImpl orderService;
    private BasketServiceImpl basketServiceImpl;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        List<OrderDTO> orders = orderService.getOrdersByUser(userDTO.getUserId());
        if (orders.isEmpty()) {
            req.setAttribute("emptyOrders", 0);
        } else {
            req.setAttribute("NEW", "NEW");
            req.setAttribute("orders", orders);
        }
        String showDetails = req.getParameter("showDetails");
        if (showDetails != null) {
            Integer orderId = Integer.valueOf(req.getParameter("orderId"));
            List<OrdersBooksDTO> ordersBooksDTO = orderService.getOrderBooksDTOByOrderId(orderId);
            req.setAttribute("ORDERID", orderId);
            req.setAttribute("ordersBooksDTO", ordersBooksDTO);
        }
        req.getRequestDispatcher("/WEB-INF/orders.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String changeReceiveStatus = req.getParameter("changeReceiveStatus");
        if (changeReceiveStatus != null) {
            Integer orderId = Integer.valueOf(req.getParameter("orderId"));
            orderService.changeReceiveStatus(orderId);
            resp.sendRedirect(req.getContextPath() + "/user/orders");
        }
        String deleteOrder = req.getParameter("deleteOrder");
        if (deleteOrder != null) {
            Integer orderId = Integer.valueOf(req.getParameter("orderId"));
            orderService.deleteOrder(orderId);
            resp.sendRedirect(req.getContextPath() + "/user/orders");
        }
        String saveOrder = req.getParameter("save");
        if (saveOrder != null) {
            Integer orderId = Integer.valueOf(req.getParameter("orderId"));
            Double fullPrice = Double.valueOf(req.getParameter("fullPrice"));
            orderService.updateOrderAndOrdersBooks(orderId, fullPrice);
            resp.sendRedirect(req.getContextPath() + "/user/orders");
        }
        String addOrder = req.getParameter("addOrder");
        if (addOrder != null) {
            Double fullPrice = Double.valueOf(req.getParameter("fullPrice"));
            HttpSession httpSession = req.getSession();
            UserDTO userDTO = (UserDTO) httpSession.getAttribute("user");
            List<BasketDTO> baskets = basketServiceImpl.getBooksFromBasketByUser(userDTO.getUserId());
            basketServiceImpl.deleteAllFromBasketByUser(userDTO.getUserId());
            orderService.insertToOrders(baskets, userDTO, fullPrice);
            resp.sendRedirect(req.getContextPath() + "/user/orders");
        }


    }


}
