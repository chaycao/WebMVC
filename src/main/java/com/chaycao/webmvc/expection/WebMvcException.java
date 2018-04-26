package com.chaycao.webmvc.expection;

/**
 * Title:
 *
 * @author chaycao
 * @description:
 * @date 2018-04-26 15:43.
 */
public class WebMvcException extends RuntimeException {

    public WebMvcException() {
        super();
    }

    public WebMvcException(String message) {
        super(message);
    }
}
