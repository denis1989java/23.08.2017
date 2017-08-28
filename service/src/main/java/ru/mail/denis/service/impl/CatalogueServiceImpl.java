package ru.mail.denis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.repositories.model.Book;
import ru.mail.denis.repositories.CatalogueDAO;
import ru.mail.denis.repositories.model.Changable;
import ru.mail.denis.service.CatalogueService;
import ru.mail.denis.service.modelDTO.BookDTO;
import ru.mail.denis.service.modelDTO.ViewDTO;
import ru.mail.denis.service.util.BookConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by user on 25.06.2017.
 */
@Service
public class CatalogueServiceImpl implements CatalogueService {

    private final CatalogueDAO catalogueDAO;
    private static final Logger logger = Logger.getLogger(CatalogueServiceImpl.class);

    @Autowired
    public CatalogueServiceImpl(CatalogueDAO catalogueDAO) {
        this.catalogueDAO = catalogueDAO;
    }

   @Override
   @Transactional
    public ViewDTO viewPage(Integer page, String orderId){
        int total=7;
        if (page != 0) {
            page = page * total;
        }
        List<BookDTO> bookDTOS=BookConverter.converter(getcatalogueByParts(page, total));
        Integer booksQuantity =findAll().size();
        List<Integer> pagination = new ArrayList();
        Integer pageQuantity = 0;
        if (booksQuantity % total == 0) {
            pageQuantity = booksQuantity / total;
        } else {
            pageQuantity = booksQuantity / total + 1;
        }
        for (Integer i = 0; i < pageQuantity; i++) {
            pagination.add(i);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("catalogue",bookDTOS);
        map.put("pagination",pagination);
        map.put("CHANGABLE","CHANGABLE");
        map.put("orderId", orderId);
        ViewDTO viewDTO=new ViewDTO();
        viewDTO.setViewMap(map);
        return viewDTO;
    }

    @Override
    @Transactional
    public BookDTO getBookById(Integer bookId) {
        return BookConverter.converter(findById(bookId));
    }

    @Override
    @Transactional
    public void updateBook(BookDTO bookDTO) {
        updateBook(BookConverter.converter(bookDTO));
    }

    @Override
    @Transactional
    public void deleteBook(Integer bookId){
        deleteBook(findById(bookId));
    }

    @Override
    @Transactional
    public void saveBook(BookDTO bookDTO) {
        bookDTO.setChangable(Changable.CHANGABLE);
        saveBook(BookConverter.converter(bookDTO));
    }

    private List<Book> findAll() {
        logger.debug("Finding all books");
        List<Book> catalogue = catalogueDAO.findAll();
        return catalogue;
    }

    private void deleteBook(Book book) {
        logger.debug("Deleting Book");
        catalogueDAO.delete(book);
    }

    private void saveBook(Book book) {
        logger.debug("Saving Book");
        catalogueDAO.save(book);
    }

    private void updateBook(Book book) {
        logger.debug("Updating book");
        catalogueDAO.update(book);
    }

    private Book findById(Integer id) {
        logger.debug("Finding book by id");
        Book book = catalogueDAO.findById(id);
        return book;
    }

    private List<Book> getcatalogueByParts(Integer pageId, Integer total) {
        logger.debug("Getting Catalogue by parts");
        List<Book> catalogue = catalogueDAO.getCatalogueByParts(pageId, total);
        return catalogue;
    }

}
