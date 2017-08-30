package ru.mail.denis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import ru.mail.denis.repositories.NewsFotoDAO;
import ru.mail.denis.repositories.model.News;
import ru.mail.denis.repositories.NewsDAO;
import ru.mail.denis.repositories.model.NewFoto;
import ru.mail.denis.service.model.NewsDTO;
import ru.mail.denis.service.model.ViewDTO;
import ru.mail.denis.service.NewService;
import ru.mail.denis.service.util.DateConverter;
import ru.mail.denis.service.util.NewsConverter;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Denis Monich on 25.06.2017.
 */
@Service
public class NewServiceImpl implements NewService {

    private final NewsDAO newsDAO;
    private final NewsFotoDAO newsFotoDAO;
    private static final Logger logger = Logger.getLogger(NewServiceImpl.class);
    private Properties properties;

    @Autowired
    public NewServiceImpl(NewsDAO newsDAO, NewsFotoDAO newsFotoDAO, Properties properties) {
        this.newsDAO = newsDAO;
        this.newsFotoDAO = newsFotoDAO;
        this.properties = properties;
    }


    @Override
    @Transactional
    public ViewDTO viewPage(Integer page){
        int total=7;
        if (page != 0) {
            page = page * total;
        }
        List<NewsDTO> newsDTOS=converter(getNewsByParts(page, total));
        Long newsQuantity =newsQuantity();
        List<Long> pagination = new ArrayList();
        Long pageQuantity = Long.valueOf(0);
        if (newsQuantity % total == 0) {
            pageQuantity = newsQuantity / total;
        } else {
            pageQuantity = newsQuantity / total + 1;
        }
        for (Long i = Long.valueOf(0); i < pageQuantity; i++) {
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
        newsDTO.setNewsDate(DateConverter.converter());
        updateNews(NewsConverter.converter(newsDTO));
    }
    @Override
    @Transactional
    public File findNewsFotoById(Integer id){
        NewFoto newFoto =newsFotoDAO.findById(id);
        return new File(newFoto.getFotoLocation());
    }

    @Override
    @Transactional
    public void addNew(NewsDTO newsDTO) {
        newsDTO.setNewsDate(DateConverter.converter());
        News news=NewsConverter.converter(newsDTO);
        try {
            NewFoto newFoto = getNewsFoto(newsDTO);
            news.setFoto(newFoto);
            saveNews(news);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void delete(News news) {

        logger.debug("Deleting new"+news.getNewsId());
        newsDAO.delete(news);
    }

    private void saveNews(News news) {

        logger.debug("Saving new");
        newsDAO.save(news);
    }

    private void updateNews(News news) {
        logger.debug("Updating new" +news.getNewsId());
        newsDAO.update(news);
    }

    private News findById(Integer id) {
        logger.debug("Finding new by id" +id);
        News news = newsDAO.findById(id);
        return news;
    }

    private Long newsQuantity () {
        logger.debug("Finding news quantity");
        Long quantity = newsDAO.getNewsQuantity();
        return quantity;
    }

    private List<News> getNewsByParts(Integer pageId, Integer total) {

        logger.debug("Getting news by parts");
        List<News> news = newsDAO.getNewsByParts(pageId, total);
        return news;
    }



    private NewFoto getNewsFoto(NewsDTO newsDTO) throws IOException {
        String fileLocation = properties.getProperty("upload.location") + System.currentTimeMillis() +".jpg";
        FileCopyUtils.copy(newsDTO.getNewsFoto().getBytes(), new File(fileLocation));
        NewFoto newFoto = new NewFoto();
        newFoto.setFotoLocation(fileLocation);
        newFoto.setFotoName(newsDTO.getNewsFoto().getName());
        return newFoto;
    }

    private List<NewsDTO> converter(List<News>news){
        List<NewsDTO> newsDTOS = new ArrayList<>();
        for (News new1 : news) {
            NewsDTO newsDTO = NewsConverter.converter(new1);
            newsDTOS.add(newsDTO);
        }
        return newsDTOS;
    }
}
