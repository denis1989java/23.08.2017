package ru.mail.denis.service.util;

import ru.mail.denis.models.Basket;
import ru.mail.denis.service.DTOmodels.BasketDTO;
import ru.mail.denis.service.DTOmodels.BookDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 24.08.2017.
 */
public class BasketConverter {
    public BasketConverter() {
    }

    public static Basket  converter(BasketDTO basketDTO){
        Basket basket = new Basket();
        basket.setBookId(basketDTO.getBookId());
        basket.setBookQuantity(basketDTO.getBookQuantity());
        basket.setBasketId(basketDTO.getBasketId());
        basket.setBookPrice(basketDTO.getBookPrice());
        basket.setUser(basketDTO.getUser());
        basket.setBookName(basketDTO.getBookName());
        return basket;
    }

    public static BasketDTO converter (Basket basket){
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setBookId(basket.getBookId());
        basketDTO.setBasketId(basket.getBasketId());
        basketDTO.setUser(basket.getUser());
        basketDTO.setBookQuantity(basket.getBookQuantity());
        basketDTO.setBookPrice(basket.getBookPrice());
        basketDTO.setBookName(basket.getBookName());
        return basketDTO;
    }

    public static List <BasketDTO> converter(List <Basket> baskets ){
        List<BasketDTO> basketDTOS = new ArrayList<>();
        for (Basket basket : baskets) {
            BasketDTO basketDTO = BasketConverter.converter (basket);
            basketDTOS.add(basketDTO);
        }
        return basketDTOS;
    }

    public static BasketDTO setBasketDTO (BookDTO bookDTO, Integer bookQuantity){
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setBookName(bookDTO.getBookName());
        basketDTO.setBookQuantity(bookQuantity);
        basketDTO.setBookPrice(bookDTO.getBookPrice());
        basketDTO.setBookId(bookDTO.getBookId());
        return basketDTO;
    }
}
