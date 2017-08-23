package ru.mail.denis.web.servlets.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.denis.service.NewService;
import ru.mail.denis.service.DTOmodels.NewsDTO;

import javax.naming.Context;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by user on 23.06.2017.
 */
@Controller
public class NewsController{

    @Autowired
    private NewService newService;

    public NewsController(NewService newService) {
        this.newService = newService;
    }


    @RequestMapping(value = "*/news/{page}", method = RequestMethod.GET)
    public ModelAndView showNews(@PathVariable int page){
        int total=7;
        if (page != 0) {
         page = page * total;
       }
        List<NewsDTO> newsDTOS=newService.getNews(page,total);
        Integer newsQuantity = newService.newsQuantity();
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
        ModelAndView modelAndView=new ModelAndView("news");
        modelAndView.addAllObjects(map);
        return modelAndView;
    }
    @RequestMapping(value = "*/new/{newsId}" ,method = RequestMethod.GET)
    public ModelAndView showNew(@PathVariable int newsId){
        NewsDTO news = newService.getNewById(newsId);
        ModelAndView modelAndView=new ModelAndView("new");
        modelAndView.addObject("news",news);
        return modelAndView;
    }
    @RequestMapping(value ={"/admin/changeNew/{newsId}","/superAdmin/changeNew/{newsId}"} , method = RequestMethod.GET)
    public String changeNew(@PathVariable int newsId, Model model){
        NewsDTO news = newService.getNewById(newsId);
        model.addAttribute("news",news);
        return "changeNew";
    }
    @RequestMapping(value ={"/admin/saveNew","/superAdmin/saveNew"}, method = RequestMethod.POST)
    public String updateNew(@ModelAttribute ("news") NewsDTO newsDTO){
        newsDTO.setNewsId(newsDTO.getNewsId());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        newsDTO.setNewsDate(dateFormat.format(date));
        newService.updateNew(newsDTO);
        return "redirect:news/0";
    }

    @RequestMapping(value = "/admin/deleteNew/{newsId}",method = RequestMethod.GET)
    public String adminDeleteNew(@PathVariable int newsId){
        newService.deleteNew(newsId);
        return "redirect:/admin/news/0";
    }
    @RequestMapping(value = "/superAdmin/deleteNew/{newsId}",method = RequestMethod.GET)
    public String superAdminDeleteNew(@PathVariable int newsId){
        newService.deleteNew(newsId);
        return "redirect:/superAdmin/news/0";
    }

    @RequestMapping (value ={"/admin/addNews","/superAdmin/addNews"} , method = RequestMethod.GET)
    public String addNews (Model model){
        model.addAttribute("news", new NewsDTO());
        return "addNew";
    }



    @RequestMapping(value ={"/admin/saveNews","/superAdmin/saveNews"} ,method = RequestMethod.POST)
    public String saveNews (@RequestParam ("newName") String newName,@RequestParam ("newText") String newText,@RequestParam ("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException, ServletException {
        NewsDTO newsDTO=new NewsDTO();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        newsDTO.setNewsDate(dateFormat.format(date));
        String fileName = date.getTime()+".png";
        newsDTO.setNewsName(newName);
        newsDTO.setNewsText(newText);
        if(!multipartFile.isEmpty()){
            byte [] bytes=multipartFile.getBytes();
            BufferedOutputStream stream=new BufferedOutputStream(new FileOutputStream(new File("D:/откат/hdgfsjd/ItBookStore/ItBookStore/src/main/webapp/resources/images/"+fileName)));
            stream.write(bytes);
            stream.flush();
            stream.close();
        }
        newsDTO.setNewsFoto(fileName);
        newService.addNew(newsDTO);
        return "redirect:news/0";
    }



}




//public class NewsServlet extends HttpServlet {
//    NewServiceImpl newService;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String spageId = req.getParameter("page");
//        int pageId = Integer.parseInt(spageId);
//        int total = 7;
//        if (pageId != 0) {
//            pageId = pageId * total;
//        }
//        List<NewsDTO> news = newService.getNews(pageId, total);
//        req.setAttribute("news", news);
//        Integer newsQuantity = newService.newsQuantity();
//        req.setAttribute("page", spageId);
//        List<Integer> pagination = new ArrayList();
//        Integer pageQuantity = 0;
//        if (newsQuantity % total == 0) {
//            pageQuantity = newsQuantity / total;
//        } else {
//            pageQuantity = newsQuantity / total + 1;
//        }
//        for (Integer i = 0; i < pageQuantity; i++) {
//            pagination.add(i);
//        }
//        req.setAttribute("pagination", pagination);
//        if (req.getRequestURI().contains("user")) {
//            req.setAttribute("roleUser", Role.USER);
//        } else if (req.getRequestURI().contains("admin")) {
//            req.setAttribute("roleAdmin", Role.ADMIN);
//        } else if (req.getRequestURI().contains("superAdmin")) {
//            req.setAttribute("roleSuperAdmin", Role.SUPER_ADMIN);
//        }
//        req.getRequestDispatcher("/WEB-INF/news.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String delete = req.getParameter("delete");
//        if (delete != null) {
//            String page = req.getParameter("page");
//            Integer newsId = Integer.valueOf(req.getParameter("newsId"));
//            newService.deleteNew(newsId);
//            if (req.getRequestURI().contains("admin")) {
//                resp.sendRedirect(req.getContextPath() + "/admin/news?page=" + page);
//            } else if (req.getRequestURI().contains("superAdmin")) {
//                resp.sendRedirect(req.getContextPath() + "/superAdmin/news?page=" + page);
//            }
//        }
//    }
//}
