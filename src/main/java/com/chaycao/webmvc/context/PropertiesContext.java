package com.chaycao.webmvc.context;

import java.io.IOException;
import java.util.Properties;

/**
 * Title:
 *
 * @author chaycao
 * @description:
 * @date 2018-04-23 20:35.
 */
public class PropertiesContext {
    private Properties prop;
    private PropertiesContext() {
        prop = new Properties();
        try {
            prop.load(PropertiesContext.class.getClassLoader().getResourceAsStream("webmvc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static class PropertiesConfigHolder {
        private static PropertiesContext instance = new PropertiesContext();
    }
    public static PropertiesContext getInstance() {
        return PropertiesConfigHolder.instance;
    }

    /**
     * 获得配置文件中的配置
     * @param name
     * @return 配置值，若为name为null或空字符串，返回null
     */
    public static String getProperty(String name, String defaultValue) {
        if (name == null || name.equals(""))
            return null;
        return getInstance().prop.getProperty(name);
    }
}
