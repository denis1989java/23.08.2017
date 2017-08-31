package ru.mail.denis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.denis.service.BasketService;
import ru.mail.denis.service.model.UserDTO;
import ru.mail.denis.service.model.ViewDTO;
import ru.mail.denis.service.UserService;

import java.security.Principal;

/**
 * Created by Denis Monich on 23.08.2017.
 */
@Controller
@RequestMapping(value = "/user")
public class BasketController {
    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public ModelAndView showBasket() {
        ViewDTO viewDTO = basketService.viewPage();
        ModelAndView modelAndView = new ModelAndView("user/basket");
        modelAndView.addObject(viewDTO);
        return modelAndView;
    }

    @RequestMapping(value = "/addToBasket", method = RequestMethod.POST)
    public String addToBasket(@RequestParam("bookId") String bookId, @RequestParam("bookQuantity") String bookQuantity) {
        basketService.addToBasket(Integer.parseInt(bookId), bookQuantity);
        return "redirect:/user/catalogue?page=0";
    }

    @RequestMapping(value = "/basket/changeQuantity", method = RequestMethod.POST)
    public String changeQuantity(@RequestParam("basketId") String basketId, @RequestParam("bookQuantity") String bookQuantity) {
        if (bookQuantity == "") {
            return "redirect:/user/basket";
        } else {
            basketService.changeBookQuantityInBasket(bookQuantity, Integer.parseInt(basketId));
            return "redirect:/user/basket";
        }
    }

    @RequestMapping(value = "/basket/delete", method = RequestMethod.POST)
    public String deleteUser(@RequestParam(value = "deleting", required = false) String[] deletings) {
        if (deletings != null) {
            basketService.deleteBookFromBasket(deletings);
        }
        return "redirect:/user/basket";
    }


}
