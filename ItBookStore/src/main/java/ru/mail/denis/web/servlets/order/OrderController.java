package ru.mail.denis.web.servlets.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.denis.service.CatalogueService;
import ru.mail.denis.service.DTOmodels.*;
import ru.mail.denis.service.Orderservice;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 24.08.2017.
 */

@Controller
public class OrderController {

    private Orderservice orderservice;
    private CatalogueService catalogueService;

    @Autowired
    public OrderController(Orderservice orderservice, CatalogueService catalogueService) {
        this.orderservice = orderservice;
        this.catalogueService = catalogueService;
    }

    @RequestMapping(value = "/user/orders", method = RequestMethod.GET)
    public ModelAndView showOrders(Principal principal, @RequestParam(value = "orderId", required = false) String orderId) {
        List<OrderDTO> orders = orderservice.getOrdersByUser(principal.getName());
        ModelAndView modelAndView = new ModelAndView("orders");
        if (orderId != null) {
            List<OrdersBooksDTO> ordersBooksDTO = orderservice.getOrderBooksDTOByOrderId(Integer.valueOf(orderId));
            modelAndView.addObject("ordersBooksDTO", ordersBooksDTO);
            modelAndView.addObject("orderID", orderId);
        }
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }

    @RequestMapping(value = {"/admin/orders/{page}", "/superAdmin/orders/{page}"}, method = RequestMethod.GET)
    public ModelAndView showAllOrders(@PathVariable int page, @RequestParam(value = "orderId", required = false) String orderId) {
        int total = 7;
        if (page != 0) {
            page = page * total;
        }
        Integer orderDTOQuantity = orderservice.orderQuantity();
        List<Integer> pagination = new ArrayList();
        Integer pageQuantity = 0;
        if (orderDTOQuantity % total == 0) {
            pageQuantity = orderDTOQuantity / total;
        } else {
            pageQuantity = orderDTOQuantity / total + 1;
        }
        for (Integer i = 0; i < pageQuantity; i++) {
            pagination.add(i);
        }
        List<OrderDTO> orders = orderservice.getOrders(page, total);
        ModelAndView modelAndView = new ModelAndView("allOrders");
        if (orderId != null) {
            List<OrdersBooksDTO> ordersBooksDTO = orderservice.getOrderBooksDTOByOrderId(Integer.valueOf(orderId));
            modelAndView.addObject("ordersBooksDTO", ordersBooksDTO);
            modelAndView.addObject("orderID", orderId);
        }
        modelAndView.addObject("orders", orders);
        modelAndView.addObject("pagination",pagination);
        return modelAndView;
    }


    @RequestMapping(value = "/admin/changeDeliveryStatus", method = RequestMethod.POST)
    public String changeDeliveriStatusAdmin(@RequestParam("page") String page,
                                       @RequestParam(value = "orderId", required = false) String orderId,
                                       @RequestParam(value = "deliveryStatus", required = false) String deliveryStatus) {
        orderservice.updateOrderDeliveryStatus(deliveryStatus,Integer.parseInt(orderId));
        return "redirect:/admin/orders/"+page;
    }
    @RequestMapping(value = "/superAdmin/changeDeliveryStatus", method = RequestMethod.POST)
    public String changeDeliveriStatusSuperAdmin(@RequestParam("page") String page,
                                       @RequestParam(value = "orderId", required = false) String orderId,
                                       @RequestParam(value = "deliveryStatus", required = false) String deliveryStatus) {
        orderservice.updateOrderDeliveryStatus(deliveryStatus,Integer.parseInt(orderId));
        return "redirect:/superAdmin/orders/"+page;
    }


    @RequestMapping(value = "/user/orders", method = RequestMethod.POST)
    public String addOrder(@RequestParam("fullPrice") String fullPrice, Principal principal) {
        orderservice.addOrder(principal.getName(), Double.valueOf(fullPrice));
        return "redirect:/user/orders";
    }

    @RequestMapping(value = "/user/orders/changeReceiveStatus", method = RequestMethod.POST)
    public String changeReceiveStatus(@RequestParam("orderId") String orderId) {
        orderservice.changeReceiveStatus(Integer.valueOf(orderId));
        return "redirect:/user/orders";
    }

