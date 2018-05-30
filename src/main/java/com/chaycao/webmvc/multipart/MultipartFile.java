package com.chaycao.webmvc.multipart;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
/**
 * @author chaycao
 * @description
 * @date 2018-05-27 17:13.
 */
public interface MultipartFile {
    String getName();

    String getOriginalFilename();

    String getContentType();

    boolean isEmpty();

    long getSize();

    byte[] getBytes() throws IOException;

    InputStream getInputStream() throws IOException;

    void transferTo(File file) throws IOException, IllegalStateException;
}
