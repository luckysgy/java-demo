package com.demo.domain.mediaplay;

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

    public String getPushUrl() {
        return pushUrl;
    }

    public String getMediaKey() {
        return mediaKey;
    }

    public int getType() {
        return type;
    }

    public String getNetTimeout() {
        return netTimeout;
    }

    public String getReadOrWriteTimeout() {
        return readOrWriteTimeout;
    }
}
