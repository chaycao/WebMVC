package com.chaycao.webmvc.dispatcher;

import com.chaycao.webmvc.config.Context;
import com.chaycao.webmvc.config.ControllerConfig;
import com.chaycao.webmvc.handler.HttpHandler;
import com.chaycao.webmvc.route.Route;
import com.chaycao.webmvc.route.RouteManager;
import com.chaycao.webmvc.view.ModelAndView;
import com.chaycao.webmvc.view.View;
import com.chaycao.webmvc.view.ViewResolver;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
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

    //TODO ViewResolver解析ModelAndView
    //TODO 使用Model对View渲染
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Route route = RouteManager.findRouteByRequest(request);
        ModelAndView mv = HttpHandler.newInstance().handler(request, response, route);
        View view = ViewResolver.newInstance().resovleModelAndView(mv);
        view.render(mv.getModel(), request, response, Context.SERVLETCONTEXT);
    }

    @Override
    public void init() throws ServletException {
        Context.REAL_CONTEXT_PATH = this.getServletContext().getRealPath("");
        Context.SERVLETCONTEXT = this.getServletContext();
        // 扫描加载Controller
        try {
            ControllerConfig.scanAndLoadController();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 扫描加载Rounte
        RouteManager.scanAndLoadRouteByController();

        //注册JSP的Servlet
        ServletRegistration jspServlet = Context.SERVLETCONTEXT.getServletRegistration("jsp");
        jspServlet.addMapping("/WEB-INF/*");
//        //注册处理静态资源的Servlet
//        ServletRegistration defaultServlet = sc.getServletRegistration("default");
//        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

}
