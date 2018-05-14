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
    public static String getProperty(String name) {
        return getInstance().prop.getProperty(name);
    }
}
