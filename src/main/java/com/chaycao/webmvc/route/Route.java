package com.chaycao.webmvc.route;

import com.chaycao.webmvc.annotation.RequestMethod;
import com.chaycao.webmvc.util.PathUtil;
import com.chaycao.webmvc.util.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * HTTP的方法类型
     */
    private RequestMethod[] requestMethods;

    /**
     * HTTP的Accept
     */
    private String[] produces;

    /**
     * HTTP的Content-Type
     */
    private String[] consumes;

    /**
     * 用于正则匹配的格式，将path中的{}替换成 ([^\\/]*)，匹配参数用
     */
    private Pattern urlPattern;

    /**
     * 参数名
     */
    private String[] parmNames;

    public Route(String path, Method action, RequestMethod[] requestMethods, String[] produces, String[] consumes) {
        this.path = path;
        this.action = action;
        this.requestMethods = requestMethods;
        this.produces = produces;
        this.consumes = consumes;
        try {
            this.object = this.action.getDeclaringClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        this.urlPattern = buildUrlPattern(path);
        this.parmNames = buildParmNames(path);
    }

    public boolean canSupportRequest(HttpServletRequest request) {
        if (canSupportRequestPath(request) &&
                canSupportRequestMethod(request) &&
                canSupportRequestAccept(request) &&
                canSupportRequestContentType(request)
                )
            return true;
        return false;
    }

    private boolean canSupportRequestPath(HttpServletRequest request) {
        String path = PathUtil.getRelativePath(request);
        Matcher urlMatcher = this.urlPattern.matcher(path);
        if (urlMatcher.matches())
            return true;
        return false;
    }

    private boolean canSupportRequestMethod(HttpServletRequest request) {
        if (this.getRequestMethods().length == 0) {
            return true;
        }
        for (RequestMethod requestMethod : this.getRequestMethods()) {
            if (requestMethod == RequestMethod.valueOf(request.getMethod().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    private boolean canSupportRequestAccept(HttpServletRequest request) {
        return canMatchContentType(HttpUtil.getAccept(request).split(","), this.getProduces());
    }

    private boolean canSupportRequestContentType(HttpServletRequest request) {
        return canMatchContentType(HttpUtil.getContentType(request).split(","), this.getConsumes());
    }

    private boolean canMatchContentType(String[] requestContentType, String[] routeContentType) {
        if (routeContentType.length == 0 || requestContentType.length == 0)
            return true;
        HashMap<String, HashSet<String>> map = new HashMap<>();
        for (String contentType : routeContentType) {
            String[] types = contentType.split("/");
            String type = types[0];
            String subType = types[1];
            if (map.containsKey(type)) {
                HashSet<String> set = map.get(type);
                set.add(subType);
            } else {
                HashSet<String> set = new HashSet<>();
                set.add(subType);
                map.put(type, set);
            }
        }
        if (map.get("*") != null && map.get("*").contains("*"))
            return true;
        for (String contentType : requestContentType) {
            String body = contentType.split(";")[0]; // 只取type部分，参数部分丢弃
            String[] types = body.split("/");
            String type = types[0];
            String subType = types[1];
            if (map.get("*") != null) {
                if (map.get("*").contains(subType) || subType.equals("*")) // Route：*/B
                    return true;

            }
            if (type.equals("*")) { // request: */B
                if (subType.equals("*")) // */*
                    return true;
                Iterator<String> iteratorKey = map.keySet().iterator();
                while (iteratorKey.hasNext()) {
                    String key = iteratorKey.next();
                    HashSet<String> set = map.get(key);
                    if (set.contains("*") || set.contains(subType))
                        return true;
                }
            }
            if (map.get(type)!=null &&
                    (subType.equals("*") || map.get(type).contains(subType)))
                return true;
        }
        return false;
    }

    // 将path中的{}替换成 ([^\\/]*)，匹配参数用
    private Pattern buildUrlPattern(String path) {
        path = path.replaceAll("//","\\//");
        path = path.replaceAll("\\{.*?\\}", "([^\\/]*)");
        return Pattern.compile(path);
    }

    private String[] buildParmNames(String path) {
        String parmPatternString = "(?<=\\{).*?(?=\\})";
        Pattern parmPattern = Pattern.compile(parmPatternString);
        Matcher parmMatcher = parmPattern.matcher(path);
        List<String> parmList = new ArrayList<>();
        while (parmMatcher.find()) {
            parmList.add(parmMatcher.group());
        }
        String[] parmNames = new String[parmList.size()];
        return parmList.toArray(parmNames);
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

    public RequestMethod[] getRequestMethods() {
        return requestMethods;
    }

    public void setRequestMethods(RequestMethod[] requestMethods) {
        this.requestMethods = requestMethods;
    }

    public String[] getProduces() {
        return produces;
    }

    public void setProduces(String[] produces) {
        this.produces = produces;
    }

    public String[] getConsumes() {
        return consumes;
    }

    public void setConsumes(String[] consumes) {
        this.consumes = consumes;
    }

    public Pattern getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(Pattern urlPattern) {
        this.urlPattern = urlPattern;
    }

    public String[] getParmNames() {
        return parmNames;
    }

    public void setParmNames(String[] parmNames) {
        this.parmNames = parmNames;
    }
}
