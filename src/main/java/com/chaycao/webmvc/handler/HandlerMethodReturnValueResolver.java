package com.chaycao.webmvc.handler;

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

    public ModelAndView resolveReturnValue(Object res) {
        ModelAndView mv = new ModelAndView();
        if (res instanceof String) {
            mv = new ModelAndView((String)res);
        } else if (res instanceof ModelAndView) {
            mv = (ModelAndView)res;
        }
        return mv;
    }

}
