package com.android.framework.jc.util;

import android.os.Environment;
import android.text.TextUtils;

import com.android.framework.jc.JcFramework;
import com.android.framework.jc.data.bean.DownloadBean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.UUID;

import okhttp3.ResponseBody;

/**
 * @author Mr.Hu(Jc)JCFramework
 * @create 2018/4/15 11:50
 * @describe 文件相关工具类
 * @update
 */
public class FileUtils {

    /**
     * 根据流写入数据到指定路径
     *
     * @param inputStream
     *         流
     * @param savePath
     *         保存路径
     *
     * @throws IOException
     *         异常
     */
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

    /**
     * 下载文件写入
     *
     * @param responseBody
     *         响应体
     * @param downloadBean
     *         下载数据bean
     *
     * @throws IOException
     *         异常
     */
    public static void write(ResponseBody responseBody, DownloadBean downloadBean) throws IOException {
        RandomAccessFile randomAccessFile = null;
        FileChannel channelOut = null;
        InputStream inputStream = null;
        File file = new File(downloadBean.getSavePath());
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            long allLength = 0 == downloadBean.getTotalLength() ? responseBody.contentLength() : downloadBean.getDownLength() + responseBody.contentLength();

            inputStream = responseBody.byteStream();
            randomAccessFile = new RandomAccessFile(file, "rwd");
            channelOut = randomAccessFile.getChannel();
            MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, downloadBean.getDownLength(), allLength - downloadBean.getDownLength());
            byte[] buffer = new byte[1024 * 4];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                mappedBuffer.put(buffer, 0, len);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (channelOut != null) {
                channelOut.close();
            }
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }
    }

    /**
     * 根据下载链接获取文件名称
     *
     * @param downloadUrl
     *         下载地址
     *
     * @return 文件名称
     */
    public static String getFileName(String downloadUrl) {
        if (TextUtils.isEmpty(downloadUrl)) {
            return null;
        }
        int index = downloadUrl.lastIndexOf(63);
        int index2 = downloadUrl.lastIndexOf("/");
        return index > 0 && index2 >= index ? UUID.randomUUID().toString() : downloadUrl.substring(index2 + 1, index < 0 ? downloadUrl.length() : index);

    }

    /**
     * 获取本地缓存路径
     *
     * @return 缓存路径
     */
    public static String getCacheDir() {
        String saveDir = "";
        File file = JcFramework.getInstance().getApplication().getExternalFilesDir(null);
        if (file != null) {
            saveDir = file.getPath() + File.separator + "download";
        } else {
            saveDir = JcFramework.getInstance().getApplication().getCacheDir() + File.separator + "download";
        }
        if (TextUtils.isEmpty(saveDir)) {
            saveDir = Environment.getExternalStorageDirectory().getPath() + File.separator + "download";
        }
        return saveDir;
    }

    /**
     * 根据下载链接获取本地保存路径
     *
     * @param downloadUrl
     *         下载链接
     *
     * @return 本地缓存路径
     */
    public static String getSavePath(String downloadUrl) {
        return getCacheDir() + File.separator + FileUtils.getFileName(downloadUrl);
    }
}
