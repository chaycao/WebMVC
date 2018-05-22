package com.chaycao.webmvc.view;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author chaycao
 * @description
 * @date 2018-05-22 17:09.
 */
public interface View {
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)  throws Exception;
}
