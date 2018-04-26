package com.chaycao.webmvc.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Title:
 *
 * @author chaycao
 * @description:
 * @date 2018-04-23 20:58.
 */
public class PathUtil {

    public static String getRelativePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String uri = request.getRequestURI();
        uri = uri.substring(contextPath.length(), uri.length()); //去掉项目名
        if (!uri.startsWith("/")) {
            uri = "/" + uri;
        }
        return uri;
    }
}
