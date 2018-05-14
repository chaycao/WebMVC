package com.chaycao.webmvc.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @author chaycao
 * @description
 * @date 2018-05-14 17:18.
 */
public class WrapperResponse extends HttpServletResponseWrapper {
    private Logger log = Logger.getLogger(WrapperResponse.class);

    private MyPrintWriter tmpWriter;
    private ByteArrayOutputStream output;
    public WrapperResponse(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        output = new ByteArrayOutputStream();;// 真正存储数据的返回流（保存数据返回的结果）
        tmpWriter = new MyPrintWriter(output);
    }

    public String getContent() {
        String str = "";
        try {
            //刷新该流的缓冲，详看java.io.Writer.flush()
            tmpWriter.flush();
            str = tmpWriter.getByteArrayOutputStream().toString("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("不支持的编码异常,Unsupported Encoding Exception！");
        }finally {
            try {
                output.close();
                tmpWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("释放资源异常，release resource error!");
            }

        }
        return str;
    }

    //覆盖getWriter()方法，使用我们自己定义的Writer
    public PrintWriter getWriter() throws IOException {
        return tmpWriter;
    }

    public void close() throws IOException {
        tmpWriter.close();
    }

    //自定义PrintWriter，为的是把response流写到自己指定的输入流当中
    //而非默认的ServletOutputStream
    private static class MyPrintWriter extends PrintWriter {
        ByteArrayOutputStream myOutput;
        //此即为存放response输入流的对象

        public MyPrintWriter(ByteArrayOutputStream output) {
            super(output);
            myOutput = output;
        }

        public ByteArrayOutputStream getByteArrayOutputStream() {
            return myOutput;
        }

    }
}
