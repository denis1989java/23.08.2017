package ru.mail.denis.web.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by user on 08.06.2017.
 */
public class RequestEncodingFilter implements Filter {
    private static final Logger logger = Logger.getLogger(RequestEncodingFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("RequestEncodingFilter start working");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        logger.debug("Setting character encoding UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
        servletResponse.setContentType("text/html; charset=UTF-8");
    }

    @Override
    public void destroy() {

    }
}
