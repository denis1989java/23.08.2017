package ru.mail.denis.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.models.Basket;
import ru.mail.denis.models.User;
import ru.mail.denis.repositories.DAO.BasketDAO;
import ru.mail.denis.repositories.DAO.CatalogueDAO;
import ru.mail.denis.service.DTOmodels.BasketDTO;
import ru.mail.denis.service.DTOmodels.BookDTO;
import ru.mail.denis.service.DTOmodels.UserDTO;
import ru.mail.denis.service.util.BasketConverter;
import ru.mail.denis.service.util.BookConverter;
import ru.mail.denis.service.util.UserConverter;

import java.util.List;


/**
 * Created by user on 26.06.2017.
 */
@Service
public class BasketServiceImpl implements BasketService {
    private final BasketDAO basketDAO;
    private final CatalogueDAO catalogueDAO;
    private static final Logger logger = Logger.getLogger(BasketServiceImpl.class);
    @Autowired
    public BasketServiceImpl(
            BasketDAO basketDAO,
            CatalogueDAO catalogueDAO) {
        this.basketDAO = basketDAO;
        this.catalogueDAO = catalogueDAO;
    }



    @Override
    @Transactional
    public List<BasketDTO> getBooksFromBasketByUser(Integer userId) {
        return BasketConverter.converter(getBasketByUserId(userId));
    }

    @Override
    @Transactional
    public void deleteBookFromBasker(Integer basketId) {
        delete(findById(basketId));
    }

    @Override
    @Transactional
    public void changeBookQuantityInBasket(Integer newQuantity, Integer basketId) {
        Basket basket = findById(basketId);
        basket.setBookQuantity(newQuantity);
        updateBasket(basket);
    }

    @Override
    @Transactional
    public Integer basketQuantity(Integer userId){
        return getBasketByUserId(userId).size();
    }

    @Override
    @Transactional
    public void addToBasket(Integer bookId, UserDTO userDTO, Integer bookQuantity){
        Basket basket = getBasketByUserIdAndBookId(userDTO.getUserId(), bookId);
        Integer bookQuantityInBasket;
        if (basket != null) {
            bookQuantityInBasket = basket.getBookQuantity();
        } else {
            bookQuantityInBasket = 0;
        }
        if (bookQuantityInBasket != 0) {
            basket.setBookQuantity(bookQuantityInBasket+bookQuantity);
            updateBasket(basket);
        } else {
            BookDTO bookDTO = BookConverter.converter(catalogueDAO.findById(bookId));
            Basket newBasket =BasketConverter.converter(BasketConverter.setBasketDTO(bookDTO,bookQuantity));
            User user= UserConverter.converter(userDTO);
            newBasket.setUser(user);
            saveBasket(newBasket);
        }

    }

    private void delete(Basket basket) {
        logger.debug("Deleting basket");
        basketDAO.delete(basket);
    }

    private void saveBasket(Basket basket) {
        logger.debug("saving new basket");
        basketDAO.save(basket);
    }

    private void updateBasket(Basket basket) {
        logger.debug("updating basket");
        basketDAO.update(basket);
    }

    private Basket findById(Integer id) {
        logger.debug("Finding Basket by id");
        Basket basket = basketDAO.findById(id);
        return basket;
    }


    private Basket getBasketByUserIdAndBookId(Integer userId, Integer bookId) {
        logger.debug("Getting basket by User id and Book id");
        Basket basket = basketDAO.getBasketByUserIdAndBookId(userId, bookId);
        return basket;
    }


    private List<Basket> getBasketByUserId(Integer userId) {

        logger.debug("Getting basket by User id");
        List<Basket> baskets = basketDAO.getBasketByUserId(userId);
        return baskets;
    }

}
