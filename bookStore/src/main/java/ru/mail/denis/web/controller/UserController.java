package ru.mail.denis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.denis.service.model.ViewDTO;
import ru.mail.denis.service.UserService;

/**
 * Created by Denis Monich on 23.08.2017.
 */
@Controller
@RequestMapping(value = "/superAdmin")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView showUsers(@RequestParam(value = "page", required = false) String page) {
        if (page=="" || page==null){
            page="0";
        }
        ViewDTO viewDTO = userService.viewPageAllUsers(Integer.valueOf(page));
        ModelAndView modelAndView = new ModelAndView("superAdmin/users");
        modelAndView.addObject(viewDTO);
        return modelAndView;


    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deleteUser(@RequestParam(value = "page", required = false) String page,@RequestParam(value = "deleting", required = false) String[] deletings) {
        if (page=="" ||page==null){
            page="0";
        }
        if (deletings != null) {
            userService.deleteUserDTO(deletings);
        }
        return "redirect:/superAdmin/users?page="+page;
    }


    @RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
    public String changeUserStatus(@RequestParam(value = "page", required = false) String page,@RequestParam("userStatus") String userStatus, @RequestParam("userId") String userId) {
        if (page=="" || page==null){
            page="0";
        }
        userService.changeUserDTOStatus(userStatus, Integer.valueOf(userId));
        return "redirect:/superAdmin/users?page="+page;
    }


    @RequestMapping(value = "/changeRole", method = RequestMethod.GET)
    public String changeUserRole(@RequestParam(value = "page", required = false) String page,@RequestParam("userRole") String userRole, @RequestParam("userId") String userId) {
        if (page=="" ||page==null){
            page="0";
        }
        userService.changeUserDTORole(userRole, Integer.valueOf(userId));
        return "redirect:/superAdmin/users?page="+page;
    }
}
