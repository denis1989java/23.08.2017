package ru.mail.denis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.denis.repositories.model.News;
import ru.mail.denis.repositories.NewsDAO;
import ru.mail.denis.service.modelDTO.NewsDTO;
import ru.mail.denis.service.modelDTO.ViewDTO;
import ru.mail.denis.service.NewService;
import ru.mail.denis.service.util.NewsConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by user on 25.06.2017.
 */
@Service
public class NewServiceImpl implements NewService {

    private final NewsDAO newsDAO;
    private static final Logger logger = Logger.getLogger(NewServiceImpl.class);

    @Autowired
    public NewServiceImpl(NewsDAO newsDAO) {
        this.newsDAO = newsDAO;
    }


    @Override
    @Transactional
    public ViewDTO viewPage(Integer page){
        int total=7;
        if (page != 0) {
            page = page * total;
        }
        List<NewsDTO> newsDTOS=NewsConverter.converter(getNewsByParts(page, total));
        Integer newsQuantity =findAll().size();
        List<Integer> pagination = new ArrayList();
        Integer pageQuantity = 0;
        if (newsQuantity % total == 0) {
            pageQuantity = newsQuantity / total;
        } else {
            pageQuantity = newsQuantity / total + 1;
        }
        for (Integer i = 0; i < pageQuantity; i++) {
            pagination.add(i);
        }
        Map <String,Object> map=new HashMap<>();
        map.put("newsDTOS",newsDTOS);
        map.put("pagination",pagination);
        ViewDTO viewDTO=new ViewDTO();
        viewDTO.setViewMap(map);
        return viewDTO;
    }


    @Override
    @Transactional
    public NewsDTO getNewById(Integer newsId) {
        return NewsConverter.converter(findById(newsId));
    }

    @Override
    @Transactional
    public void deleteNew(Integer newsId) {
        delete(findById(newsId));
    }

    @Override
    @Transactional
    public void updateNew(NewsDTO newsDTO) {
        newsDTO.setNewsId(newsDTO.getNewsId());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        newsDTO.setNewsDate(dateFormat.format(date));
        updateNews(NewsConverter.converter(newsDTO));
    }

    @Override
    @Transactional
    public void addNew(NewsDTO newsDTO) {
        saveNews(NewsConverter.converter(newsDTO));
    }

    private List<News> findAll() {
        logger.debug("Finding all news");
        List<News> news = newsDAO.findAll();
        return news;
    }

    private void delete(News news) {

        logger.debug("Deleting new");
        newsDAO.delete(news);
    }

    private void saveNews(News news) {

        logger.debug("Saving new");
        newsDAO.save(news);
    }

    private void updateNews(News news) {
        logger.debug("Updating new");
        newsDAO.update(news);
    }

    private News findById(Integer id) {

        logger.debug("Finding new by id");
        News news = newsDAO.findById(id);
        return news;
    }

    private List<News> getNewsByParts(Integer pageId, Integer total) {

        logger.debug("Getting news by parts");
        List<News> news = newsDAO.getNewsByParts(pageId, total);
        return news;
    }
}
