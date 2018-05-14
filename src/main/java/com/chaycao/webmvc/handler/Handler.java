package com.chaycao.webmvc.handler;

import com.chaycao.webmvc.route.Route;
import com.chaycao.webmvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

/**
 * @author chaycao
 * @description
 * @date 2018-04-27 10:43.
 */
public interface Handler {
    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Route route) throws Exception;
}
