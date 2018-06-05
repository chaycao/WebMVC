package com.chaycao.webmvc.handler;

import com.chaycao.webmvc.annotation.RequestParam;
import com.chaycao.webmvc.annotation.RequestPart;
import com.chaycao.webmvc.context.PropertiesContext;
import com.chaycao.webmvc.multipart.MultipartFile;
import com.chaycao.webmvc.route.Route;
import com.chaycao.webmvc.util.PathUtil;
import com.chaycao.webmvc.util.TypeUtil;
import com.chaycao.webmvc.view.ModelAndView;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chaycao
 * @description
 * @date 2018-04-27 16:02.
 */
@Component
@Scope("prototype")
public class HandlerMethodArgumentResolver {

    @Autowired
    private MultipartResolver multipartResolver;

    public Object[] resolveArgument(HttpServletRequest request, HttpServletResponse response, Route route) throws FileUploadException {
        Method method = route.getAction();
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        // 是否有文件上传请求
        if (multipartResolver.checkMultipart(request))
            multipartResolver.resolveRequest(request);

        Map<String, String> urlParm = getUrlParm(request, route);

        int i = 0;
        for (Parameter p : parameters) {
            Class<?> type = p.getType();
            if (type == HttpServletRequest.class) {
                args[i++] = request;
            } else if (type == HttpServletResponse.class) {
                args[i++] = response;
            } else if (p.getAnnotation(RequestParam.class) != null) {
                if (urlParm.get(p.getName())!= null)
                    args[i++] = TypeUtil.cast(urlParm.get(p.getName()), p.getType());
                else
                    args[i++] = TypeUtil.cast(request.getParameter(p.getName()), p.getType());
            } else if (type == ModelAndView.class) {
                args[i++] = new ModelAndView();
            } else if (p.getAnnotation(RequestPart.class) != null) {
                MultipartFile file = multipartResolver.getMultipart(p.getName());
                args[i++] = file;
            }
        }
        return args;
    }

    private Map<String, String> getUrlParm(HttpServletRequest request, Route route) {
        String path = PathUtil.getRelativePath(request);
        Pattern urlPattern = route.getUrlPattern();
        String[] parmNames = route.getParmNames();
        Matcher matcher = urlPattern.matcher(path);
        Map<String, String> map = new HashMap<>(parmNames.length);
        if (matcher.matches()) {
            for (int i = 0; i < parmNames.length; i++) {
                map.put(parmNames[i], matcher.group(i+1));
            }
        }
        return map;
    }
}
