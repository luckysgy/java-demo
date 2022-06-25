package com.demo.domain.mediaplay;

/**
 * @author shenguangyang
 * @date 2021-12-04 17:14
 */
public class MediaTransferInfo {
    /**
     * streamId 播放流id, 需要用户自己随机定义一个字符串作为播放流的标识, eg: uuid
     */
    private String streamId;

    /**
     * 推送的url, rtmp
     * flv访问链接http://local.work01.com:38080/live?port=11935&app=video&stream=1
     */
    private final String pushUrl;

    /**
     * 网络超时 ffmpeg默认5秒，这里设置15秒
     */
    private String netTimeout = "15000000";

    /**
     * 读写超时，默认5秒
     */
    private String readOrWriteTimeout = "15000000";

    public MediaTransferInfo(String streamId, String pushUrl) {
        this.pushUrl = pushUrl;
        this.streamId = streamId;
    }

    public MediaTransferInfo(String streamId, String pushUrl, String netTimeout, String readOrWriteTimeout) {
        this.streamId =streamId;
        this.pushUrl = pushUrl;
        this.netTimeout = netTimeout;
        this.readOrWriteTimeout = readOrWriteTimeout;
    }

    @Override
    public String toString() {
        return "MediaTransferInfo{" +
                "streamId='" + streamId + '\'' +
                ", pushUrl='" + pushUrl + '\'' +
                ", netTimeout='" + netTimeout + '\'' +
                ", readOrWriteTimeout='" + readOrWriteTimeout + '\'' +
                '}';
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getPushUrl() {
        return pushUrl;
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
