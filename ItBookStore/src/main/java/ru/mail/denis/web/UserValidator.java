package ru.mail.denis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.mail.denis.service.DTOmodels.UserDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 18.08.2017.
 */

@Component
public class UserValidator implements Validator {

    private Pattern patternEmail;
    private Pattern patternPassword;
    private Matcher matcherEmail;
    private Matcher matcherPassword;


    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UserDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        String EMAIL_PATTERN="[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}";
        String PASSWORD_PATTER = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$";
        UserDTO userDTO = (UserDTO) o;
        ValidationUtils.rejectIfEmpty(errors, "userName", "error.userName.empty");
        ValidationUtils.rejectIfEmpty(errors, "userSurname", "error.userSurname.empty");
        ValidationUtils.rejectIfEmpty(errors, "userSecondName", "error.userSecondName.empty");
        ValidationUtils.rejectIfEmpty(errors, "userPhoneNumber", "error.userPhoneNumber.empty");
        ValidationUtils.rejectIfEmpty(errors, "userAddress", "error.userAddress.empty");
        ValidationUtils.rejectIfEmpty(errors, "userAdditionalInfo", "error.userAdditionalInfo.empty");
        ValidationUtils.rejectIfEmpty(errors, "userEmail", "error.userEmail.empty");
        ValidationUtils.rejectIfEmpty(errors, "userPassword", "error.userPassword.empty");
        ValidationUtils.rejectIfEmpty(errors, "repeatePassword", "error.repeatePassword.empty");
        patternEmail=Pattern.compile(EMAIL_PATTERN);
        patternPassword=Pattern.compile(PASSWORD_PATTER);
        matcherEmail=patternEmail.matcher(userDTO.getUserEmail());
        matcherPassword=patternPassword.matcher(userDTO.getUserPassword());
        if (!matcherEmail.matches()) {
            errors.rejectValue("userEmail", "error.userEmail.regex");
        }
        if (!matcherPassword.matches()) {
            errors.rejectValue("userPassword", "error.userPassword.regex");
        }
       try{
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUserEmail());
           if (userDetails != null) {
               errors.rejectValue("userEmail", "error.userEmail.exist");
           }
       }catch (UsernameNotFoundException e){
            e.getStackTrace();
       }

       if (!userDTO.getRepeatePassword().equals(userDTO.getUserPassword())){
           errors.rejectValue("repeatePassword", "error.repeatePassword.wrong");
       }

    }
}

