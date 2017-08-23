package ru.mail.denis.web;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mail.denis.models.Role;
import ru.mail.denis.service.DTOmodels.AppUserPrincipal;


/**
 * Created by user on 17.08.2017.
 */

@Controller
public class DefaultSuccessController {

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public String filterDirectedUrl() {
        AppUserPrincipal principal = (AppUserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (principal.getRole() == Role.USER) {
            return "redirect:/user/cabinet";
        } else if (principal.getRole() == Role.ADMIN) {
            return "redirect:/admin/cabinet";
        } else if(principal.getRole()==Role.SUPER_ADMIN){
            return "redirect:/superAdmin/cabinet";
        }else{
            return "redirect:/login";
        }
    }
}
