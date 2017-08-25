package ru.mail.denis.web.controller.exceptions;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by user on 08.06.2017.
 */




@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = Logger.getLogger(ExceptionController.class);

    @ExceptionHandler(value = Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception e){
        logger.error("Reques"+req.getRequestURL()+" raised "+e);

        ModelAndView modelAndView=new ModelAndView("error");
        modelAndView.addObject("exception",e);
        modelAndView.addObject("url",req.getRequestURL());
        return modelAndView;
    }
}
