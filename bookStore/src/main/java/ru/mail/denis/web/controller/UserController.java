package ru.mail.denis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.denis.service.modelDTO.ViewDTO;
import ru.mail.denis.service.UserService;

/**
 * Created by user on 23.08.2017.
 */
@Controller
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping(value = "/superAdmin/users/{page}", method = RequestMethod.GET)
    public ModelAndView showUsers(@PathVariable int page){
        ViewDTO viewDTO=userService.viewPageAllUsers(page);
        ModelAndView modelAndView=new ModelAndView("superAdmin/users");
        modelAndView.addObject(viewDTO);
        return modelAndView;


    }
    @RequestMapping(value = "/superAdmin/deleteUser",method = RequestMethod.GET)
    public String deleteUser(@RequestParam (value = "deleting",required = false) String [] deletings){
        if (deletings!=null) {
                userService.deleteUserDTO(deletings);
        }
        return "redirect:/superAdmin/users/0";
    }


    @RequestMapping(value = "/superAdmin/changeStatus",method = RequestMethod.GET)
    public String changeUserStatus(@RequestParam ("userStatus") String userStatus,@RequestParam ("userId") String userId){
        userService.changeUserDTOStatus(userStatus, Integer.valueOf(userId));
        return "redirect:/superAdmin/users/0";
    }


    @RequestMapping(value = "/superAdmin/changeRole",method = RequestMethod.GET)
    public String changeUserRole(@RequestParam ("userRole") String userRole,@RequestParam ("userId") String userId){
        userService.changeUserDTORole(userRole, Integer.valueOf(userId));
        return "redirect:/superAdmin/users/0";
    }
}
