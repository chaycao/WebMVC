package com.chaycao.webmvc.dispatcher;

import com.chaycao.webmvc.config.AppConfig;
import com.chaycao.webmvc.config.Context;
import com.chaycao.webmvc.handler.HttpHandler;
import com.chaycao.webmvc.route.ControllerManager;
import com.chaycao.webmvc.route.Route;
import com.chaycao.webmvc.route.RouteManager;
import com.chaycao.webmvc.view.ModelAndView;
import com.chaycao.webmvc.view.View;
import com.chaycao.webmvc.view.ViewResolver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

    private RouteManager routeManager;

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

    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        doDispatch(request, response);
    }

    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("DispatcherServlet doDispatch: " + request.getRequestURL());
        Route route = routeManager.findRouteByRequest(request);
        if (route == null) { // 未找到路由
            logger.info("Can not find Rout : " + request.getRequestURL());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            ModelAndView mv = HttpHandler.newInstance().handler(request, response, route);
            View view = ViewResolver.newInstance().resovleModelAndView(mv);
            view.render(mv.getModel(), request, response, Context.SERVLET_CONTEXT);
        }
    }

    @Override
    public void init() throws ServletException {
        logger.info("DispatcherServlet start init.");
        Context.REAL_CONTEXT_PATH = this.getServletContext().getRealPath("");
        Context.SERVLET_CONTEXT = this.getServletContext();

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        this.routeManager = (RouteManager) context.getBean("routeManager");

        JspRegister.registeJspServlet();
    }

}
