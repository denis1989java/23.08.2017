package ru.mail.denis.web.servlets.exceptions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by user on 08.06.2017.
 */
public class ExceptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processError(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processError(req, resp);
    }

    private void processError(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.write("<html><head><title>Exception</title></head><body>");
        out.write("<strong>Exception</strong>" + "</br></br>");
        out.write("<strong>Sorry! We are working about this problem</strong>");
        out.write("<br><br>");
        out.write("<a href=\"home\">home</a>");
        out.write("</body></html>");
        out.close();
    }
}