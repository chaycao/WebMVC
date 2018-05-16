package com.chaycao.webmvc.config;

import java.io.IOException;
import java.util.Properties;

/**
 * Title:
 *
 * @author chaycao
 * @description:
 * @date 2018-04-23 20:35.
 */
public class PropertiesConfig {
    private Properties prop;
    private PropertiesConfig() {
        prop = new Properties();
        try {
            prop.load(PropertiesConfig.class.getClassLoader().getResourceAsStream("webmvc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static class PropertiesConfigHolder {
        private static PropertiesConfig instance = new PropertiesConfig();
    }
    public static PropertiesConfig getInstance() {
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
        //TODO 根据不同属性，不同默认值设置
        String property = getInstance().prop.getProperty(name);

        return property;
    }
}
