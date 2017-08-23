package ru.mail.denis.web.servlets.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.denis.models.Role;
import ru.mail.denis.models.Status;
import ru.mail.denis.service.DTOmodels.UserDTO;
import ru.mail.denis.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int total=7;
        if (page != 0) {
            page = page * total;
        }
        List<UserDTO> userDTOS = userService.getUsersDTO(page, total);
        Integer usersDTOQuantity = userService.usersDTOQuantity();
        List<Integer> pagination = new ArrayList();
        Integer pageQuantity = 0;
        if (usersDTOQuantity % total == 0) {
            pageQuantity = usersDTOQuantity / total;
        } else {
            pageQuantity = usersDTOQuantity / total + 1;
        }
        for (Integer i = 0; i < pageQuantity; i++) {
            pagination.add(i);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("users",userDTOS);
        map.put("pagination",pagination);
        ModelAndView modelAndView=new ModelAndView("users");
        modelAndView.addAllObjects(map);
        return modelAndView;


    }
    @RequestMapping(value = "/superAdmin/deleteUser",method = RequestMethod.GET)
    public String deleteUser(@RequestParam (value = "deleting",required = false) String [] deletings){
        if (deletings!=null) {
            for (int i = 0; i < deletings.length; i++) {
                Integer userId = Integer.valueOf(deletings[i]);
                userService.deleteUserDTO(userId);
            }
        }
        return "redirect:/superAdmin/users/0";
    }
    @RequestMapping(value = "/superAdmin/changeStatus",method = RequestMethod.GET)
    public String changeUserStatus(@RequestParam ("userStatus") String userStatus,@RequestParam ("userId") String userId){
        Status status = null;
        if (userStatus.equals("ACTIVE")) {
            status = Status.ACTIVE;
        } else if (userStatus.equals("BLOCKED")) {
            status = Status.BLOCKED;
        }
        userService.changeUserDTOStatus(status, Integer.valueOf(userId));
        return "redirect:/superAdmin/users/0";
    }


    @RequestMapping(value = "/superAdmin/changeRole",method = RequestMethod.GET)
    public String changeUserRole(@RequestParam ("userRole") String userRole,@RequestParam ("userId") String userId){
        Role role = null;
        if (userRole.equals("USER")) {
            role = Role.USER;
        } else if (userRole.equals("ADMIN")) {
            role = Role.ADMIN;
        } else if (userRole.equals("SUPER_ADMIN")) {
            role = Role.SUPER_ADMIN;
        }
        userService.changeUserDTORole(role, Integer.valueOf(userId));
        return "redirect:/superAdmin/users/0";
    }
}
