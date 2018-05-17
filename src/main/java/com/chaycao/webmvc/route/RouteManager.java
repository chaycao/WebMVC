package com.chaycao.webmvc.route;

import com.chaycao.webmvc.annotation.RequestMapping;
import com.chaycao.webmvc.dispatcher.ControllerManager;
import com.chaycao.webmvc.util.PathUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chaycao
 * @description: 路由管理器
 * @date 2018-04-23 19:16.
 */
public class RouteManager {

    private static final Logger LOGGER = Logger.getLogger(RouteManager.class.getName());

    private List<Route> routes = new ArrayList<Route>();

    private RouteManager() {}

    private static class RoutersHolder {
        private static RouteManager instance = new RouteManager();
    }

    public static RouteManager getInstance() {
        return RoutersHolder.instance;
    }

    /**
     * // 从Controller中扫描具有RequestMapping的方法
     */
    public static void scanAndLoadRouteByController() {
        List<Route> routes = scanRouteByController();
        addRoutes(routes);
    }

    public static List<Route> scanRouteByController() {
        List<Route> routes = new ArrayList<>();
        for (Class<?> controller : ControllerManager.getControllers()) {
            String basePath = getRequestMappingValue(controller);
            for (Method m : controller.getDeclaredMethods()) {
                String routPath = getRequestMappingValue(m);
                if (routPath == null)
                    continue;
                String path = basePath + routPath;
                routes.add(new Route(path, m));
            }
        }
        return routes;
    }

    /**
     * 遍历routers查找符合的Route对象
     * @param request HTTP请求
     * @return 符合的Rounte对象，找不到返回null
     */
    public static Route findRouteByRequest(HttpServletRequest request) {
        String path = PathUtil.getRelativePath(request);
        Route route = findRouteByPath(path);
        return route;
    }

    /**
     *
     * @param path
     * @return 符合的Route对象，若没找到返回null
     */
    public static Route findRouteByPath(String path) {
        List<Route> routes = getInstance().routes;
        for (Route route : routes) {
            if (route.getPath().equals(path)) {
                return route;
            }
        }
        return null;
    }

    /**
     *
     * @param clazz
     * @return 获得RequestMapping的值，若没有则返回空字符串
     */
    private static String getRequestMappingValue(Class<?> clazz) {
        RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
        if (requestMapping != null)
            return requestMapping.value();
        return "";
    }

    /**
     *
     * @param method
     * @return 获得RequestMapping的值，若没有则返回null
     */
    private static String getRequestMappingValue(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null)
            return requestMapping.value();
        return null;
    }

    private static boolean isRequestMapping(Method method) {
        if (method.getAnnotation(RequestMapping.class) == null)
            return false;
        return true;
    }

    public static void addRoute(Route r) {
        getInstance().routes.add(r);
    }

    public static void addRoutes(List<Route> routes) {
        getInstance().routes.addAll(routes);
    }

    public static List<Route> getRoutes() {
        return getInstance().routes;
    }

}
