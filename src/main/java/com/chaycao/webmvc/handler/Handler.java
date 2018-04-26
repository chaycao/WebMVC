package com.chaycao.webmvc.handler;

import com.chaycao.webmvc.annotation.RequestParam;
import com.chaycao.webmvc.route.Route;
import com.chaycao.webmvc.util.TypeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Title:
 *
 * @author chaycao
 * @description:
 * @date 2018-04-25 16:37.
 */
public class Handler {
    private Handler() {}
    private static class HandlerHolder {
        private static Handler instance = new Handler();
    }
    private static Handler getInstance() {
        return HandlerHolder.instance;
    }
    public static void handler(HttpServletRequest request, HttpServletResponse response, Route route) throws InvocationTargetException, IllegalAccessException {
        Method method = route.getAction();
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        int i = 0;
        for (Parameter p : parameters) {
            Class<?> t = p.getType();
            if (t == HttpServletRequest.class) {
                args[i++] = request;
            } else if (t == HttpServletResponse.class) {
                args[i++] = response;
            } else if (p.getAnnotation(RequestParam.class) != null) {
                args[i++] = TypeUtil.cast(request.getParameter(p.getName()), p.getType());
            }
        }
        Object object = route.getObject();
        method.invoke(object, args);
    }
}
