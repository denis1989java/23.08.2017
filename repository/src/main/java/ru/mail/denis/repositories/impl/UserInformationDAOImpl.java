package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.UserInformationDAO;
import ru.mail.denis.repositories.model.UserInformation;

import javax.persistence.NoResultException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by Denis Monich on 08.08.2017.
 */
@Repository
public class UserInformationDAOImpl extends GenericDaoImpl<UserInformation, Integer> implements UserInformationDAO {
    private static final Logger logger = Logger.getLogger(UserInformationDAOImpl.class);
    @Autowired
    private Properties properties;

    @Override
    public UserInformation findUserInformationByUserId(Integer userId) {
        UserInformation userInformation = null;
        Query query = getSession().createQuery(properties.getProperty("find.userInformation.by.userId"));
        query.setParameter("userId", userId);
        try {
            userInformation = (UserInformation) query.uniqueResult();
        } catch (NoResultException nre) {
            logger.error("Exception findUserInformationByUserId:"+userId+" no UserInformation");
        }
        return userInformation;
    }
}
