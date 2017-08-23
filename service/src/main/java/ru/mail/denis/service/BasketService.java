package ru.mail.denis.service;

import ru.mail.denis.service.DTOmodels.BasketDTO;
import ru.mail.denis.service.DTOmodels.UserDTO;

import java.util.List;

/**
 * Created by user on 05.07.2017.
 */
public interface BasketService {



    List<BasketDTO> getBooksFromBasketByUser(Integer userId);


    void deleteBookFromBasker(Integer basketId);

    void changeBookQuantityInBasket(Integer newQuantity, Integer basketId);

    void deleteAllFromBasketByUser(Integer userId);

    Integer basketQuantity(Integer userId);

    void addToBasket(Integer bookId, UserDTO userDTO, Integer bookQuantity);
}
