package com.chaycao.webmvc.route;

import com.chaycao.webmvc.annotation.RequestMapping;
import com.chaycao.webmvc.configuration.ControllerConfig;
import com.chaycao.webmvc.configuration.PropertiesConfig;
import com.chaycao.webmvc.util.PackageUtil;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Title:路由管理器
 * 存放所有路由
 *
 * @author chaycao
 * @description:
 * @date 2018-04-23 19:16.
 */
public class Routers {

    private static final Logger LOGGER = Logger.getLogger(Routers.class.getName());

    private List<Route> routes = new ArrayList<Route>();

    private Routers() {}

    private static class RoutersHolder {
        private static Routers instance = new Routers();
    }

    public static Routers getInstance() {
        return RoutersHolder.instance;
    }

    /**
     * // 从Controller中扫描具有RequestMapping的方法
     */
    public static void init() {
        for (Class<?> c : ControllerConfig.getControllers()) {
            RequestMapping rm = c.getAnnotation(RequestMapping.class);
            String basePath = "";
            if (rm != null) {
                basePath = rm.value();
            }
            for (Method m : c.getDeclaredMethods()) {
                RequestMapping r = m.getAnnotation(RequestMapping.class);
                if (r != null) {
                    addRoute(new Route(basePath+r.value(), m));
                }
            }
        }
    }

    public static void addRoute(Route r) {
        getInstance().routes.add(r);
    }

    public static List<Route> getRoutes() {
        return getInstance().routes;
    }

}
