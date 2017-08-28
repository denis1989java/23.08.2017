package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.MessageDAO;
import ru.mail.denis.repositories.model.Book;
import ru.mail.denis.repositories.model.Message;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


/**
 * Created by user on 28.08.2017.
 */
@Repository
public class MessageDAOImpl extends GenericDaoImpl <Message,Integer> implements MessageDAO {
    private static final Logger logger = Logger.getLogger(MessageDAOImpl.class);
    ResourceBundle resourceBundle = PropertyResourceBundle.getBundle("query");


    @Override
    public List<Message> getMessagesByParts(Integer pageId, Integer total) {
        String hql=resourceBundle.getString("getMessagesByParts");
        Query query = getSession().createQuery(hql);
        query.setFirstResult(pageId);
        query.setMaxResults(total);
        List<Message> messages = null;
        try {
            messages = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception finding getMessagesByParts: no messages");
        }
        return messages;
    }



}
