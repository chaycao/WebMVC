package com.chaycao.webmvc.dispatcher;

import com.chaycao.webmvc.configuration.ControllerConfig;
import com.chaycao.webmvc.handler.Handler;
import com.chaycao.webmvc.route.Route;
import com.chaycao.webmvc.route.RouteMatcher;
import com.chaycao.webmvc.route.Routers;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Title:DispatcherServlet
 * 一个标准的Servlet，负责接收和转发web请求到内部框架处理单元
 *
 * @author chaycao
 * @description:
 * @date 2018-04-23 11:30.
 */
public class DispatcherServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(DispatcherServlet.class.getName());

    /** 处理Get请求 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doService(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 处理Post请求 */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doService(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        doDispatch(request, response);
    }

    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Route route = RouteMatcher.findRoute(Routers.getInstance().getRoutes(), request);
        Handler.handler(request, response, route);
    }

    @Override
    public void init() throws ServletException {
        // 初始化ControllerConfig，扫描加载Controller
        ControllerConfig.init();
        // 初始化Rounters，扫描管理Rounte
        Routers.init();
    }

}
