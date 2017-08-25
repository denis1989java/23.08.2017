package ru.mail.denis.repositories;

import ru.mail.denis.repositories.model.Book;

import java.util.List;

/**
 * Created by user on 09.08.2017.
 */
public interface CatalogueDAO extends GenericDao<Book, Integer> {
    List<Book> getCatalogueByParts(Integer pageId, Integer total);
}
