package ru.mail.denis.service.util;

import ru.mail.denis.repositories.model.Basket;
import ru.mail.denis.service.model.BasketDTO;
import ru.mail.denis.service.model.BookDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis Monich on 24.08.2017.
 */
public class BasketConverter {

    private static BasketDTO converter (Basket basket){
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


}
