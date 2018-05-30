package com.chaycao.webmvc.handler;

import com.chaycao.webmvc.multipart.MultipartFile;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chaycao
 * @description
 * @date 2018-05-27 17:46.
 */
public interface MultipartResolver {
    boolean checkMultipart(HttpServletRequest request);
    void resolveRequest(HttpServletRequest request) throws FileUploadException;
    MultipartFile getMultipart(String name);
}
