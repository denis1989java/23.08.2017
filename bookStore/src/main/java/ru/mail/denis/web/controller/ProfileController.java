package ru.mail.denis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.mail.denis.service.model.UserDTO;
import ru.mail.denis.service.model.UserInformationDTO;
import ru.mail.denis.service.model.ViewDTO;
import ru.mail.denis.service.UserService;

import java.security.Principal;

/**
 * Created by Denis Monich on 22.08.2017.
 */
@Controller
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "*/profile", method = RequestMethod.GET)
    public ModelAndView showUserinfo(Principal principal) {
        ViewDTO viewDTO = userService.viewPageProfile(principal.getName());
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject(viewDTO);
        return modelAndView;
    }

    @RequestMapping(value = "*/changeInfo", method = RequestMethod.GET)
    public String changeInfo( Model model) {
        UserInformationDTO userInformationDTO = userService.getUserinformationDTOByUserId();
        model.addAttribute("userInformationDTO", userInformationDTO);
        return "changeProfileInfo";
    }

    @RequestMapping(value = "*/changePassword", method = RequestMethod.GET)
    public String changePassword(@ModelAttribute("error") String error,
                                 Model model) {
        model.addAttribute("error", error);
        return "changePassword";
    }

    @RequestMapping(value = "*/changeInfo", method = RequestMethod.POST)
    public String updateInfo( @ModelAttribute("userInformationDTO") UserInformationDTO userInformationDTO) {
        userService.updateUserInformationDTO(userInformationDTO);
        return "redirect:profile";
    }

    @RequestMapping(value = "*/changePassword", method = RequestMethod.POST)
    public String updatepassword(Principal principal, @ModelAttribute("newPassword") String newPassword,
                                 @ModelAttribute("userPassword") String userPassword,
                                 @ModelAttribute("repeatPassword") String repeatPassword,
                                 RedirectAttributes ra) {
        if (userService.checkPassword(userPassword)) {
            if (newPassword.equals(repeatPassword)) {
                userService.updateUserDTO(newPassword);
                return "redirect:profile";
            } else {
                ra.addFlashAttribute("error", "repeate password is wrong");
                return "redirect:changePassword";
            }
        } else {
            ra.addFlashAttribute("error", "password is wrong");
            return "redirect:changePassword";
        }
    }


}
