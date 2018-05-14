package com.chaycao.webmvc.config;

import com.chaycao.webmvc.annotation.Controller;
import com.chaycao.webmvc.expection.WebMvcException;
import com.chaycao.webmvc.util.PackageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chaycao
 * @description: Controller组件的配置
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
     * 查找指定扫描包下的所有类，筛选具有Controller注解的类
     */
    public static void scanAndLoadController() throws ClassNotFoundException {
        String value = PropertiesConfig.getProperty("CompantScanBasePackages");
        String[] basePackages = value.split(",");
        List<Class<?>> controllers = scanController(basePackages);
        addControllers(controllers);
    }

    public static List<Class<?>> scanController(String[] basePackages) throws ClassNotFoundException {
        List<String> classNames = new ArrayList<>();
        for (String packageName : basePackages) {
            classNames.addAll(PackageUtil.getClassName(packageName));
        }
        List<Class<?>> controllers = new ArrayList<>();
        for (String className : classNames) {
            Class<?> c = Class.forName(className);
            if (hasController(c)) {
                controllers.add(c);
            }
        }
        return controllers;
    }

    public static boolean hasController(Class<?> c) {
        if (c.getAnnotation(Controller.class) == null)
            return false;
        return true;
    }

    public static List<Class<?>> getControllers() {
        return getInstance().controllers;
    }

    public static void addController(Class<?> controller) {
        if (controller == null)
            throw new WebMvcException("add null Controller");
        getInstance().controllers.add(controller);
    }

    public static void addControllers(List<Class<?>> controllers) {
        getInstance().controllers.addAll(controllers);
    }
}
