package com.chaycao.webmvc.multipart;

import com.chaycao.webmvc.util.StreamUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author chaycao
 * @description
 * @date 2018-05-30 15:36.
 */
public class CommonMultipartFile implements MultipartFile {

    private final FileItem fileItem;
    private final long size;

    public CommonMultipartFile(FileItem fileItem, long size) {
        this.fileItem = fileItem;
        this.size = size;
    }


    @Override
    public String getName() {
        return this.fileItem.getName();
    }

    @Override
    public String getOriginalFilename() {
        String filename = this.fileItem.getName();
        if (filename == null) {
            return "";
        } else {
            int unixSep = filename.lastIndexOf("/");
            int winSep = filename.lastIndexOf("\\");
            int pos = winSep > unixSep?winSep : unixSep;
            return pos != -1?filename.substring(pos+1):filename;
        }
    }

    @Override
    public String getContentType() {
        return this.fileItem.getContentType();
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0L;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public byte[] getBytes() throws IOException {
        if (!this.isAvailable()) {
            throw new IllegalStateException("File has been moved - cannot be read again");
        } else {
            byte[] bytes = this.fileItem.get();
            return bytes != null?bytes:new byte[0];
        }
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (!this.isAvailable()) {
            throw new IllegalStateException("File has been moved - cannot be read again");
        } else {
            InputStream inputStream = this.fileItem.getInputStream();
            return inputStream != null?inputStream:StreamUtil.emptyInput();
        }
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        if (!this.isAvailable()) {
            throw new IllegalStateException("File has been moved - cannot be read again");
        } else if (dest.exists() && !dest.delete()){
            throw new IOException("Destination file [" + dest.getAbsolutePath() + "] already exists and could not be deleted");
        } else {
            try {
                this.fileItem.write(dest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean isAvailable() {
        return this.fileItem.isInMemory()?true:(this.fileItem instanceof DiskFileItem?((DiskFileItem)this.fileItem).getStoreLocation().exists():this.fileItem.getSize() == this.size);
    }
}
