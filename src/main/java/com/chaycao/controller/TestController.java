package com.chaycao.controller;

import com.chaycao.webmvc.annotation.Controller;
import com.chaycao.webmvc.annotation.RequestMapping;
import com.chaycao.webmvc.annotation.RequestParam;
import com.chaycao.webmvc.view.ModelAndView;

/**
 * Title:
 *
 * @author chaycao
 * @description:
 * @date 2018-04-23 20:22.
 */
@Controller
public class TestController {
    @RequestMapping("/")
    public void root() {
        System.out.println("OK:root");
    }
    @RequestMapping("/void")
    public void voidTest(){
        System.out.println("OK:void");
    }
    @RequestMapping("/par")
    public void parTest(@RequestParam int a, @RequestParam String b) {
        System.out.println("OK:par " + a + b);
    }
    @RequestMapping("/view")
    public String view() {
        return "WEB-INF/index";
    }
    @RequestMapping("/model")
    public ModelAndView model(ModelAndView modelAndView) {
        modelAndView.addObject("name", "chaycao");
        modelAndView.setView("model");
        return modelAndView;
    }
}
