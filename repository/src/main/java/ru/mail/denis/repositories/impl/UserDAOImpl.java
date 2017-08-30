package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.UserDAO;
import ru.mail.denis.repositories.model.User;

import javax.persistence.NoResultException;
import java.util.*;


/**
 * Created by Denis Monich on 04.08.2017.
 */
@Repository
public class UserDAOImpl extends GenericDaoImpl<User, Integer> implements UserDAO {
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

    @Autowired
    private Properties properties;

    @Override
    public User findByEmail(String email) {
        User user = null;
        Query query = getSession().createQuery(properties.getProperty("find.by.email"));
        query.setParameter("email", email);
        try {
            user = (User) query.uniqueResult();
        } catch (NoResultException nre) {
            logger.error("Exception findByEmail:"+email+" no user");
        }
        return user;
    }

    @Override
    public List<User> getUsersByParts(Integer pageId, Integer total) {
        Query query = getSession().createQuery(properties.getProperty("get.users.by.parts"));
        query.setFirstResult(pageId);
        query.setMaxResults(total);
        List<User> users = new ArrayList<>();
        try {
            users = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception getUsersByParts: no users");
        }
        return users;
    }
    @Override
    public Long getUsersQuantity(){
        Query query = getSession().createQuery(properties.getProperty("get.users.quantity"));
        Long quantity= (Long) query.uniqueResult();
        return quantity;
    }


}
