package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.UserInformationDAO;
import ru.mail.denis.repositories.model.UserInformation;

import javax.persistence.NoResultException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by user on 08.08.2017.
 */
@Repository
public class UserInformationDAOImpl extends GenericDaoImpl<UserInformation, Integer> implements UserInformationDAO {
    private static final Logger logger = Logger.getLogger(UserInformationDAOImpl.class);
    ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("query");


    @Override
    public UserInformation findUserInformationByUserId(Integer userId) {
        UserInformation userInformation = null;
        String hql=resourceBundle.getString("findUserInformationByUserId");
        Query query = getSession().createQuery(hql);
        query.setParameter("userId", userId);
        try {
            userInformation = (UserInformation) query.uniqueResult();
        } catch (NoResultException nre) {
            logger.error("Exception findUserInformationByUserId: no UserInformation");
        }
        return userInformation;
    }
}
