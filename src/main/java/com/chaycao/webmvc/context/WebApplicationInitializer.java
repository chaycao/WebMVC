package com.chaycao.webmvc.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author chaycao
 * @description
 * @date 2018-05-27 08:34.
 */
public interface WebApplicationInitializer {
    void onStartup(ServletContext servletContext) throws ServletException;
}
