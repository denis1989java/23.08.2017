package ru.mail.denis.service;

import ru.mail.denis.service.model.MessageDTO;
import ru.mail.denis.service.model.ViewDTO;

/**
 * Created by Denis Monich on 28.08.2017.
 */
public interface MessageService {
    void saveMessage(MessageDTO messageDTO);

    ViewDTO viewPage(Integer page, String userEmail, String messageID);
}
