package ru.mail.denis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.repositories.model.Book;
import ru.mail.denis.repositories.CatalogueDAO;
import ru.mail.denis.service.CatalogueService;
import ru.mail.denis.service.DTOmodels.BookDTO;
import ru.mail.denis.service.util.BookConverter;

import java.util.List;


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
    public List<BookDTO> getBooks(int pageId, int total) {
        return BookConverter.converter(getcatalogueByParts(pageId, total));
    }

    @Override
    @Transactional
    public Integer booksQuantity() {
        return findAll().size();
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
