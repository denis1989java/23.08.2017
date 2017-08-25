package ru.mail.denis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.mail.denis.service.DTOmodels.UserDTO;
import ru.mail.denis.service.DTOmodels.UserInformationDTO;
import ru.mail.denis.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 22.08.2017.
 */
@Controller
public class ProfileController {
    private UserService userService;
    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping(value = "*/profile",method = RequestMethod.GET)
    public ModelAndView showUserinfo (Principal principal){
        UserDTO userDTO=userService.getUserDTOByEmail(principal.getName());
        UserInformationDTO userInformationDTO=userService.getUserinformationDTOByUserId(userDTO.getUserId());
        Map<String,Object> map=new HashMap<>();
        map.put("userDTO",userDTO);
        map.put("userInformationDTO",userInformationDTO);
        ModelAndView modelAndView=new ModelAndView("profile");
        modelAndView.addAllObjects(map);
        return modelAndView;
    }
    @RequestMapping(value = "*/changeInfo",method = RequestMethod.GET)
    public String changeInfo (Principal principal, Model model){
        UserInformationDTO userInformationDTO=userService.getUserinformationDTOByUserId((userService.getUserDTOByEmail(principal.getName()).getUserId()));
        model.addAttribute("userInformationDTO",userInformationDTO);
        return "changeProfileInfo";
    }
    @RequestMapping(value = "*/changePassword", method = RequestMethod.GET)
    public String changePassword (@ModelAttribute("error") String error,
                                  Model model){
        model.addAttribute("error",error);
        return "changePassword";
    }

    @RequestMapping(value = "*/changeInfo",method = RequestMethod.POST)
    public String updateInfo(Principal principal,@ModelAttribute ("userInformationDTO") UserInformationDTO userInformationDTO){
        UserDTO userDTO=userService.getUserDTOByEmail(principal.getName());
        userService.updateUserInformationDTO(userInformationDTO,userDTO.getUserId());
        return "redirect:profile";
    }

    @RequestMapping(value = "*/changePassword",method = RequestMethod.POST)
    public String updatepassword(Principal principal, @ModelAttribute ("newPassword") String newPassword,
                                 @ModelAttribute("userPassword") String userPassword,
                                 @ModelAttribute("repeatPassword")String repeatPassword,
                                 RedirectAttributes ra){
        UserDTO userDTO=userService.getUserDTOByEmail(principal.getName());
        if (BCrypt.checkpw(userPassword,userDTO.getUserPassword())){
            if (newPassword.equals(repeatPassword)){
                userDTO.setUserPassword(newPassword);
                userService.updateUserDTO(userDTO);
                return "redirect:profile";
            }else{
                ra.addFlashAttribute("error","repeate password is wrong");
                return "redirect:changePassword";
            }
        }else{
            ra.addFlashAttribute("error","password is wrong");
            return "redirect:changePassword";
        }
    }


}
