package com.chaycao.webmvc.util;

import com.chaycao.webmvc.expection.WebMvcException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author chaycao
 * @description
 * @date 2018-05-16 20:45.
 */
public class FileUtil {

    private static LinkedList<File> queueFiles = new LinkedList<>();
    /**
     * 扫描当前位置下的全部JSP文件，返回当前项目的相对位置，
     * 如“/index.jsp”
     * @return
     */
    public static List<String> scanAllJsp(String root) throws IOException {
        List<String> jspPathList = new ArrayList<>();
        if (root == null)
            return null;
        File dir = new File(root);
        if (!dir.isDirectory())
            throw  new WebMvcException(root + " is not a directory.");
        else
            queueFiles.add(dir);
        while (!queueFiles.isEmpty()) {
            File headDirectory = queueFiles.removeFirst();
            File[] currentFiles = headDirectory.listFiles();
            for (File file : currentFiles) {
                if (file.isDirectory()) {
                    queueFiles.add(file);
                } else {
                    String path = file.getCanonicalPath();
                    if (path.endsWith(".jsp")) {
                        String relativePath = "/" + path.substring(root.length()).replace("\\","/");
                        jspPathList.add(relativePath);
                    }
                }
            }
        }
        return jspPathList;
    }
}
