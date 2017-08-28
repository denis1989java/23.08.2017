package ru.mail.denis.service;

import ru.mail.denis.service.modelDTO.BookDTO;
import ru.mail.denis.service.modelDTO.ViewDTO;

/**
 * Created by user on 05.07.2017.
 */
public interface CatalogueService {


    ViewDTO viewPage(Integer page, String orderId);

    BookDTO getBookById(Integer bookId);

    void  updateBook(BookDTO bookDTO);

    void deleteBook(Integer bookId);

    void  saveBook(BookDTO bookDTO);
}
