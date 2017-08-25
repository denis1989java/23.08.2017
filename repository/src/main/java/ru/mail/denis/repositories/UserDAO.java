package ru.mail.denis.repositories;


import ru.mail.denis.repositories.model.User;

import java.util.List;

/**
 * Created by user on 04.08.2017.
 */
public interface UserDAO extends GenericDao<User, Integer> {
    User findByEmail(String email);

    List<User> getUsersByParts(Integer pageId, Integer total);
}
