package com.chaycao.webmvc.view;

import com.chaycao.webmvc.config.Context;
import com.chaycao.webmvc.util.JspToHtmlUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Map;

/**
 * Title:View
 * 接收模型以及Servlet的request和response，
 * 并将输出结果渲染到response
 *
 * @author chaycao
 * @description:
 * @date 2018-04-26 17:00.
 */
public class View {

    private String path;

    public View() {
        path = null;
    }

    public View(String path) {
        this.path = path;
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws Exception {
        if (path == null)
            return;
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        return;
    }
}
