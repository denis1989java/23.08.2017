package ru.mail.denis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.denis.repositories.model.Delivery;
import ru.mail.denis.service.CatalogueService;
import ru.mail.denis.service.model.*;
import ru.mail.denis.service.Orderservice;

import java.security.Principal;

/**
 * Created by Denis Monich on 24.08.2017.
 */

@Controller
public class OrderController {

    private final Orderservice orderservice;
    private final CatalogueService catalogueService;

    @Autowired
    public OrderController(Orderservice orderservice, CatalogueService catalogueService) {
        this.orderservice = orderservice;
        this.catalogueService = catalogueService;
    }

    @RequestMapping(value = "/user/orders", method = RequestMethod.GET)
    public ModelAndView showOrders(Principal principal, @RequestParam(value = "orderId", required = false) String orderId) {
        ModelAndView modelAndView = new ModelAndView("user/orders");
        ViewDTO viewDTO = orderservice.viewPageOrders(orderId, principal.getName());
        modelAndView.addObject(viewDTO);
        return modelAndView;
    }

    @RequestMapping(value = {"/admin/orders/{page}", "/superAdmin/orders/{page}"}, method = RequestMethod.GET)
    public ModelAndView showAllOrders(@PathVariable int page, @RequestParam(value = "orderId", required = false) String orderId) {
        ModelAndView modelAndView = new ModelAndView("allOrders");
        ViewDTO viewDTO = orderservice.viewPageAllOrders(orderId, page);
        modelAndView.addObject(viewDTO);
        return modelAndView;
    }


    @RequestMapping(value = "/admin/changeDeliveryStatus", method = RequestMethod.POST)
    public String changeDeliveriStatusAdmin(@RequestParam("page") String page,
                                            @RequestParam(value = "orderId", required = false) String orderId,
                                            @RequestParam(value = "deliveryStatus", required = false) String deliveryStatus) {
        orderservice.updateOrderDeliveryStatus(deliveryStatus, Integer.parseInt(orderId));
        return "redirect:/admin/orders/" + page;
    }

    @RequestMapping(value = "/superAdmin/changeDeliveryStatus", method = RequestMethod.POST)
    public String changeDeliveriStatusSuperAdmin(@RequestParam("page") String page,
                                                 @RequestParam(value = "orderId", required = false) String orderId,
                                                 @RequestParam(value = "deliveryStatus", required = false) String deliveryStatus) {
        orderservice.updateOrderDeliveryStatus(deliveryStatus, Integer.parseInt(orderId));
        return "redirect:/superAdmin/orders/" + page;
    }


    @RequestMapping(value = "/user/orders", method = RequestMethod.POST)
    public String addOrder(@RequestParam("fullPrice") String fullPrice, Principal principal) {
        orderservice.addOrder(principal.getName(), String.valueOf(Double.parseDouble(fullPrice)));
        return "redirect:/user/orders";
    }

    @RequestMapping(value = "/user/orders/changeReceiveStatus", method = RequestMethod.POST)
    public String changeReceiveStatus(@RequestParam("orderId") String orderId) {
        orderservice.changeReceiveStatus(Integer.valueOf(orderId));
        return "redirect:/user/orders";
    }

    @RequestMapping(value = "/user/order/change", method = RequestMethod.GET)
    public String showChangeOrder(@RequestParam("orderId") String orderId, Model model) {
        OrderDTO orderDTO = orderservice.getOrderById(Integer.valueOf(orderId));
        if (orderDTO.getOrderDelivery() != Delivery.NEW) {
            return "redirect:/user/orders";
        }
        AppUserPrincipal principal = (AppUserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Integer userId=principal.getUserId();
        if (orderDTO.getUserId() != userId) {
            return "redirect:/user/orders";
        }
        ViewDTO viewDTO = orderservice.viewPageChangeOrder(orderDTO, orderId);
        model.addAttribute(viewDTO);
        return "user/changeOrder";
    }

    @RequestMapping(value = "/user/order/addToOrder/{page}", method = RequestMethod.GET)
    public ModelAndView showAddToOrder(@RequestParam("orderId") String orderId, @PathVariable int page) {
        ViewDTO viewDTO = catalogueService.viewPage(page, orderId);
        ModelAndView modelAndView = new ModelAndView("user/addToOrder");
        modelAndView.addObject(viewDTO);
        return modelAndView;
    }

    @RequestMapping(value = "/user/order/addToOrder", method = RequestMethod.POST)
    public String addToOrder(@RequestParam("orderId") String orderId,
                             @RequestParam("bookId") String bookId,
                             @RequestParam("bookQuantity") String bookQuantity) {
        orderservice.addOrderBookTimes(Integer.parseInt(bookId), Integer.parseInt(orderId), bookQuantity);
        return "redirect:/user/order/change?orderId=" + orderId;
    }

    @RequestMapping(value = "/user/order/changeBookQuantity", method = RequestMethod.POST)
    public String changeBookQuantity(@RequestParam("orderId") String orderId,
                                     @RequestParam("ordersBooksTimesId") String ordersBooksTimesId,
                                     @RequestParam("bookQuantity") String bookQuantity) {
        if (bookQuantity == "") {
            return "redirect:/user/order/change?orderId=" + orderId;
        } else {
            orderservice.changeOrdersBooksTimesQuantity(bookQuantity, Integer.parseInt(ordersBooksTimesId));
            return "redirect:/user/order/change?orderId=" + orderId;
        }
    }

    @RequestMapping(value = "/user/order/deleteBook", method = RequestMethod.POST)
    public String deleteOrderBookTimes(@RequestParam(value = "deleting", required = false) String[] deletings,
                                       @RequestParam("orderId") String orderId) {
        if (deletings != null) {
            orderservice.deleteFromOrdersBooksTimesById(deletings);
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
        orderservice.updateOrderAndOrdersBooks(Integer.parseInt(orderId), String.valueOf(Double.parseDouble(fullPrice)));
        return "redirect:/user/orders";
    }
}
