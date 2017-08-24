package ru.mail.denis.repositories.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by user on 04.08.2017.
 */
@SuppressWarnings("unchecked")
@Repository
public abstract class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {

    private final Class<T> entityClass;

    public GenericDaoImpl() {
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Autowired
    private SessionFactory sessionFactory;


    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public T findById(ID id) {
        return (T) getSession().get(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        Query query = getSession().createQuery("from " + entityClass.getName());
        return query.list();
    }

    @Override
    public void save(T entity) {
        getSession().save(entity);
    }

    @Override
    public void update(T entity) {
        getSession().update(entity);
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }

}
