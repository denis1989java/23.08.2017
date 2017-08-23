package ru.mail.denis.web.servlets.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;

/**
 * Created by user on 07.08.2017.
 */

@Controller
public class CabinetController {

   private final ServletContext servletContext;

    @Autowired
    public CabinetController(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @RequestMapping(value = {"/user/cabinet","/admin/cabinet","/superAdmin/cabinet"},method = RequestMethod.GET)
    public String showCabinet (){
        return "cabinet";
    }
}
