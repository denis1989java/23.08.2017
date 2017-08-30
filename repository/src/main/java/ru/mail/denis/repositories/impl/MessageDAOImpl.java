package ru.mail.denis.repositories.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.MessageDAO;
import ru.mail.denis.repositories.model.Book;
import ru.mail.denis.repositories.model.Message;

import javax.persistence.NoResultException;
import java.util.*;


/**
 * Created by Denis Monich on 28.08.2017.
 */
@Repository
public class MessageDAOImpl extends GenericDaoImpl <Message,Integer> implements MessageDAO {
    private static final Logger logger = Logger.getLogger(MessageDAOImpl.class);
    @Autowired
    private Properties properties;

    @Override
    public List<Message> getMessagesByParts(Integer pageId, Integer total) {
        Query query = getSession().createQuery(properties.getProperty("get.messages.by.parts"));
        query.setFirstResult(pageId);
        query.setMaxResults(total);
        List<Message> messages = new ArrayList<>();
        try {
            messages = query.list();
        } catch (NoResultException nre) {
            logger.error("Exception finding getMessagesByParts: no messages");
        }
        return messages;
    }

    @Override
    public Long getMessagesQuantity(){
        Query query = getSession().createQuery(properties.getProperty("get.message.quantity"));
        Long quantity= (Long) query.uniqueResult();
        return quantity;
    }


}
