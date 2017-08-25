package ru.mail.denis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.denis.service.BasketService;
import ru.mail.denis.service.DTOmodels.BasketDTO;
import ru.mail.denis.service.DTOmodels.UserDTO;
import ru.mail.denis.service.UserService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 23.08.2017.
 */
@Controller
public class BasketController {
    private BasketService basketService;
    private UserService userService;

    @Autowired
    public BasketController(BasketService basketService, UserService userService) {
        this.basketService = basketService;
        this.userService = userService;
    }

    @RequestMapping(value = "/user/basket",method = RequestMethod.GET)
    public ModelAndView showBasket(Principal principal){
        UserDTO userDTO=userService.getUserDTOByEmail(principal.getName());
        List<BasketDTO> basket =basketService.getBooksFromBasketByUser(userDTO.getUserId());
        BigDecimal summ = BigDecimal.ZERO;
        for (int i = 0; i < basket.size(); i++) {
            BigDecimal price = basket.get(i).getBookPrice();
            Integer quantity = basket.get(i).getBookQuantity();
            summ = summ.add(price.multiply(new BigDecimal(quantity))) ;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("basket",basket);
        map.put("summ",summ);
        ModelAndView modelAndView=new ModelAndView("user/basket");
        modelAndView.addAllObjects(map);
        return modelAndView;
    }
    @RequestMapping(value = "/user/addToBasket", method = RequestMethod.POST)
    public String addToBasket(@RequestParam ("bookId") String bookId,@RequestParam ("bookQuantity") String bookQuantity, Principal principal){
        UserDTO userDTO=userService.getUserDTOByEmail(principal.getName());
        basketService.addToBasket(Integer.parseInt(bookId),userDTO,Integer.parseInt(bookQuantity));
        return "redirect:/user/catalogue/0";
    }
    @RequestMapping(value = "/user/basket/changeQuantity", method = RequestMethod.POST)
    public String changeQuantity(@RequestParam ("basketId") String basketId,@RequestParam ("bookQuantity") String bookQuantity){
        if (bookQuantity == "") {
            return "redirect:/user/basket";
        } else {
            basketService.changeBookQuantityInBasket(Integer.parseInt(bookQuantity),Integer.parseInt(basketId));
            return "redirect:/user/basket";
        }
    }

    @RequestMapping(value = "/user/basket/delete",method = RequestMethod.POST)
    public String deleteUser(@RequestParam (value = "deleting",required = false) String [] deletings){
        if (deletings!=null) {
            for (int i = 0; i < deletings.length; i++) {
                Integer basketId= Integer.valueOf(deletings[i]);
                basketService.deleteBookFromBasker(basketId);
            }
        }
        return "redirect:/user/basket";
    }


}
