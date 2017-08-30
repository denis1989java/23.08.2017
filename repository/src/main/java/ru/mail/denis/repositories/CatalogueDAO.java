package ru.mail.denis.repositories;

import ru.mail.denis.repositories.model.Book;

import java.util.List;

/**
 * Created by Denis Monich on 09.08.2017.
 */
public interface CatalogueDAO extends GenericDao<Book, Integer> {
    List<Book> getCatalogueByParts(Integer pageId, Integer total);

    Long getCatalogueQuantity();
}
