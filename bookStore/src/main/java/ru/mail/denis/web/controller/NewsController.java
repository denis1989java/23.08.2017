package ru.mail.denis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.denis.service.model.ViewDTO;
import ru.mail.denis.service.NewService;
import ru.mail.denis.service.model.NewsDTO;

import javax.servlet.ServletException;
import java.io.*;

/**
 * Created by Denis Monich on 23.06.2017.
 */
@Controller
public class NewsController {


    private final NewService newService;

    @Autowired
    public NewsController(NewService newService) {
        this.newService = newService;
    }


    @RequestMapping(value = "*/news", method = RequestMethod.GET)
    public ModelAndView showNews(@RequestParam(value = "page", required = false) String page) {
        ViewDTO viewDTO = newService.viewPage(Integer.valueOf(page));
        ModelAndView modelAndView = new ModelAndView("news");
        modelAndView.addObject(viewDTO);
        return modelAndView;
    }

    @RequestMapping(value = "*/new/{newsId}", method = RequestMethod.GET)
    public ModelAndView showNew(@PathVariable int newsId) {
        NewsDTO news = newService.getNewById(newsId);
        ModelAndView modelAndView = new ModelAndView("new");
        modelAndView.addObject("news", news);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/changeNew/{newsId}", method = RequestMethod.GET)
    public String changeNewAdmin(@PathVariable int newsId, Model model) {
        NewsDTO news = newService.getNewById(newsId);
        model.addAttribute("news", news);
        return "admin/changeNew";
    }

    @RequestMapping(value = "/superAdmin/changeNew/{newsId}", method = RequestMethod.GET)
    public String changeNewSuperAdmin(@PathVariable int newsId, Model model) {
        NewsDTO news = newService.getNewById(newsId);
        model.addAttribute("news", news);
        return "superAdmin/changeNew";
    }

    @RequestMapping(value = {"/admin/saveNew", "/superAdmin/saveNew"}, method = RequestMethod.POST)
    public String updateNew(@ModelAttribute("news") NewsDTO newsDTO) {
        newService.updateNew(newsDTO);
        return "redirect:news?page=0";
    }

    @RequestMapping(value = "/admin/deleteNew/{newsId}", method = RequestMethod.GET)
    public String adminDeleteNew(@PathVariable int newsId) {
        newService.deleteNew(newsId);
        return "redirect:/admin/news?page=0";
    }

    @RequestMapping(value = "/superAdmin/deleteNew/{newsId}", method = RequestMethod.GET)
    public String superAdminDeleteNew(@PathVariable int newsId) {
        newService.deleteNew(newsId);
        return "redirect:/superAdmin/news?page=0";
    }

    @RequestMapping(value = "/admin/addNews", method = RequestMethod.GET)
    public String addNewsAdmin(Model model) {
        model.addAttribute("news", new NewsDTO());
        return "admin/addNew";
    }

    @RequestMapping(value = "/superAdmin/addNews", method = RequestMethod.GET)
    public String addNewsSuperAdmin(Model model) {
        model.addAttribute("news", new NewsDTO());
        return "superAdmin/addNew";
    }


    @RequestMapping(value = {"/admin/saveNews", "/superAdmin/saveNews"}, method = RequestMethod.POST)
    public String saveNews(@ModelAttribute("news") NewsDTO newsDTO) throws IOException, ServletException {
        newService.addNew(newsDTO);
        return "redirect:news?page=0";
    }


}
