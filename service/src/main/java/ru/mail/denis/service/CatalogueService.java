package ru.mail.denis.service;

import ru.mail.denis.service.DTOmodels.BookDTO;

import java.util.List;

/**
 * Created by user on 05.07.2017.
 */
public interface CatalogueService {


    List<BookDTO> getBooks(int pageId, int total);

    Integer booksQuantity();

    BookDTO getBookById(Integer bookId);

    void  updateBook(BookDTO bookDTO);

    void deleteBook(Integer bookId);

    void  saveBook(BookDTO bookDTO);
}
