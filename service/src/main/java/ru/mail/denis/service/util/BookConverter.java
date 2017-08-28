package ru.mail.denis.service.util;

import ru.mail.denis.repositories.model.Book;
import ru.mail.denis.service.modelDTO.BookDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 24.08.2017.
 */
public class BookConverter {
    public BookConverter() {
    }

    public static Book converter (BookDTO bookDTO){
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

    public static BookDTO converter(Book book){
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

    public static List<BookDTO> converter (List<Book> books){
        List<BookDTO> bookDTOS = new ArrayList<>();
        for (Book book : books) {
            BookDTO bookDTO = BookConverter.converter(book);
            bookDTOS.add(bookDTO);
        }
        return bookDTOS;
    }
}
