package com.chaycao.webmvc.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chaycao
 * @description
 * @date 2018-05-14 17:18.
 */
public class JspToHtmlUtil {
    /**
     * 根据JSP所在的路径获取JSP的内容<br/>
     * 在不跳转下访问目标jsp。就是利用RequestDispatcher.include(ServletRequest request, ServletResponse response)。
     * 该方法把RequestDispatcher指向的目标页面写到response中。
     * @param jspPath jsp路径
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     * @throws Exception
     */
    public static String getJspOutput(String jspPath, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        WrapperResponse wrapperResponse = new WrapperResponse(response);
        request.getRequestDispatcher(jspPath).include(request, wrapperResponse);
        return wrapperResponse.getContent();
    }
}
