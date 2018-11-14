package com.android.framework.jc.data.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/11/1 14:20
 * @describe
 * @update
 */
public class DownloadBean extends RealmObject {

    /**
     * 文件下载地址
     */
    @PrimaryKey
    private String downloadUrl;
    /**
     * 下载的文件保存路径
     */
    private String savePath;
    /**
     * 文件大小
     */
    private long totalLength;
    /**
     * 已经下载的文件大小
     */
    private long downLength;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setFileUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public long getDownLength() {
        return downLength;
    }

    public void setDownLength(long downLength) {
        this.downLength = downLength;
    }
}
