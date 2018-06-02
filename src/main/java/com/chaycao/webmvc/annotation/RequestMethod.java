package com.chaycao.webmvc.annotation;

/**
 * @author chaycao
 * @description
 * @date 2018-06-02 15:25.
 */
public enum RequestMethod {
    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE;

    private RequestMethod() {
    }
}
