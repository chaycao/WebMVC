package com.chaycao.webmvc.dispatcher;

import com.chaycao.webmvc.config.Context;
import com.chaycao.webmvc.util.FileUtil;

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
    public static void registeJsp() throws IOException {
        List<String> paths = new ArrayList<>();
        paths = FileUtil.scanAllJsp(Context.REAL_CONTEXT_PATH);
        ServletRegistration jspServlet = Context.SERVLETCONTEXT.getServletRegistration("jsp");
        for (String path : paths)
            jspServlet.addMapping(path);
    }
}
