package com.chaycao.webmvc.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author chaycao
 * @description
 * @date 2018-05-30 15:52.
 */
public class StreamUtil {
    private static final byte[] EMPTY_CONTENT = new byte[0];

    public static InputStream emptyInput() {
        return new ByteArrayInputStream(EMPTY_CONTENT);
    }
}
