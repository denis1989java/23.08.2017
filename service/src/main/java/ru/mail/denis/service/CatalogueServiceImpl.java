package ru.mail.denis.service;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.models.Book;
import ru.mail.denis.repositories.DAO.CatalogueDAO;
import ru.mail.denis.repositories.DAO.CatalogueDAOImpl;
import ru.mail.denis.service.DTOmodels.BookDTO;

import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

import static ru.mail.denis.repositories.HibernateUtil.sessionFactory;

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
        List<Book> books = getcatalogueByParts(pageId, total);
        List<BookDTO> bookDTOS = new ArrayList<>();
        for (Book book : books) {
            BookDTO bookDTO = booktoBookDTO(book);
            bookDTOS.add(bookDTO);
        }
        return bookDTOS;
    }

    @Override
    @Transactional
    public Integer booksQuantity() {
        Integer quantity=findAll().size();
        return quantity;
    }

    @Override
    @Transactional
    public BookDTO getBookById(Integer bookId) {
        BookDTO bookDTO=booktoBookDTO(findById(bookId));
        return bookDTO;
    }

    @Override
    @Transactional
    public void updateBook(BookDTO bookDTO) {
        updateBook(bookDTOToBook(bookDTO));
    }
    @Override
    @Transactional
    public void deleteBook(Integer bookId){
        Book book=findById(bookId);
        deleteBook(book);
    }

    @Override
    @Transactional
    public void saveBook(BookDTO bookDTO) {
        saveBook(bookDTOToBook(bookDTO));
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

    private Book bookDTOToBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setChangable(bookDTO.getChangable());
        book.setBookAuthor(bookDTO.getBookAuthor());
        book.setBookDescription(bookDTO.getBookDescription());
        book.setBookName(bookDTO.getBookName());
        book.setBookPrice(bookDTO.getBookPrice());
        book.setBookQuantity(bookDTO.getBookQuantity());
        book.setBookId(bookDTO.getBookId());
        return book;
    }

    private BookDTO booktoBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(book.getBookId());
        bookDTO.setChangable(book.getChangable());
        bookDTO.setBookName(book.getBookName());
        bookDTO.setBookPrice(book.getBookPrice());
        bookDTO.setBookAuthor(book.getBookAuthor());
        bookDTO.setBookDescription(book.getBookDescription());
        bookDTO.setBookQuantity(book.getBookQuantity());
        return bookDTO;
    }
}
