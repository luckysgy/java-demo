package com.demo.domian.mediaplay;

import java.io.Serializable;

/**
 * 媒体dto
 * @author shenguangyang
 * @date 2021-10-30 9:39
 */
public class MediaInfo implements Serializable {
    private static final long serialVersionUID = -5575352151805386129L;
    /**
     * 拉取的rtsp、rtmp、d:/flv/test.mp4、desktop
     */
    private String pullUrl;

    /**
     * 推送的url, rtmp
     */
    private String pushUrl;

    /**
     * md5 key，媒体标识，区分不同媒体
     */
    private String mediaKey;
    /**
     * 0网络流，1本地视频
     */
    private int type = 0;

    /**
     * 网络超时 ffmpeg默认5秒，这里设置15秒
     */
    private String netTimeout = "15000000";

    /**
     * 读写超时，默认5秒
     */
    private String readOrWriteTimeout = "15000000";


    public String getPullUrl() {
        return pullUrl;
    }

    public void setPullUrl(String pullUrl) {
        this.pullUrl = pullUrl;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getMediaKey() {
        return mediaKey;
    }

    public void setMediaKey(String mediaKey) {
        this.mediaKey = mediaKey;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNetTimeout() {
        return netTimeout;
    }

    public void setNetTimeout(String netTimeout) {
        this.netTimeout = netTimeout;
    }

    public String getReadOrWriteTimeout() {
        return readOrWriteTimeout;
    }

    public void setReadOrWriteTimeout(String readOrWriteTimeout) {
        this.readOrWriteTimeout = readOrWriteTimeout;
    }
}
