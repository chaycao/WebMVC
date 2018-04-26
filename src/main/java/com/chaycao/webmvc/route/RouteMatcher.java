package com.chaycao.webmvc.route;

import com.chaycao.webmvc.util.PathUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Title:路由匹配器
 *
 * @author chaycao
 * @description:
 * @date 2018-04-23 19:20.
 */
public class RouteMatcher {

    /**
     * 遍历routers查找符合的Route对象
     * @param request HTTP请求
     * @return 符合的Rounte对象，找不到返回null
     */
    public static Route findRoute(List<Route> routes, HttpServletRequest request) {
        String path = PathUtil.getRelativePath(request);
        for (Route route : routes) {
            if (route.getPath().equals(path)) {
                return route;
            }
        }
        return null;
    }

}
