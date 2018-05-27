package com.chaycao.webmvc.view;

import com.chaycao.webmvc.context.PropertiesContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Title:ViewResolver
 * 视图解析器，逻辑视图变为物理实现
 * 确定渲染模型的JSP文件的物理位置
 *
 * @author chaycao
 * @description:
 * @date 2018-04-26 16:59.
 */
@Component
@Scope("prototype")
public class JspViewResolver implements ViewResolver {

    public JspViewResolver() {}

    public View resovleModelAndView(ModelAndView mv) {
        return resolveViewName(mv.getViewName());
    }

    private View resolveViewName(String viewName) {
        if (null == viewName)
            return new JspView();
        String path = PropertiesContext.getProperty("viewPrefix", "/") + viewName + PropertiesContext.getProperty("viewSuffix", "");
        return new JspView(path);
    }

}
