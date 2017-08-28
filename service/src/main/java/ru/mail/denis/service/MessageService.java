package ru.mail.denis.service;

import ru.mail.denis.service.modelDTO.MessageDTO;
import ru.mail.denis.service.modelDTO.ViewDTO;

/**
 * Created by user on 28.08.2017.
 */
public interface MessageService {
    void saveMessage(MessageDTO messageDTO);

    ViewDTO viewPage(Integer page, String userEmail, String messageID);
}
