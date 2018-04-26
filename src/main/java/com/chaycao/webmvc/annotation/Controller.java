package com.chaycao.webmvc.annotation;

/**
 * Title:
 *
 * @author chaycao
 * @description:
 * @date 2018-04-25 15:47.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {
}
