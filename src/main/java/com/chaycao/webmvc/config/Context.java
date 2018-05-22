package com.chaycao.webmvc.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;

/**
 * Title:
 *
 * @author chaycao
 * @description:
 * @date 2018-04-26 17:36.
 */
public class Context {
    public static final ApplicationContext APPLICATION_CONTEXT = new AnnotationConfigApplicationContext(AppConfig.class);
}
