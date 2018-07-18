package com.android.framework.jc.util;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/4/15 11:50
 * @organize 卓世达科
 * @describe
 * @update
 */
public class FileUtils {
    public static void write(InputStream inputStream, String savePath) throws IOException {
        if (inputStream == null) {
            throw new IOException("inputStream can not null");
        }
        File file = new File(savePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        byte[] buffer = new byte[1024];
        int len;
        try (FileOutputStream fos = new FileOutputStream(file); BufferedInputStream bis = new BufferedInputStream(inputStream)) {
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                fos.flush();
            }
        } finally {
            inputStream.close();
        }
    }


    public static String getFileName(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        int index = url.lastIndexOf(63);
        int index2 = url.lastIndexOf("/");
        return index > 0 && index2 >= index ? UUID.randomUUID().toString() : url.substring(index2 + 1, index < 0 ? url.length() : index);

    }
}
