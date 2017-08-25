package ru.mail.denis.repositories;

import ru.mail.denis.repositories.model.Basket;

import java.util.List;

/**
 * Created by user on 08.08.2017.
 */
public interface BasketDAO extends GenericDao<Basket, Integer> {
    List<Basket> getBasketByUserId(Integer userId);

    Basket getBasketByUserIdAndBookId(Integer userId, Integer bookId);
}
