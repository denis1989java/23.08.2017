package ru.mail.denis.repositories;


import ru.mail.denis.repositories.model.UserInformation;

/**
 * Created by user on 08.08.2017.
 */
public interface UserInformationDAO extends GenericDao<UserInformation, Integer> {
    UserInformation findUserInformationByUserId(Integer userId);

}
