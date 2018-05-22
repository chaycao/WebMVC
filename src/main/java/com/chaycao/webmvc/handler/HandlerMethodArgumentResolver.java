package com.chaycao.webmvc.handler;

import com.chaycao.webmvc.annotation.RequestParam;
import com.chaycao.webmvc.route.Route;
import com.chaycao.webmvc.util.TypeUtil;
import com.chaycao.webmvc.view.ModelAndView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author chaycao
 * @description
 * @date 2018-04-27 16:02.
 */
@Component
@Scope("prototype")
public class HandlerMethodArgumentResolver {

    public Object[] resolveArgument(HttpServletRequest request, HttpServletResponse response, Route route) {
        Method method = route.getAction();
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        int i = 0;
        for (Parameter p : parameters) {
            Class<?> type = p.getType();
            if (type == HttpServletRequest.class) {
                args[i++] = request;
            } else if (type == HttpServletResponse.class) {
                args[i++] = response;
            } else if (p.getAnnotation(RequestParam.class) != null) {
                args[i++] = TypeUtil.cast(request.getParameter(p.getName()), p.getType());
            } else if (type == ModelAndView.class) {
                args[i++] = new ModelAndView();
            }
        }
        return args;
    }
}
