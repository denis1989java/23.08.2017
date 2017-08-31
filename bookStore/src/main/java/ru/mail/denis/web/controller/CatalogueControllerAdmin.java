package ru.mail.denis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.denis.repositories.model.Changable;
import ru.mail.denis.service.CatalogueService;
import ru.mail.denis.service.model.BookDTO;
import ru.mail.denis.service.model.ViewDTO;
import ru.mail.denis.web.controller.validator.BookValidator;

/**
 * Created by Denis Monich on 22.08.2017.
 */
@Controller
@RequestMapping(value = "/admin")
public class CatalogueControllerAdmin {

    private final CatalogueService catalogueService;
    private final BookValidator bookValidator;

    @Autowired
    public CatalogueControllerAdmin(CatalogueService catalogueService, BookValidator bookValidator) {
        this.catalogueService = catalogueService;
        this.bookValidator = bookValidator;
    }

    @RequestMapping(value = "/addBook", method = RequestMethod.GET)
    public String addBook(Model model) {
        model.addAttribute("book", new BookDTO());
        return "admin/addBook";
    }

    @RequestMapping(value = "/saveBook", method = RequestMethod.POST)
    public String saveBookAdmin(@ModelAttribute("book") BookDTO bookDTO, BindingResult bindingResult) {
        bookValidator.validate(bookDTO, bindingResult);
        if (!bindingResult.hasErrors()) {
            catalogueService.saveBook(bookDTO);
            return "redirect:/admin/catalogue?page=0";
        } else {
            return "admin/addBook";
        }
    }

    @RequestMapping(value = "/changeBook/{bookId}", method = RequestMethod.GET)
    public String changeBookAdmin(@PathVariable Integer bookId, Model model) {
        BookDTO bookDTO = catalogueService.getBookById(bookId);
        if (bookDTO.getChangable() != Changable.CHANGABLE) {
            return "redirect:/admin/catalogue?page=0";
        }
        model.addAttribute("book", bookDTO);
        return "admin/changeBook";
    }

    @RequestMapping(value = "/updateBook", method = RequestMethod.POST)
    public String updateBookAdmin(@ModelAttribute("book") BookDTO bookDTO, BindingResult bindingResult) {
        bookDTO.setBookId(bookDTO.getBookId());
        bookValidator.validate(bookDTO, bindingResult);
        if (!bindingResult.hasErrors()) {
            catalogueService.updateBook(bookDTO);
            return "redirect:/admin/catalogue?page=0";
        } else {
            return "admin/changeBook";
        }
    }

    @RequestMapping(value = "/copyBook/{bookId}", method = RequestMethod.GET)
    public String copyBook(@PathVariable Integer bookId, Model model) {
        BookDTO bookDTO = catalogueService.getBookById(bookId);
        model.addAttribute("book", bookDTO);
        return "admin/copyBook";
    }

    @RequestMapping(value = "/deleteBook/{bookId}", method = RequestMethod.GET)
    public String adminDeleteBook(@PathVariable Integer bookId) {
        BookDTO bookDTO = catalogueService.getBookById(bookId);
        if (bookDTO.getChangable() != Changable.CHANGABLE) {
            return "redirect:/admin/catalogue?page=0";
        }
        catalogueService.deleteBook(bookId);
        return "redirect:/admin/catalogue?page=0";
    }


}
