package ru.mail.denis.service;

import ru.mail.denis.service.modelDTO.UserDTO;
import ru.mail.denis.service.modelDTO.ViewDTO;

/**
 * Created by user on 05.07.2017.
 */
public interface BasketService {


    ViewDTO viewPage(Integer userId);

    void deleteBookFromBasket(String[] deletings);

    void changeBookQuantityInBasket(Integer newQuantity, Integer basketId);

    void addToBasket(Integer bookId, UserDTO userDTO, Integer bookQuantity);
}
