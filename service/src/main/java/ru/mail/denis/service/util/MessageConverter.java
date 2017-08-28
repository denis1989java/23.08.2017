package ru.mail.denis.service.util;

import ru.mail.denis.repositories.model.Message;
import ru.mail.denis.service.MessageService;
import ru.mail.denis.service.modelDTO.MessageDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 28.08.2017.
 */
public class MessageConverter {

    public static Message converter(MessageDTO messageDTO){
        Message message=new Message();
        message.setAuthorEmail(messageDTO.getAuthorEmail());
        message.setMessageId(messageDTO.getMessageId());
        message.setMessageText(messageDTO.getMessageText());
        return message;
    }

    public static MessageDTO converter(Message message){
        MessageDTO messageDTO=new MessageDTO();
        messageDTO.setAuthorEmail(message.getAuthorEmail());
        messageDTO.setMessageId(message.getMessageId());
        messageDTO.setMessageText(message.getMessageText());
        return messageDTO;
    }


    public static List<MessageDTO> converter (List <Message> messages){
        List <MessageDTO> messageDTOS=new ArrayList<>();
        for (Message message : messages) {
            MessageDTO messageDTO=MessageConverter.converter(message);
            messageDTOS.add(messageDTO);
        }
        return messageDTOS;
    }
}
