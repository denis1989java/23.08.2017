package ru.mail.denis.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.models.Basket;
import ru.mail.denis.models.User;
import ru.mail.denis.repositories.DAO.BasketDAO;
import ru.mail.denis.service.DTOmodels.BasketDTO;
import ru.mail.denis.service.DTOmodels.BookDTO;
import ru.mail.denis.service.DTOmodels.UserDTO;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 26.06.2017.
 */
@Service
public class BasketServiceImpl implements BasketService {
    private final BasketDAO basketDAO;
    private final CatalogueService catalogueService;
    private final UserService userService;
    private static final Logger logger = Logger.getLogger(BasketServiceImpl.class);
    @Autowired
    public BasketServiceImpl(
            BasketDAO basketDAO,
            CatalogueService catalogueService, UserService userService
    ) {
        this.basketDAO = basketDAO;
        this.catalogueService = catalogueService;
        this.userService = userService;
    }



    @Override
    @Transactional
    public List<BasketDTO> getBooksFromBasketByUser(Integer userId) {
        List<Basket> baskets = getBasketByUserId(userId);
        List<BasketDTO> basketDTOS = new ArrayList<>();
        for (Basket basket : baskets) {
            BasketDTO basketDTO = basketToBasketDTO(basket);
            basketDTOS.add(basketDTO);
        }
        return basketDTOS;
    }

    @Override
    @Transactional
    public void deleteBookFromBasker(Integer basketId) {
        Basket basket = findById(basketId);
        delete(basket);
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
    public void deleteAllFromBasketByUser(Integer userId) {
        List<Basket> baskets = getBasketByUserId(userId);
        for (Basket basket : baskets) {
            delete(basket);
        }
    }
    @Override
    @Transactional
    public Integer basketQuantity(Integer userId){
        List<Basket>  baskets=getBasketByUserId(userId);
        return baskets.size();
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
            BookDTO bookDTO = catalogueService.getBookById(bookId);
            BasketDTO basketDTO = new BasketDTO();
            basketDTO.setBookName(bookDTO.getBookName());
            basketDTO.setBookQuantity(bookQuantity);
            basketDTO.setBookPrice(bookDTO.getBookPrice());
            basketDTO.setBookId(bookDTO.getBookId());
            Basket basket1 = basketDTOToBasket(basketDTO);
            User user=userService.userDTOToUser(userDTO);
            basket1.setUser(user);
            saveBasket(basket1);
        }

    }

    private List<Basket> findAll() {
        logger.debug("Finding all basket");
        List<Basket> baskets = basketDAO.findAll();
        return baskets;
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

    private BasketDTO basketToBasketDTO(Basket basket) {
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setBookId(basket.getBookId());
        basketDTO.setBasketId(basket.getBasketId());
        basketDTO.setUser(basket.getUser());
        basketDTO.setBookQuantity(basket.getBookQuantity());
        basketDTO.setBookPrice(basket.getBookPrice());
        basketDTO.setBookName(basket.getBookName());
        return basketDTO;
    }

    private Basket basketDTOToBasket(BasketDTO basketDTO) {
        Basket basket = new Basket();
        basket.setBookId(basketDTO.getBookId());
        basket.setBookQuantity(basketDTO.getBookQuantity());
        basket.setBasketId(basketDTO.getBasketId());
        basket.setBookPrice(basketDTO.getBookPrice());
        basket.setUser(basketDTO.getUser());
        basket.setBookName(basketDTO.getBookName());
        return basket;
    }

}
