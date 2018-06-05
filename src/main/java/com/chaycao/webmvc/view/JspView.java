package com.chaycao.webmvc.view;

import com.chaycao.webmvc.expection.WebMvcException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Title:JspView
 * 接收模型以及Servlet的request和response，
 * 并将输出结果渲染到response
 *
 * @author chaycao
 * @description:
 * @date 2018-04-26 17:00.
 */
public class JspView implements View {

    private String path;

    public JspView() {
        path = null;
    }

    public JspView(String path) {
        this.path = path;
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (path != null) {
            model.forEach((k, v) -> request.setAttribute(k, v));
            request.getRequestDispatcher(path).forward(request, response);
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
