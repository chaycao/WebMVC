package com.chaycao.controller;

import com.chaycao.webmvc.annotation.Controller;
import com.chaycao.webmvc.annotation.RequestMapping;
import com.chaycao.webmvc.annotation.RequestParam;
import com.chaycao.webmvc.annotation.RequestPart;
import com.chaycao.webmvc.multipart.MultipartFile;
import com.chaycao.webmvc.view.ModelAndView;

import java.io.*;

import static com.chaycao.webmvc.annotation.RequestMethod.*;

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
    @RequestMapping("/par/")
    public void parTest(@RequestParam int a, @RequestParam String b) {
        System.out.println("OK:par " + a + b);
    }
    @RequestMapping("/parRest/{a}/{b}")
    public void parRestTest(@RequestParam int a, @RequestParam String b) {
        System.out.println("OK:parRest " + a + b);
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

    @RequestMapping(value = "/getPost", method = {GET, POST})
    public void getPostTest() {
        System.out.println("getPost OK");
    }

    @RequestMapping(value = "/putDelete", method = {PUT, DELETE})
    public void putDeleteTest() {
        System.out.println("putDelete OK");
    }

    @RequestMapping(value = "/produces", produces = "application/json")
    public void producesJsonTest() {
        System.out.println("produces application/json OK");
    }

    @RequestMapping(value = "/produces", produces = "text/html")
    public void producesHtmlTest() {
        System.out.println("produces text/html OK");
    }

    @RequestMapping(value = "/consume", consumes = "application/json")
    public void consumeJsonTest() {
        System.out.println("consume application/json OK");
    }

    @RequestMapping(value = "/consume", consumes = "text/html")
    public void consumeHtmlTest() {
        System.out.println("consume text/html OK");
    }

}
