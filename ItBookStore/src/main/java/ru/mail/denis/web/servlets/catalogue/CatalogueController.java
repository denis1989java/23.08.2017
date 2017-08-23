package ru.mail.denis.web.servlets.catalogue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.denis.models.Changable;
import ru.mail.denis.service.CatalogueService;
import ru.mail.denis.service.DTOmodels.BookDTO;
import ru.mail.denis.service.DTOmodels.NewsDTO;
import ru.mail.denis.service.DTOmodels.OrdersBooksDTO;
import ru.mail.denis.service.NewService;
import ru.mail.denis.service.Orderservice;
import ru.mail.denis.web.BookValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 22.08.2017.
 */
@Controller
public class CatalogueController {

    private CatalogueService catalogueService;
    private Orderservice orderservice;
    private BookValidator bookValidator;

    @Autowired
    public CatalogueController(CatalogueService catalogueService, Orderservice orderservice, BookValidator bookValidator) {
        this.catalogueService = catalogueService;
        this.orderservice = orderservice;
        this.bookValidator = bookValidator;
    }

    @RequestMapping(value = "*/catalogue/{page}", method = RequestMethod.GET)
    public ModelAndView showCatalogue(@PathVariable int page){
        int total=7;
        if (page != 0) {
            page = page * total;
        }
        List<BookDTO> books = catalogueService.getBooks(page, total);
        for (int i = 0; i < books.size(); i++) {
            Integer bookID = books.get(i).getBookId();
            List<OrdersBooksDTO> list = orderservice.getOrderBooksDTOByBookId(bookID);
            BookDTO bookDTO = books.get(i);
            if (!list.isEmpty()) {
                bookDTO.setChangable(Changable.NOT_CHANGABLE);
            } else {
                bookDTO.setChangable(Changable.CHANGABLE);
            }
            catalogueService.updateBook(bookDTO);
        }
        List<BookDTO> bookDTOS=catalogueService.getBooks(page,total);
        Integer booksQuantity = catalogueService.booksQuantity();
        List<Integer> pagination = new ArrayList();
        Integer pageQuantity = 0;
        if (booksQuantity % total == 0) {
            pageQuantity = booksQuantity / total;
        } else {
            pageQuantity = booksQuantity / total + 1;
        }
        for (Integer i = 0; i < pageQuantity; i++) {
            pagination.add(i);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("catalogue",bookDTOS);
        map.put("pagination",pagination);
        map.put("CHANGABLE","CHANGABLE");
        ModelAndView modelAndView=new ModelAndView("catalogue");
        modelAndView.addAllObjects(map);
        return modelAndView;
    }
    @RequestMapping (value ={"/admin/addBook","/superAdmin/addBook"} , method = RequestMethod.GET)
    public String addBook (Model model){
        model.addAttribute("book", new BookDTO());
        return "addBook";
    }

    @RequestMapping (value ={"/admin/saveBook","/superAdmin/saveBook"} , method = RequestMethod.POST)
    public String saveBook(@ModelAttribute("book") BookDTO bookDTO, BindingResult bindingResult){
        bookValidator.validate(bookDTO,bindingResult);
        if (!bindingResult.hasErrors()){
            catalogueService.saveBook(bookDTO);
            return "redirect:catalogue/0";
        }else{
            return "addBook";
        }

    }
    @RequestMapping(value ={"/admin/changeBook/{bookId}","/superAdmin/changeBook/{bookId}"} , method = RequestMethod.GET)
    public String changeBook(@PathVariable int bookId, Model model){
        BookDTO bookDTO = catalogueService.getBookById(bookId);
        model.addAttribute("book",bookDTO);
        return "changeBook";
    }

    @RequestMapping (value ={"/admin/updateBook","/superAdmin/updateBook"} , method = RequestMethod.POST)
    public String updateBook(@ModelAttribute("book") BookDTO bookDTO, BindingResult bindingResult){
        bookDTO.setBookId(bookDTO.getBookId());
        bookValidator.validate(bookDTO,bindingResult);
        if (!bindingResult.hasErrors()){
            catalogueService.updateBook(bookDTO);
            return "redirect:catalogue/0";
        }else{
            return "changeBook";
        }

    }

    @RequestMapping(value ={"/admin/copyBook/{bookId}","/superAdmin/copyBook/{bookId}"}, method = RequestMethod.GET)
    public String copyBook(@PathVariable int bookId, Model model){
        BookDTO bookDTO = catalogueService.getBookById(bookId);
        model.addAttribute("book",bookDTO);
        return "copyBook";
    }

    @RequestMapping(value ="/admin/deleteBook/{bookId}",method = RequestMethod.GET)
    public String adminDeleteBook(@PathVariable int bookId){
        catalogueService.deleteBook(bookId);
        return "redirect:/admin/catalogue/0";
    }

    @RequestMapping(value ="/superAdmin/deleteBook/{bookId}",method = RequestMethod.GET)
    public String superAdminDeleteBook(@PathVariable int bookId){
        catalogueService.deleteBook(bookId);
        return "redirect:/superAdmin/catalogue/0";
    }



}
