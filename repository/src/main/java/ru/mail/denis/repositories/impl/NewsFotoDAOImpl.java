package ru.mail.denis.repositories.impl;

import org.springframework.stereotype.Repository;
import ru.mail.denis.repositories.NewsFotoDAO;
import ru.mail.denis.repositories.model.NewFoto;

/**
 * Created by Denis Monich on 29.08.2017.
 */
@Repository
public class NewsFotoDAOImpl extends GenericDaoImpl <NewFoto,Integer> implements NewsFotoDAO {
}
