package ru.mail.denis.web.servlets.home;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * Created by моня on 26.05.2017.
 */

@Controller
public class HomeController {

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String showWelcomePage(@ModelAttribute("successRegistration") String successRegistration,
                                  Model model) {
        model.addAttribute("successRegistration",successRegistration);
        return "login";
    }

    }

