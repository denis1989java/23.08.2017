package ru.mail.denis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.denis.service.modelDTO.ViewDTO;
import ru.mail.denis.service.NewService;
import ru.mail.denis.service.modelDTO.NewsDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
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
        ViewDTO viewDTO=newService.viewPage(page);
        ModelAndView modelAndView=new ModelAndView("news");
        modelAndView.addObject(viewDTO);
        return modelAndView;
    }
    @RequestMapping(value = "*/new/{newsId}" ,method = RequestMethod.GET)
    public ModelAndView showNew(@PathVariable int newsId){
        NewsDTO news = newService.getNewById(newsId);
        ModelAndView modelAndView=new ModelAndView("new");
        modelAndView.addObject("news",news);
        return modelAndView;
    }
    @RequestMapping(value ="/admin/changeNew/{newsId}" , method = RequestMethod.GET)
    public String changeNewAdmin(@PathVariable int newsId, Model model){
        NewsDTO news = newService.getNewById(newsId);
        model.addAttribute("news",news);
        return "admin/changeNew";
    }
    @RequestMapping(value ="/superAdmin/changeNew/{newsId}" , method = RequestMethod.GET)
    public String changeNewSuperAdmin(@PathVariable int newsId, Model model){
        NewsDTO news = newService.getNewById(newsId);
        model.addAttribute("news",news);
        return "superAdmin/changeNew";
    }

    @RequestMapping(value ={"/admin/saveNew","/superAdmin/saveNew"}, method = RequestMethod.POST)
    public String updateNew (@ModelAttribute ("news") NewsDTO newsDTO){
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

    @RequestMapping (value ="/admin/addNews", method = RequestMethod.GET)
    public String addNewsAdmin (Model model){
        model.addAttribute("news", new NewsDTO());
        return "admin/addNew";
    }
    @RequestMapping (value ="/superAdmin/addNews" , method = RequestMethod.GET)
    public String addNewsSuperAdmin (Model model){
        model.addAttribute("news", new NewsDTO());
        return "superAdmin/addNew";
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
            BufferedOutputStream stream=new BufferedOutputStream(new FileOutputStream(new File("D:/проект/25082017/ItBookStore/ItBookStore/src/main/webapp/resources/images/"+fileName)));
            stream.write(bytes);
            stream.flush();
            stream.close();
        }
        newsDTO.setNewsFoto(fileName);
        newService.addNew(newsDTO);
        return "redirect:news/0";
    }



}
