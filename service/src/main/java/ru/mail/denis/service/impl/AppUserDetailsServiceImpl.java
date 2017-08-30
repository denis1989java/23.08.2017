package ru.mail.denis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.repositories.model.User;
import ru.mail.denis.repositories.UserDAO;
import ru.mail.denis.service.model.AppUserPrincipal;

/**
 * Created by Denis Monich on 15.08.2017.
 */
@Service(value = "appUserDetailsService")
public class AppUserDetailsServiceImpl implements UserDetailsService {

    private final UserDAO userDao;

    @Autowired
    public AppUserDetailsServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userDao.findByEmail(username);
        if (user == null) {
            return null;
        }
        return new AppUserPrincipal(user);
    }
}
