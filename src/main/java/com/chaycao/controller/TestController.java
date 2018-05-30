package com.chaycao.controller;

import com.chaycao.webmvc.annotation.Controller;
import com.chaycao.webmvc.annotation.RequestMapping;
import com.chaycao.webmvc.annotation.RequestParam;
import com.chaycao.webmvc.annotation.RequestPart;
import com.chaycao.webmvc.multipart.MultipartFile;
import com.chaycao.webmvc.view.ModelAndView;

import java.io.*;

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
    @RequestMapping("/file")
    public void fileTest(@RequestPart MultipartFile file) {
        System.out.println(file.getName());
        try {
            byte [] buffer=new byte[400];
            int length = 0;
            InputStream input = file.getInputStream();
            InputStreamReader isr = new InputStreamReader(input, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
