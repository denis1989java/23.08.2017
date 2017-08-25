package ru.mail.denis.web.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mail.denis.repositories.model.Role;
import ru.mail.denis.repositories.model.Status;
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
            if(principal.getStatus()== Status.ACTIVE){
                return "redirect:/user/cabinet";
            }else {
                SecurityContextHolder.clearContext();
                return "redirect:/login?status=Your status is blocked";
            }
        } else if (principal.getRole() == Role.ADMIN) {
            if(principal.getStatus()== Status.ACTIVE){
                return "redirect:/admin/cabinet";
            }else {
                SecurityContextHolder.clearContext();
                return "redirect:/login?status=Your status is blocked";
            }
        } else if(principal.getRole()==Role.SUPER_ADMIN){
            if(principal.getStatus()== Status.ACTIVE){
                return "redirect:/superAdmin/cabinet";
            }else {

                SecurityContextHolder.clearContext();
                return "redirect:/login?status=Your status is blocked";
            }

        }else{
            return "redirect:/login";
        }
    }
}
