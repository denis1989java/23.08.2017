package ru.mail.denis.repositories.DAO;

import ru.mail.denis.models.Book;
import ru.mail.denis.models.News;

import java.util.List;

/**
 * Created by user on 09.08.2017.
 */
public interface CatalogueDAO extends GenericDao<Book, Integer> {
    List<Book> getCatalogueByParts(Integer pageId, Integer total);
}
