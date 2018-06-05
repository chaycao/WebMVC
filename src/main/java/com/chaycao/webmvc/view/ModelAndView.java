package com.chaycao.webmvc.view;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chaycao
 * @description
 * @date 2018-04-27 10:44.
 */
public class ModelAndView {
    private Object view;
    private Map model;

    public ModelAndView() {
    }

    public ModelAndView(String viewName) {
        this.view = viewName;
    }

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView(String viewName, Map<String, ?> model) {
        this.view = viewName;
        if (model != null)
            this.getModel().putAll(model);
    }

    public void setViewName(String viewName) {
        this.view = viewName;
    }

    public String getViewName() {
        return this.view instanceof String?(String)this.view:null;
    }

    public Object getView() {
        return view;
    }

    public void setView(Object view) {
        this.view = view;
    }

    public Map getModel() {
        if (this.model == null) {
            this.model = new HashMap();
        }
        return this.model;
    }

    public void setMap(Map model) {
        this.model = model;
    }

    public ModelAndView addObject(Object attributeName, Object attributeValue) {
        this.getModel().put(attributeName, attributeValue);
        return this;
    }

    @Override
    public String toString() {
        return view.toString();
    }
}
