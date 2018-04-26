package com.chaycao.route;

import com.chaycao.webmvc.annotation.RequestMapping;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Title:
 *
 * @author chaycao
 * @description:
 * @date 2018-04-23 19:45.
 */
public class RouterMatcher {
    @Test
    public void findRoute(){
        String uri = "/WebMVC/abc";
        String contextPath = "/WebMVC";
        uri = uri.substring(contextPath.length(), uri.length());
        System.out.println(uri);
    }

    @Test
    public void annotation() {
        try {
            Class<?> c = Class.forName("com.chaycao.controller.TestController");
            for (Method m : c.getDeclaredMethods()) {
                RequestMapping r = m.getAnnotation(RequestMapping.class);
                if (r!=null)
                    System.out.println(r.value());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
