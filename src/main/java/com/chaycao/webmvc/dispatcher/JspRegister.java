package com.chaycao.webmvc.dispatcher;

import com.chaycao.webmvc.util.FileUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chaycao
 * @description
 * @date 2018-05-16 20:41.
 */
public class JspRegister {
    public static void registeJspServlet(ServletContext servletContext) {
        List<String> paths = new ArrayList<>();
        try {
            paths = FileUtil.scanAllJsp(servletContext.getRealPath(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        for (String path : paths)
            jspServlet.addMapping(path);
    }
}
