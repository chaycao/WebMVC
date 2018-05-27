package com.chaycao.webmvc.context;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author chaycao
 * @description
 * @date 2018-05-25 15:38.
 */
// TODO 动态加载servlet和filter
@HandlesTypes({WebApplicationInitializer.class})
public class SpringServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> webAppInitializerClasses, ServletContext servletContext) throws ServletException {
        List<WebApplicationInitializer> initializers = new LinkedList();
        Iterator iterator;
        if(webAppInitializerClasses != null) {
            iterator = webAppInitializerClasses.iterator();
            while(iterator.hasNext()) {
                Class<?> waiClass = (Class)iterator.next();
                if(!waiClass.isInterface() && !Modifier.isAbstract(waiClass.getModifiers()) && WebApplicationInitializer.class.isAssignableFrom(waiClass)) {
                    try {
                        initializers.add((WebApplicationInitializer)waiClass.newInstance());
                    } catch (Throwable var7) {
                        throw new ServletException("Failed to instantiate WebApplicationInitializer class", var7);
                    }
                }
            }
        }
        if(initializers.isEmpty()) {
            servletContext.log("No Spring WebApplicationInitializer types detected on classpath");
        } else {
            servletContext.log(initializers.size() + " Spring WebApplicationInitializers detected on classpath");
            AnnotationAwareOrderComparator.sort(initializers);
            iterator = initializers.iterator();

            while(iterator.hasNext()) {
                WebApplicationInitializer initializer = (WebApplicationInitializer)iterator.next();
                initializer.onStartup(servletContext);
            }
        }
    }
}
