package com.chaycao.route;

import com.chaycao.controller.TestController;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Title:
 *
 * @author chaycao
 * @description:
 * @date 2018-04-25 20:59.
 */
public class Parameter {
    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Method[] ms = TestController.class.getDeclaredMethods();
        for (Method m : ms) {
            if (m.getParameters().length != 0)
            {
                java.lang.reflect.Parameter[] p = m.getParameters();
                System.out.println(p[0].getName());
                Object[] args = {1};
                m.invoke(TestController.class.newInstance(), args);
            }
        }


    }
}
