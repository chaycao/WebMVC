package com.chaycao.webmvc.handler;

import com.chaycao.webmvc.annotation.RequestParam;
import com.chaycao.webmvc.route.Route;
import com.chaycao.webmvc.util.TypeUtil;
import com.chaycao.webmvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author chaycao
 * @description:
 * @date 2018-04-25 16:37.
 */
public class HttpHandler implements Handler {

    private HandlerMethodArgumentResolver argumentResolver;
    private HandlerMethodReturnValueResolver returnValueResolver;

    public HttpHandler() {
        this.argumentResolver = new HandlerMethodArgumentResolver();
        this.returnValueResolver = new HandlerMethodReturnValueResolver();
    }

    public static HttpHandler newInstance() {
        return new HttpHandler();
    }

    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Route route) throws Exception {
        Object[] args = argumentResolver.resolveArgument(request, response, route);
        Object result = invoke(route, args);
        ModelAndView mv = returnValueResolver.resolveReturnValue(result);
        return mv;
    }

    public Object invoke(Route route, Object[] args) throws InvocationTargetException, IllegalAccessException {
        Method method = route.getAction();
        Object obj = route.getObject();
        return method.invoke(obj, args);
    }


}
