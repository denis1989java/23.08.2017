package ru.mail.denis.web.servlets.order;

import ru.mail.denis.models.Delivery;
import ru.mail.denis.models.Role;
import ru.mail.denis.service.OrderServiceImpl;
import ru.mail.denis.service.DTOmodels.OrderDTO;
import ru.mail.denis.service.DTOmodels.OrdersBooksDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 31.07.2017.
 */
public class AllOrdersServlet extends HttpServlet {
    private OrderServiceImpl orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String spageId = req.getParameter("page");
        String orderId = req.getParameter("orderId");
        List<OrdersBooksDTO> ordersBooksDTO = null;
        if (orderId != null) {
            ordersBooksDTO = orderService.getOrderBooksDTOByOrderId(Integer.valueOf(orderId));
        }
        int pageId = Integer.parseInt(spageId);
        int total = 7;
        if (pageId != 0) {
            pageId = pageId * total;
        }
        List<OrderDTO> orders = orderService.getOrders(pageId, total);
        if (orders.isEmpty()) {
            req.setAttribute("emptyOrders", 0);
        } else {
            req.setAttribute("page", spageId);
            req.setAttribute("orderId", orderId);
            req.setAttribute("ordersBooksDTO", ordersBooksDTO);
            req.setAttribute("orders", orders);
        }
        Integer orderQuantity = orderService.orderQuantity();
        List<Integer> pagination = new ArrayList();
        Integer pageQuantity = 0;
        if (orderQuantity % total == 0) {
            pageQuantity = orderQuantity / total;
        } else {
            pageQuantity = orderQuantity / total + 1;
        }
        for (Integer i = 0; i < pageQuantity; i++) {
            pagination.add(i);
        }
        req.setAttribute("pagination", pagination);
        if (req.getRequestURI().contains("admin")) {
            req.setAttribute("roleAdmin", Role.ADMIN);
        } else if (req.getRequestURI().contains("superAdmin")) {
            req.setAttribute("roleSuperAdmin", Role.SUPER_ADMIN);
        }
        req.getRequestDispatcher("/WEB-INF/allOrders.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String changeStatus = req.getParameter("changeStatus");
        if (changeStatus != null) {
            String page = req.getParameter("page");
            String deliveryStatus = req.getParameter("deliveryStatus");
            Delivery delivery = null;
            if (deliveryStatus.equals("NEW")) {
                delivery = Delivery.NEW;
            } else if (deliveryStatus.equals("REWIWING")) {
                delivery = Delivery.REVIWING;
            } else if (deliveryStatus.equals("IN_PROGRESS")) {
                delivery = Delivery.IN_PROGRESS;
            } else if (deliveryStatus.equals("DELIVERED")) {
                delivery = Delivery.DELIVERED;
            }
            Integer orderId = Integer.valueOf(req.getParameter("orderId"));
            orderService.updateOrderDeliveryStatus(delivery, orderId);
            if (req.getRequestURI().contains("admin")) {
                resp.sendRedirect(req.getContextPath() + "/admin/allOrders?page=" + page);
            } else if (req.getRequestURI().contains("superAdmin")) {
                resp.sendRedirect(req.getContextPath() + "/superAdmin/allOrders?page=" + page);
            }
        }
        String showDetails = req.getParameter("showDetails");
        if (showDetails != null) {
            String page = req.getParameter("page");
            Integer orderId = Integer.valueOf(req.getParameter("orderId"));
            if (req.getRequestURI().contains("admin")) {
                resp.sendRedirect(req.getContextPath() + "/admin/allOrders?page=" + page + "&orderId=" + orderId);
            } else if (req.getRequestURI().contains("superAdmin")) {
                resp.sendRedirect(req.getContextPath() + "/superAdmin/allOrders?page=" + page + "&orderId=" + orderId);
            }
        }
    }
}
