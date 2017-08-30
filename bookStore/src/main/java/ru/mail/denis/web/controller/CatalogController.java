package ru.mail.denis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.denis.service.CatalogueService;
import ru.mail.denis.service.model.ViewDTO;

/**
 * Created by Denis Monich on 29.08.2017.
 */

@Controller
public class CatalogController {
    private final CatalogueService catalogueService;

    @Autowired
    public CatalogController(CatalogueService catalogueService) {
        this.catalogueService = catalogueService;
    }


    @RequestMapping(value = "*/catalogue", method = RequestMethod.GET)
    public ModelAndView showCatalogue( @RequestParam(value = "page", required = false) String page) {
        ViewDTO viewDTO = catalogueService.viewPage(Integer.valueOf(page), "");
        ModelAndView modelAndView = new ModelAndView("catalogue");
        modelAndView.addObject(viewDTO);
        return modelAndView;
    }
}
