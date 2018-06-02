package com.chaycao.webmvc.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chaycao
 * @description
 * @date 2018-06-02 17:06.
 */
public class HttpUtil {

    public static String getAccept(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        return accept == null ? "" : accept;
    }

    public static String getContentType(HttpServletRequest request) {
        String contentType = request.getHeader("Content-Type");
        return contentType == null ? "" : contentType;
    }
}
