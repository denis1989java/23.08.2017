package ru.mail.denis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.repositories.model.Book;
import ru.mail.denis.repositories.CatalogueDAO;
import ru.mail.denis.repositories.model.Changable;
import ru.mail.denis.service.CatalogueService;
import ru.mail.denis.service.model.BookDTO;
import ru.mail.denis.service.model.ViewDTO;
import ru.mail.denis.service.util.BookConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Denis Monich on 25.06.2017.
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
        int pageNumber = 0;
        if (page != 0) {
            page = page * total;
        }
        List<BookDTO> bookDTOS=converter(getcatalogueByParts(page, total));
        Long booksQuantity =catalogueQuantity();
        List<Long> pagination = new ArrayList();
        Long pageQuantity = Long.valueOf(0);
        if (booksQuantity % total == 0) {
            pageQuantity = booksQuantity / total;
        } else {
            pageQuantity = booksQuantity / total + 1;
        }
        for (Long i = Long.valueOf(0); i < pageQuantity; i++) {
            pagination.add(i);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("page",pageNumber);
        map.put("orderId",orderId);
        map.put("catalogue",bookDTOS);
        map.put("pagination",pagination);
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

    private Long catalogueQuantity () {
        logger.debug("Finding books quantity");
        Long quantity = catalogueDAO.getCatalogueQuantity();
        return quantity;
    }

    private void deleteBook(Book book) {
        logger.debug("Deleting Book"+book.getBookId());
        catalogueDAO.delete(book);
    }

    private void saveBook(Book book) {
        logger.debug("Saving Book");
        catalogueDAO.save(book);
    }

    private void updateBook(Book book) {
        logger.debug("Updating book" +book.getBookId());
        catalogueDAO.update(book);
    }

    private Book findById(Integer id) {
        logger.debug("Finding book by id"+id);
        Book book = catalogueDAO.findById(id);
        return book;
    }

    private List<Book> getcatalogueByParts(Integer pageId, Integer total) {
        logger.debug("Getting Catalogue by parts");
        List<Book> catalogue = catalogueDAO.getCatalogueByParts(pageId, total);
        return catalogue;
    }
    private List<BookDTO> converter (List<Book> books){
        List<BookDTO> bookDTOS = new ArrayList<>();
        for (Book book : books) {
            BookDTO bookDTO = BookConverter.converter(book);
            bookDTOS.add(bookDTO);
        }
        return bookDTOS;
    }

}
