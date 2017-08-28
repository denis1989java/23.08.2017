package ru.mail.denis.repositories;

import ru.mail.denis.repositories.model.Basket;
import ru.mail.denis.repositories.model.Message;

import java.util.List;

/**
 * Created by user on 28.08.2017.
 */
public interface MessageDAO extends GenericDao<Message, Integer>  {

    List<Message> getMessagesByParts(Integer pageId, Integer total);
}
