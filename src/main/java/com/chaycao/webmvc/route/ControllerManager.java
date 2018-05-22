package com.chaycao.webmvc.route;

import com.chaycao.webmvc.annotation.Controller;
import com.chaycao.webmvc.config.PropertiesConfig;
import com.chaycao.webmvc.expection.WebMvcException;
import com.chaycao.webmvc.util.PackageUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chaycao
 * @description: Controller组件的配置
 * @date 2018-04-25 16:08.
 */
@Component
public class ControllerManager {

    private List<Class<?>> controllers;

    public ControllerManager() {
        controllers = new ArrayList<>();
        scanAndLoadController();
    }

    /**
     * 加载Controller
     * 查找指定扫描包下的所有类，筛选具有Controller注解的类
     */
    public void scanAndLoadController() {
        String value = PropertiesConfig.getProperty("CompantScanBasePackages", "");
        String[] basePackages = value.split(",");
        List<Class<?>> controllers = null;
        try {
            controllers = scanController(basePackages);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        addControllers(controllers);
    }

    public List<Class<?>> scanController(String[] basePackages) throws ClassNotFoundException {
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

    public boolean hasController(Class<?> c) {
        if (c.getAnnotation(Controller.class) == null)
            return false;
        return true;
    }

    public List<Class<?>> getControllers() {
        return this.controllers;
    }

    public void addController(Class<?> controller) {
        if (controller == null)
            throw new WebMvcException("add null Controller");
        this.controllers.add(controller);
    }

    public void addControllers(List<Class<?>> controllers) {
        this.controllers.addAll(controllers);
    }
}
