package ru.mail.denis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.mail.denis.service.MessageService;
import ru.mail.denis.service.modelDTO.MessageDTO;
import ru.mail.denis.service.modelDTO.ViewDTO;
import ru.mail.denis.web.controller.validator.MessageValidator;

/**
 * Created by user on 28.08.2017.
 */
@Controller
public class ContactFormController {

    private final MessageValidator messageValidator;
    private MessageService messageService;

    @Autowired
    public ContactFormController(MessageValidator messageValidator, MessageService messageService) {
        this.messageValidator = messageValidator;
        this.messageService = messageService;
    }

    @RequestMapping(value = "/contact/form", method = RequestMethod.GET)
    public String showContactForm(Model model){
        model.addAttribute("message",new MessageDTO());
        return "contactForm";
    }

    @RequestMapping(value = "/contact/form",method = RequestMethod.POST)
    public String saveMessage(@ModelAttribute("message") MessageDTO messageDTO,
                              BindingResult result,
                              RedirectAttributes ra){
        messageValidator.validate(messageDTO,result);
        if (!result.hasErrors()) {
            messageService.saveMessage(messageDTO);
            ra.addFlashAttribute("successMessage","Message sent successfully");
            return "redirect:/login";
        } else {
            return "contactForm";
        }
    }
    @RequestMapping(value = "*/messages/{page}",method = RequestMethod.GET)
    public ModelAndView showMessages (@PathVariable int page,@RequestParam(value = "userEmail", required = false) String userEmail,
                                      @RequestParam(value = "messageId", required = false) String messageId){
        ViewDTO viewDTO=messageService.viewPage(page,userEmail,messageId);
        ModelAndView modelAndView=new ModelAndView("messages");
        modelAndView.addObject(viewDTO);
        return modelAndView;

    }
}
