package ru.mail.denis.repositories;


import ru.mail.denis.repositories.model.UserInformation;

/**
 * Created by Denis Monich on 08.08.2017.
 */
public interface UserInformationDAO extends GenericDao<UserInformation, Integer> {
    UserInformation findUserInformationByUserId(Integer userId);

}
