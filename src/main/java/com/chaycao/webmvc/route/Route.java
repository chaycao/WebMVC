package com.chaycao.webmvc.route;

import java.lang.reflect.Method;

/**
 * Title:
 *
 * @author chaycao
 * @description:
 * @date 2018-04-23 19:01.
 */
public class Route {
    /**
     * 路由path
     */
    private String path;

    /**
     * 执行路由的方法
     */
    private Method action;

    /**
     * 对象
     */
    private Object object;

    public Route(String path, Method action) {
        this.path = path;
        this.action = action;
        try {
            this.object = this.action.getDeclaringClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Method getAction() {
        return action;
    }

    public void setAction(Method action) {
        this.action = action;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
