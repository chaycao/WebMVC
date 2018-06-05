package com.chaycao.webmvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标志参数，表示为JSON格式参数
 * 标志方法，表示该方法返回JSON格式
 * @author chaycao
 * @description
 * @date 2018-06-05 16:28.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface RequestBody {
}
