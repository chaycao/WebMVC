package com.chaycao.webmvc.view;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author chaycao
 * @description
 * @date 2018-06-05 19:20.
 */
public class JsonView implements View {
    private String json;

    public JsonView() {
        this.json = "";
    }

    public JsonView(String json) {
        this.json = json;
    }
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletOutputStream output = response.getOutputStream();
        output.write(this.json.getBytes());
        output.close();
    }
}
