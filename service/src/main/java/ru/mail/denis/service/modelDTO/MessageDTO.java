package ru.mail.denis.service.modelDTO;

import java.io.Serializable;

/**
 * Created by user on 28.08.2017.
 */
public class MessageDTO implements Serializable {
    private Integer messageId;
    private String authorEmail;
    private String messageText;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
