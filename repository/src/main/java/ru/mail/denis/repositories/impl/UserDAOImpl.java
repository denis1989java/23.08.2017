package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.UserDAO;
import ru.mail.denis.repositories.model.User;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


/**
 * Created by user on 04.08.2017.
 */
@Repository
public class UserDAOImpl extends GenericDaoImpl<User, Integer> implements UserDAO {
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);
    ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("query");

    @Override
    public User findByEmail(String email) {
        User user = null;
        String hql=resourceBundle.getString("findByEmail");
        System.out.println(hql);
        Query query = getSession().createQuery(hql);
        query.setParameter("email", email);
        try {
            user = (User) query.uniqueResult();
        } catch (NoResultException nre) {
            logger.error("Exception findByEmail: no user");
        }
        return user;
    }

    @Override
    public List<User> getUsersByParts(Integer pageId, Integer total) {
        String hql=resourceBundle.getString("getUsersByParts");
        Query query = getSession().createQuery(hql);
        query.setFirstResult(pageId);
        query.setMaxResults(total);
        List<User> users = null;
        try {
            users = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception getUsersByParts: no users");
        }
        return users;
    }


}