    @RequestMapping(value = "/user/order/change", method = RequestMethod.GET)
    public ModelAndView showChangeOrder(@RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = orderservice.getOrderById(Integer.valueOf(orderId));
        List<OrderBookTimesDTO> bookTimesDTOS = orderservice.getOrderBooksTimesDTOByOrderId(Integer.valueOf(orderId));
        Double summ = 0.00;
        for (int i = 0; i < bookTimesDTOS.size(); i++) {
            Double price = bookTimesDTOS.get(i).getBookPrice();
            Double quantity = Double.valueOf(bookTimesDTOS.get(i).getBookQuantity());
            summ = summ + price * quantity;
        }
        ModelAndView modelAndView = new ModelAndView("changeOrder");
        modelAndView.addObject("orderDTO", orderDTO);
        modelAndView.addObject("summ", summ);
        modelAndView.addObject("OrderBookTimesDTO", bookTimesDTOS);
        return modelAndView;
    }

    @RequestMapping(value = "/user/order/addToOrder/{page}", method = RequestMethod.GET)
    public ModelAndView showAddToOrder(@RequestParam("orderId") String orderId, @PathVariable int page) {
        int total = 7;
        if (page != 0) {
            page = page * total;
        }
        List<BookDTO> bookDTOS = catalogueService.getBooks(page, total);
        Integer booksQuantity = catalogueService.booksQuantity();
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
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("catalogue", bookDTOS);
        map.put("pagination", pagination);
        ModelAndView modelAndView = new ModelAndView("addToOrder");
        modelAndView.addAllObjects(map);
        return modelAndView;
    }

    @RequestMapping(value = "/user/order/addToOrder", method = RequestMethod.POST)
    public String addToOrder(@RequestParam("orderId") String orderId,
                             @RequestParam("bookId") String bookId,
                             @RequestParam("bookQuantity") String bookQuantity) {
        orderservice.addOrderBookTimes(Integer.parseInt(bookId), Integer.parseInt(orderId), Integer.parseInt(bookQuantity));
        return "redirect:/user/order/change?orderId=" + orderId;
    }

    @RequestMapping(value = "/user/order/changeBookQuantity", method = RequestMethod.POST)
    public String changeBookQuantity(@RequestParam("orderId") String orderId,
                                     @RequestParam("ordersBooksTimesId") String ordersBooksTimesId,
                                     @RequestParam("bookQuantity") String bookQuantity) {
        if (bookQuantity == "") {
            return "redirect:/user/order/change?orderId=" + orderId;
        } else {
            orderservice.changeOrdersBooksTimesQuantity(Integer.parseInt(bookQuantity), Integer.parseInt(ordersBooksTimesId));
            return "redirect:/user/order/change?orderId=" + orderId;
        }
    }

    @RequestMapping(value = "/user/order/deleteBook", method = RequestMethod.POST)
    public String deleteOrderBookTimes(@RequestParam(value = "deleting", required = false) String[] deletings,
                                       @RequestParam("orderId") String orderId) {
        if (deletings != null) {
            for (int i = 0; i < deletings.length; i++) {
                Integer orderBookTimesId = Integer.valueOf(deletings[i]);
                orderservice.deleteFromOrdersBooksTimesById(orderBookTimesId);
            }
        }
        return "redirect:/user/order/change?orderId=" + orderId;
    }

    @RequestMapping(value = "/user/order/delete", method = RequestMethod.POST)
    public String deleteOrder(@RequestParam("orderId") String orderId) {
        orderservice.deleteOrder(Integer.parseInt(orderId));
        return "redirect:/user/orders";
    }

    @RequestMapping(value = "/user/order/save", method = RequestMethod.POST)
    public String saveOrder(@RequestParam("orderId") String orderId,
                            @RequestParam("fullPrice") String fullPrice) {
        orderservice.updateOrderAndOrdersBooks(Integer.parseInt(orderId), Double.parseDouble(fullPrice));
        return "redirect:/user/orders";
    }
}
