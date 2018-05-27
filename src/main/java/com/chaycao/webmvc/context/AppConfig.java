package com.chaycao.webmvc.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author chaycao
 * @description
 * @date 2018-05-21 16:30.
 */
// TODO 可以把AppConfig的配置再解耦，由另外一个配置类来加载，设置ApplicationContext
@Configuration
@ComponentScan(basePackages = "com.chaycao")
public class AppConfig {
}
