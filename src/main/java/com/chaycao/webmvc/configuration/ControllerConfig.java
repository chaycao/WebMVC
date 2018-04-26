package com.chaycao.webmvc.configuration;

import com.chaycao.webmvc.annotation.Controller;
import com.chaycao.webmvc.annotation.RequestMapping;
import com.chaycao.webmvc.route.Route;
import com.chaycao.webmvc.util.PackageUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Title:查找Controller
 *
 * @author chaycao
 * @description:
 * @date 2018-04-25 16:08.
 */
public class ControllerConfig {
    private List<Class<?>> controllers = new ArrayList<>();
    private ControllerConfig() {}
    private static class controllerConfigHolder {
        private static ControllerConfig instance = new ControllerConfig();
    }
    private static ControllerConfig getInstance() {
        return controllerConfigHolder.instance;
    }

    /**
     * 加载Controller
     */
    public static void init() {
        // 查找指定扫描包下的所有类
        String value = PropertiesConfig.getProperty("CompantScanBasePackages");
        String[] basePackages = value.split(",");
        List<String> classNames = new ArrayList<>();
        for (String packageName : basePackages) {
            classNames.addAll(PackageUtil.getClassName(packageName));
        }
        // 筛选具有Controller注解的类
        // 添加到controllers中
        for (String className : classNames) {
            try {
                Class<?> c = Class.forName(className);
                Controller con = c.getAnnotation(Controller.class);
                if (con != null) {
                    addController(c);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Class<?>> getControllers() {
        return getInstance().controllers;
    }

    public static void addController(Class<?> c) {
        getInstance().controllers.add(c);
    }
}
