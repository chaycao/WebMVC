package com.chaycao.webmvc.dispatcher;

import com.chaycao.webmvc.context.Context;
import com.chaycao.webmvc.handler.Handler;
import com.chaycao.webmvc.route.Route;
import com.chaycao.webmvc.route.RouteManager;
import com.chaycao.webmvc.view.*;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chaycao
 * @description: 整体入口，负责接收和转发web请求到内部框架处理单元
 * @date 2018-04-23 11:30.
 */
public class DispatcherServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(DispatcherServlet.class.getName());

    private ApplicationContext applicationContext;

    private RouteManager routeManager;

    private Handler handler;

    private ViewResolver viewResolver;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doService(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doService(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doService(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doService(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doService(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doService(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doService(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        doDispatch(request, response);
    }

    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("DispatcherServlet doDispatch: " + request.getRequestURL());
        Route route = routeManager.findRouteByRequest(request);
        if (route == null) { // 未找到路由
            logger.info("Can not find Rout : " + request.getRequestURL());
            // TODO 未正确返回404，需要修改
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            ModelAndView mv = handler.handle(request, response, route);
            View view = viewResolver.resovleModelAndView(mv);
            view.render(mv.getModel(), request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        logger.info("DispatcherServlet start init.");

        applicationContext = Context.APPLICATION_CONTEXT;
        routeManager = (RouteManager) applicationContext.getBean("routeManager");
        handler = (Handler) applicationContext.getBean("httpHandler");
        viewResolver = (ViewResolver) applicationContext.getBean("jspViewResolver");

        routeManager.scanAndLoadRouteByController();

        JspRegister.registeJspServlet(this.getServletContext());
    }

}
