package com.chaycao.webmvc.handler;

import com.chaycao.webmvc.annotation.RequestParam;
import com.chaycao.webmvc.route.Route;
import com.chaycao.webmvc.util.TypeUtil;
import com.chaycao.webmvc.view.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
@Component
@Scope("prototype")
public class HttpHandler implements Handler {

    @Autowired
    private HandlerMethodArgumentResolver argumentResolver;

    @Autowired
    private HandlerMethodReturnValueResolver returnValueResolver;

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Route route) throws Exception {

        Object[] args = argumentResolver.resolveArgument(request, response, route);
        Object result = invoke(route, args);
        ModelAndView mv = returnValueResolver.resolveReturnValue(result, route);
        return mv;
    }

    public Object invoke(Route route, Object[] args) throws InvocationTargetException, IllegalAccessException {
        Method method = route.getAction();
        Object obj = route.getObject();
        return method.invoke(obj, args);
    }


}
