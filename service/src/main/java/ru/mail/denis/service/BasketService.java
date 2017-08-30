package ru.mail.denis.service;

import ru.mail.denis.service.model.UserDTO;
import ru.mail.denis.service.model.ViewDTO;

/**
 * Created by Denis Monich on 05.07.2017.
 */
public interface BasketService {


    ViewDTO viewPage();

    void deleteBookFromBasket(String[] deletings);

    void changeBookQuantityInBasket(String newQuantity, Integer basketId);

    void addToBasket(Integer bookId, String bookQuantity);
}
