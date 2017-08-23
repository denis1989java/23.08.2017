package ru.mail.denis.web.servlets.order;

import ru.mail.denis.service.CatalogueServiceImpl;
import ru.mail.denis.service.OrderServiceImpl;
import ru.mail.denis.service.DTOmodels.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by user on 28.07.2017.
 */
public class ChangeServlet extends HttpServlet {
    private OrderServiceImpl orderServiceImpl;
    private CatalogueServiceImpl catalogueService ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer orderId = Integer.valueOf(req.getParameter("orderId"));
        OrderDTO orderDTO = orderServiceImpl.getOrderById(orderId);
        List<OrderBookTimesDTO> OrderBookTimesDTO = orderServiceImpl.getOrderBooksTimesDTOByOrderId(orderId);
        if (OrderBookTimesDTO.isEmpty()) {
            req.setAttribute("orderDTO", orderDTO);
            req.setAttribute("emptyList", 0);
        } else {
            Double summ = 0.00;
            for (int i = 0; i < OrderBookTimesDTO.size(); i++) {
                Double price = OrderBookTimesDTO.get(i).getBookPrice();
                Double quantity = Double.valueOf(OrderBookTimesDTO.get(i).getBookQuantity());
                summ = summ + price * quantity;
            }
            req.setAttribute("summ", summ);
            req.setAttribute("orderDTO", orderDTO);
            req.setAttribute("OrderBookTimesDTO", OrderBookTimesDTO);
        }

        req.getRequestDispatcher("/WEB-INF/changeOrder.jsp").forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String delete = req.getParameter("delete");
        if (delete != null) {
            String[] deletings = req.getParameterValues("deleting");
            if (deletings == null) {
                Integer orderId = Integer.valueOf(req.getParameter("orderId"));
                resp.sendRedirect(req.getContextPath() + "/user/changeOrder?orderId=" + orderId);
            } else {
                for (int i = 0; i < deletings.length; i++) {
                    Integer orderBooksId = Integer.valueOf(deletings[i]);
                    orderServiceImpl.deleteFromOrdersBooksTimesById(orderBooksId);
                }
                Integer orderId = Integer.valueOf(req.getParameter("orderId"));
                resp.sendRedirect(req.getContextPath() + "/user/changeOrder?orderId=" + orderId);
            }
        }
        String changeQuantity = req.getParameter("change");
        if (changeQuantity != null) {
            Integer orderId = Integer.valueOf(req.getParameter("orderId"));
            String newQuantity1 = req.getParameter("bookQuantity");
            if (newQuantity1 == "") {
                resp.sendRedirect(req.getContextPath() + "/user/changeOrder?orderId=" + orderId);
            } else {
                Integer newQuantity = Integer.valueOf(newQuantity1);
                Integer orderBooksId = Integer.valueOf(req.getParameter("ordersBooksId"));
                orderServiceImpl.changeOrdersBooksTimesQuantity(newQuantity, orderBooksId);
                resp.sendRedirect(req.getContextPath() + "/user/changeOrder?orderId=" + orderId);
            }

        }
        String addToChangeOrder = req.getParameter("addToChangeOrder");
        if (addToChangeOrder != null) {
            Integer bookId = Integer.valueOf(req.getParameter("bookId"));
            Integer quantity = Integer.parseInt(req.getParameter("bookQuantity"));
            Integer orderId = Integer.valueOf(req.getParameter("orderId"));
            Integer quantityInChangeOrder = orderServiceImpl.bookQuantityInOrdersBooksTimes(bookId, orderId);
            if (quantityInChangeOrder != 0) {
                orderServiceImpl.updateQuantityInOrderBooksTimes(bookId, orderId, quantity);
            } else {
                BookDTO bookDTO = catalogueService.getBookById(bookId);
                bookDTO.setBookQuantity(quantity);
                orderServiceImpl.insertToOrdersBooksTimes(bookDTO, orderId);
            }
            resp.sendRedirect(req.getContextPath() + "/user/changeOrder?orderId=" + orderId);

        }
    }

}
