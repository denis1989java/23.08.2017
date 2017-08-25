package ru.mail.denis.repositories;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 04.08.2017.
 */
public interface GenericDao<T, ID extends Serializable> {
    T findById(ID id);

    List<T> findAll();

    void save(T entity);

    void update(T entity);

    void delete(T entity);
}
