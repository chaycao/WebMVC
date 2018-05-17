package com.chaycao.webmvc.view;

import com.chaycao.webmvc.config.PropertiesConfig;

/**
 * Title:ViewResolver
 * 视图解析器，逻辑视图变为物理实现
 * 确定渲染模型的JSP文件的物理位置
 *
 * @author chaycao
 * @description:
 * @date 2018-04-26 16:59.
 */
public class ViewResolver {

    public ViewResolver() {}

    public static ViewResolver newInstance() {
        return new ViewResolver();
    }

    public View resovleModelAndView(ModelAndView mv) {
        return resolveViewName(mv.getViewName());
    }

    private View resolveViewName(String viewName) {
        if (null == viewName)
            return new View();
        String path = PropertiesConfig.getProperty("viewPrefix", "/") + viewName + PropertiesConfig.getProperty("viewSuffix", "");
        return new View(path);
    }

}
