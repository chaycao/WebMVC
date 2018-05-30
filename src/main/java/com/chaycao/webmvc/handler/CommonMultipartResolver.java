package com.chaycao.webmvc.handler;

import com.chaycao.webmvc.context.PropertiesContext;
import com.chaycao.webmvc.expection.WebMvcException;
import com.chaycao.webmvc.multipart.CommonMultipartFile;
import com.chaycao.webmvc.multipart.MultipartFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * @author chaycao
 * @description
 * @date 2018-05-30 16:05.
 */
@Component
public class CommonMultipartResolver implements MultipartResolver {

    private HashMap<String, FileItem> fileItems = new HashMap<>();

    @Override
    public boolean checkMultipart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    @Override
    public void resolveRequest(HttpServletRequest request) throws FileUploadException {
        fileItems.clear();
        DiskFileItemFactory disk = new DiskFileItemFactory(Integer.valueOf(PropertiesContext.getProperty("tmpDirSize", "10240")), new File(PropertiesContext.getProperty("tmpDir", "/tmp")));
        ServletFileUpload upload = new ServletFileUpload(disk);
        upload.setHeaderEncoding("utf-8");
        List<FileItem> list = upload.parseRequest(request);
        for (FileItem item : list) {
            fileItems.put(item.getFieldName(), item);
        }
    }

    @Override
    public MultipartFile getMultipart(String name) {
        FileItem item = fileItems.get(name);
        if (item != null) {
            return new CommonMultipartFile(item, item.getSize());
        } else {
            throw new WebMvcException("MultiPart [" + name + "] can not attach");
        }
    }
}
