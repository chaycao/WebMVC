package com.chaycao.webmvc.handler;

import com.alibaba.fastjson.JSON;
import com.chaycao.webmvc.annotation.RequestBody;
import com.chaycao.webmvc.route.Route;
import com.chaycao.webmvc.view.JsonView;
import com.chaycao.webmvc.view.ModelAndView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author chaycao
 * @description
 * @date 2018-04-27 16:43.
 */
@Component
@Scope("prototype")
public class HandlerMethodReturnValueResolver {

    // TODO 若route方法被@RequestBody标识，则转为json
    public ModelAndView resolveReturnValue(Object res, Route route) {
        ModelAndView mv = new ModelAndView();
        if (route.getAction().getAnnotation(RequestBody.class) != null) {
            mv = new ModelAndView(new JsonView(JSON.toJSONString(res)));
        } else if (res instanceof String) {
            mv = new ModelAndView((String)res);
        } else if (res instanceof ModelAndView) {
            mv = (ModelAndView)res;
        }
        return mv;
    }

}
