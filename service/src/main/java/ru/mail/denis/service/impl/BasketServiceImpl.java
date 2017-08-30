package ru.mail.denis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.repositories.UserDAO;
import ru.mail.denis.repositories.model.Basket;
import ru.mail.denis.repositories.model.User;
import ru.mail.denis.repositories.BasketDAO;
import ru.mail.denis.repositories.CatalogueDAO;
import ru.mail.denis.service.BasketService;
import ru.mail.denis.service.model.*;
import ru.mail.denis.service.util.BasketConverter;
import ru.mail.denis.service.util.BookConverter;
import ru.mail.denis.service.util.UserConverter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Denis Monich on 26.06.2017.
 */
@Service
public class BasketServiceImpl implements BasketService {
    private final BasketDAO basketDAO;
    private final CatalogueDAO catalogueDAO;
    private final UserDAO userDAO;
    private static final Logger logger = Logger.getLogger(BasketServiceImpl.class);
    @Autowired
    public BasketServiceImpl(
            BasketDAO basketDAO,
            CatalogueDAO catalogueDAO, UserDAO userDAO) {
        this.basketDAO = basketDAO;
        this.catalogueDAO = catalogueDAO;
        this.userDAO = userDAO;
    }


    @Override
    @Transactional
    public ViewDTO viewPage(){
        UserDTO userDTO=getUser();
        List<BasketDTO> basket= BasketConverter.converter(getBasketByUserId(userDTO.getUserId()));
        BigDecimal summ = BigDecimal.ZERO;
        for (int i = 0; i < basket.size(); i++) {
            BigDecimal price =new BigDecimal(basket.get(i).getBookPrice()) ;
            String quantity = basket.get(i).getBookQuantity();
            summ = summ.add(price.multiply(new BigDecimal(quantity))) ;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("basket",basket);
        map.put("summ",summ);
        ViewDTO viewDTO=new ViewDTO();
        viewDTO.setViewMap(map);
        return viewDTO;
    }


    @Override
    @Transactional
    public void deleteBookFromBasket(String[] deletings) {
        for (String deleting : deletings) {
            Integer basketId= Integer.valueOf(deleting);
            Basket basket=findById(basketId);
            delete(basket);
        }
    }

    @Override
    @Transactional
    public void changeBookQuantityInBasket(String newQuantity, Integer basketId) {
        Basket basket = findById(basketId);
        basket.setBookQuantity(newQuantity);
        updateBasket(basket);
    }

    @Override
    @Transactional
    public void addToBasket(Integer bookId, String bookQuantity){
        UserDTO userDTO=getUser();
        Basket basket = getBasketByUserIdAndBookId(userDTO.getUserId(), bookId);
        Integer bookQuantityInBasket;
        if (basket != null) {
            bookQuantityInBasket = Integer.valueOf(basket.getBookQuantity());
        } else {
            bookQuantityInBasket = 0;
        }
        if (bookQuantityInBasket != 0) {
            basket.setBookQuantity( String.valueOf(bookQuantityInBasket+Integer.parseInt(bookQuantity)));
            updateBasket(basket);
        } else {
            BookDTO bookDTO = BookConverter.converter(catalogueDAO.findById(bookId));
            Basket newBasket =converter(setBasketDTO(bookDTO,bookQuantity));
            User user= UserConverter.converter(userDTO);
            newBasket.setUser(user);
            saveBasket(newBasket);
        }

    }

    private void delete(Basket basket) {
        logger.debug("Deleting basket"+basket.getBasketId());
        basketDAO.delete(basket);
    }

    private void saveBasket(Basket basket) {
        logger.debug("saving new basket");
        basketDAO.save(basket);
    }

    private void updateBasket(Basket basket) {
        logger.debug("updating basket"+basket.getBasketId());
        basketDAO.update(basket);
    }

    private Basket findById(Integer id) {
        logger.debug("Finding Basket by id" + id);
        Basket basket = basketDAO.findById(id);
        return basket;
    }


    private Basket getBasketByUserIdAndBookId(Integer userId, Integer bookId) {
        logger.debug("Getting basket by User id and Book id"+userId+ " AND "+ bookId);
        Basket basket = basketDAO.getBasketByUserIdAndBookId(userId, bookId);
        return basket;
    }


    private List<Basket> getBasketByUserId(Integer userId) {

        logger.debug("Getting basket by User id"+userId);
        List<Basket> baskets = basketDAO.getBasketByUserId(userId);
        return baskets;
    }

    private UserDTO getUser() {
        AppUserPrincipal principal = (AppUserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return UserConverter.converter(userDAO.findById(principal.getUserId())) ;
    }
    private  Basket  converter(BasketDTO basketDTO){
        Basket basket = new Basket();
        basket.setBookId(basketDTO.getBookId());
        basket.setBookQuantity(basketDTO.getBookQuantity());
        basket.setBasketId(basketDTO.getBasketId());
        basket.setBookPrice(basketDTO.getBookPrice());
        basket.setUser(basketDTO.getUser());
        basket.setBookName(basketDTO.getBookName());
        return basket;
    }
    private  BasketDTO setBasketDTO (BookDTO bookDTO, String bookQuantity){
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setBookName(bookDTO.getBookName());
        basketDTO.setBookQuantity(bookQuantity);
        basketDTO.setBookPrice(bookDTO.getBookPrice());
        basketDTO.setBookId(bookDTO.getBookId());
        return basketDTO;
    }

}
