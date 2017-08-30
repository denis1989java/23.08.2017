package ru.mail.denis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.mail.denis.repositories.model.Changable;
import ru.mail.denis.service.CatalogueService;
import ru.mail.denis.service.model.BookDTO;
import ru.mail.denis.web.controller.validator.BookValidator;

/**
 * Created by Denis Monich on 25.08.2017.
 */

@Controller
@RequestMapping(value = "/superAdmin")
public class CatalogControllerSuperAdmin {

    private final CatalogueService catalogueService;
    private final BookValidator bookValidator;

    @Autowired
    public CatalogControllerSuperAdmin(CatalogueService catalogueService, BookValidator bookValidator) {
        this.catalogueService = catalogueService;
        this.bookValidator = bookValidator;
    }

    @RequestMapping(value = "/addBook", method = RequestMethod.GET)
    public String addBook(Model model) {
        model.addAttribute("book", new BookDTO());
        return "superAdmin/addBook";
    }

    @RequestMapping(value = "/saveBook", method = RequestMethod.POST)
    public String saveBookAdmin(@ModelAttribute("book") BookDTO bookDTO, BindingResult bindingResult) {
        bookValidator.validate(bookDTO, bindingResult);
        if (!bindingResult.hasErrors()) {
            catalogueService.saveBook(bookDTO);
            return "redirect:/superAdmin/catalogue/0";
        } else {
            return "superAdmin/addBook";
        }
    }

    @RequestMapping(value = "/changeBook/{bookId}", method = RequestMethod.GET)
    public String changeBookSuoerAdmin(@PathVariable int bookId, Model model) {
        BookDTO bookDTO = catalogueService.getBookById(bookId);
        if (bookDTO.getChangable() != Changable.CHANGABLE) {
            return "redirect:/superAdmin/catalogue/0";
        }
        model.addAttribute("book", bookDTO);
        return "superAdmin/changeBook";
    }

    @RequestMapping(value = "/superAdmin/updateBook", method = RequestMethod.POST)
    public String updateBookAdmin(@ModelAttribute("book") BookDTO bookDTO, BindingResult bindingResult) {
        bookDTO.setBookId(bookDTO.getBookId());
        bookValidator.validate(bookDTO, bindingResult);
        if (!bindingResult.hasErrors()) {
            catalogueService.updateBook(bookDTO);
            return "redirect:/superAdmin/catalogue/0";
        } else {
            return "superAdmin/changeBook";
        }
    }

    @RequestMapping(value = "/copyBook/{bookId}", method = RequestMethod.GET)
    public String copyBook(@PathVariable int bookId, Model model) {
        BookDTO bookDTO = catalogueService.getBookById(bookId);
        model.addAttribute("book", bookDTO);
        return "superAdmin/copyBook";
    }

    @RequestMapping(value = "/deleteBook/{bookId}", method = RequestMethod.GET)
    public String adminDeleteBook(@PathVariable int bookId) {
        BookDTO bookDTO = catalogueService.getBookById(bookId);
        if (bookDTO.getChangable() != Changable.CHANGABLE) {
            return "redirect:/superAdmin/catalogue/0";
        }
        catalogueService.deleteBook(bookId);
        return "redirect:/superAdmin/catalogue/0";
    }

}
