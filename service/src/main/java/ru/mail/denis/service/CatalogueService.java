package ru.mail.denis.service;

import ru.mail.denis.service.model.BookDTO;
import ru.mail.denis.service.model.ViewDTO;

/**
 * Created by Denis Monich on 05.07.2017.
 */
public interface CatalogueService {


    ViewDTO viewPage(Integer page, String orderId);

    BookDTO getBookById(Integer bookId);

    void  updateBook(BookDTO bookDTO);

    void deleteBook(Integer bookId);

    void  saveBook(BookDTO bookDTO);
}
