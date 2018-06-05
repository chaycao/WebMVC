package com.chaycao.webmvc.view;

import com.chaycao.webmvc.expection.WebMvcException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author chaycao
 * @description
 * @date 2018-06-05 19:40.
 */
public class RedirectView implements View {
    private String path;

    public RedirectView(String path) {
        this.path = path;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (path != null) {
            response.sendRedirect(path);
        } else {
            throw new WebMvcException("RedirectView can not render, because path is null");
        }
    }
}
