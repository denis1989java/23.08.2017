package ru.mail.denis.web.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.mail.denis.service.model.MessageDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Denis Monich on 28.08.2017.
 */
@Component
public class MessageValidator implements Validator {

    private Pattern patternEmail;
    private Matcher matcherEmail;


    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(MessageDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        String EMAIL_PATTERN="[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}";


        MessageDTO messageDTO=(MessageDTO) o;
        ValidationUtils.rejectIfEmpty(errors, "authorEmail", "error.email.empty");
        ValidationUtils.rejectIfEmpty(errors, "messageText", "error.text.empty");

        patternEmail=Pattern.compile(EMAIL_PATTERN);
        matcherEmail=patternEmail.matcher(messageDTO.getAuthorEmail());
        if (!matcherEmail.matches()) {
            errors.rejectValue("authorEmail", "error.userEmail.regex");
        }
    }
}
