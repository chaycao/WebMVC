package com.chaycao.webmvc.view;

/**
 * @author chaycao
 * @description
 * @date 2018-05-22 17:06.
 */
public interface ViewResolver {
    public View resovleModelAndView(ModelAndView mv);
}
