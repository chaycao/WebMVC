package com.chaycao.webmvc.route;

import com.chaycao.webmvc.annotation.RequestMapping;
import com.chaycao.webmvc.annotation.RequestMethod;
import com.chaycao.webmvc.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chaycao
 * @description: 路由管理器
 * @date 2018-04-23 19:16.
 */
@Component
public class RouteManager {

    private List<Route> routes;

    @Autowired
    private ControllerManager controllerManager;

    public RouteManager() {
        routes = new ArrayList<>();
    }

    /**
     * // 从Controller中扫描具有RequestMapping的方法
     */
    public void scanAndLoadRouteByController() {
        List<Route> routes = scanRouteByController();
        addRoutes(routes);
    }

    public List<Route> scanRouteByController() {
        List<Route> routes = new ArrayList<>();
        for (Class<?> controller : controllerManager.getControllers()) {
            String basePath = getRequestMappingValue(controller);
            for (Method m : controller.getDeclaredMethods()) {
                if (isRequestMapping(m)) {
                    String path = basePath + getRequestMappingValue(m);
                    RequestMethod[] methods = getRequestMappingMethods(m);
                    String[] produces = getRequestMappingProduces(m);
                    String[] consumes = getRequestMappingConsumes(m);
                    routes.add(new Route(path, m, methods, produces, consumes));
                }
            }
        }
        return routes;
    }

    /**
     * 遍历routers查找符合的Route对象
     * @param request HTTP请求
     * @return 符合的Rounte对象，找不到返回null
     */
    public Route findRouteByRequest(HttpServletRequest request) {
        List<Route> routes = findRoutesByPath(this.getRoutes(), request);
        for (Route route : routes) {
            if (route.canSupportRequest(request))
                return route;
        }
        return null;
    }

    /**
     *
     *
     * @param list
     * @param request
     * @return 符合的Route对象，若没找到返回null
     */
    public List<Route> findRoutesByPath(List<Route> list, HttpServletRequest request) {
        String path = PathUtil.getRelativePath(request);
        List<Route> res = new ArrayList<>();
        for (Route route : list) {
            if (route.getPath().equals(path)) {
                res.add(route);
            }
        }
        return res;
    }

    /**
     *
     * @param clazz
     * @return 获得RequestMapping的值，若没有则返回空字符串
     */
    private String getRequestMappingValue(Class<?> clazz) {
        RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
        if (requestMapping != null)
            return requestMapping.value();
        return "";
    }

    /**
     *
     * @param method
     * @return 获得RequestMapping的Value，若没有则返回null
     */
    private String getRequestMappingValue(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null)
            return requestMapping.value();
        return null;
    }

    private boolean isRequestMapping(Method method) {
        if (method.getAnnotation(RequestMapping.class) == null)
            return false;
        return true;
    }

    private RequestMethod[] getRequestMappingMethods(Class<?> clazz) {
        RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
        if (requestMapping != null)
            return requestMapping.method();
        return null;
    }

    private RequestMethod[] getRequestMappingMethods(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null)
            return requestMapping.method();
        return null;
    }

    private String[] getRequestMappingProduces(Class<?> clazz) {
        RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
        if (requestMapping != null)
            return requestMapping.produces();
        return null;
    }

    private String[] getRequestMappingProduces(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null)
            return requestMapping.produces();
        return null;
    }

    private String[] getRequestMappingConsumes(Class<?> clazz) {
        RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
        if (requestMapping != null)
            return requestMapping.consumes();
        return null;
    }

    private String[] getRequestMappingConsumes(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null)
            return requestMapping.consumes();
        return null;
    }

    public void addRoute(Route r) {
        this.routes.add(r);
    }

    public void addRoutes(List<Route> routes) {
        this.routes.addAll(routes);
    }

    public List<Route> getRoutes() {
        return this.routes;
    }

}
