package com.demo.domain.vcr;

import com.alibaba.fastjson.annotation.JSONField;
import com.concise.component.core.exception.Assert;
import com.demo.domain.common.VcrCommon;
import com.demo.sdk.HCNetSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 回放信息
 * @author shenguangyang
 * @date 2021-12-04 16:07
 */
public class VcrPlayStart implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(VcrPlayStart.class);
    /**
     * streamId 播放流id, 需要用户自己随机定义一个字符串作为播放流的标识, eg: uuid
     */
    private String streamId;
    /**
     * 通道号
     */
    private VcrChannel channel;

    /**
     * 下载视频的起始时间戳, 实际下载的视频是精确到s的
     */
    private Long startTimestamp;

    /**
     * 下载视频的结束时间戳, 实际下载的视频是精确到s的
     */
    private Long endTimestamp;

    /**
     * 回放句柄
     */
    private Integer playHandle;

    @JSONField(serialize = false)
    private HCNetSDK.NET_DVR_TIME dvrStartTime;

    @JSONField(serialize = false)
    private HCNetSDK.NET_DVR_TIME dvrEndTime;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 存放回放数据的队列
     */
    private final BlockingQueue<byte[]> playDataQueue = new ArrayBlockingQueue<>(10000);
    /**
     * 管理所有回放对象
     */
    private static final Map<String, VcrPlayStart> vcrPlayStartMap = new ConcurrentHashMap<>();

    /**
     * push 数据的时间戳
     */
    private Long pushDataTimestamp;
    private Long createVcrPlayStartTimestamp;

    /**
     * 播放是否运行中
     */
    private volatile boolean running = true;

    public static VcrPlayStart create(String streamId, VcrChannel channel, Long startTimestamp, Long endTimestamp) {
        VcrPlayStart vcrPlayStart = new VcrPlayStart(streamId, channel, startTimestamp, endTimestamp);
        vcrPlayStartMap.put(streamId, vcrPlayStart);

        return vcrPlayStart;
    }

    public static VcrPlayStart create(String streamId, VcrChannel channel, String startTimeStr, String endTimeStr) throws ParseException {
        VcrPlayStart vcrPlayStart = new VcrPlayStart(streamId, channel, startTimeStr, endTimeStr);
        vcrPlayStartMap.put(streamId, vcrPlayStart);
        return vcrPlayStart;
    }

    private VcrPlayStart(String streamId, VcrChannel channel, Long startTimestamp, Long endTimestamp) {
        Assert.isTrue(startTimestamp != null && startTimestamp > 0 && endTimestamp != null && endTimestamp > 0,
                "startTimestamp != null && startTimestamp > 0 && endTimestamp != null && endTimestamp > 0");
        Assert.isTrue(endTimestamp > startTimestamp  , "endTimestamp > startTimestamp");
        Assert.notNull(streamId, "streamId not null");
        this.streamId = streamId;
        this.channel = channel;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        dvrStartTime = VcrCommon.getDvrTime(startTimestamp);
        dvrEndTime = VcrCommon.getDvrTime(endTimestamp);
        this.createVcrPlayStartTimestamp = System.currentTimeMillis();
    }

    /**
     * @param startTimeStr 起始时间, 格式yyyy-MM-dd HH:mm:ss
     * @param endTimeStr 结束时间, 格式yyyy-MM-dd HH:mm:ss
     */
    private VcrPlayStart(String streamId, VcrChannel channel, String startTimeStr, String endTimeStr) throws ParseException {
        Assert.notEmpty(startTimeStr, "startTimeStr not empty");
        Assert.notEmpty(endTimeStr, "endTimeStr not empty");
        long startTime = sdf.parse(startTimeStr).getTime();
        long endTime = sdf.parse(endTimeStr).getTime();

        this.streamId = streamId;
        this.channel = channel;
        this.startTimestamp = startTime;
        this.endTimestamp = endTime;

        Assert.isTrue(endTimestamp > startTimestamp  , "endTimestamp > startTimestamp");
        Assert.notNull(streamId, "streamId not null");

        dvrStartTime = VcrCommon.getDvrTime(startTimestamp);
        dvrEndTime = VcrCommon.getDvrTime(endTimestamp);
        this.createVcrPlayStartTimestamp = System.currentTimeMillis();
    }

    public boolean isRunning() {
        if (!running) {
            return false;
        }
        // 实际相差太大, 认为已经停止 push 数据了
        Long currentTime = System.currentTimeMillis();
        if (pushDataTimestamp == null) {
            if (Math.abs(currentTime - createVcrPlayStartTimestamp) > 1000 * 3) {
                running = false;
                log.warn("长时间没有收到回调数据, 放弃本次播放");
            }
            return running;
        }
        if (Math.abs(pushDataTimestamp - currentTime) > (1000 * 3)) {
            running = false;
        }
        return running;
    }

    /**
     * 停止当前流回放
     */
    public void stop() {
        this.running = false;
        vcrPlayStartMap.remove(this.streamId);
    }

    /**
     * 停止指定流回放
     * @param streamId 流id
     * @return false 已停止, true 停止成功
     */
    public static boolean stop(String streamId) {
        VcrPlayStart vcrPlayStart = vcrPlayStartMap.get(streamId);
        if (vcrPlayStart == null) {
            return false;
        }
        vcrPlayStart.stop();
        return true;
    }

    public void pushPlayDataQueue(byte[] bytes) {
        this.pushDataTimestamp = System.currentTimeMillis();
        if (!playDataQueue.offer(bytes)) {
            log.error("回放数据添加到队列中失败, queue size: {}", playDataQueue.size());
        }
    }

    /**
     * 获取数据, 不要使用 {@link #isRunning()} 判断是否运行, 因为这个是针对 回调播放是否运行中
     * 由于数据存在队列中, 即使播放结束, 队列中依然会有一些数据
     * @return 返回null 一定没有数据, 非null 有数据
     */
    public byte[] pollPlayDataQueue() {
        // 如果两次没有获取到数据就认为已经没有回放数据了
        int count = 2;
        while (count-- > 0) {
            try {
                byte[] poll = playDataQueue.poll(2, TimeUnit.SECONDS);
                if (poll != null) {
                    return poll;
                }
            } catch (InterruptedException e) {
                log.error("error: ", e);
                return null;
            }
        }
        return null;
    }

    /**
     * 创建回放句柄
     */
    public void createPlayHandle(int playHandle) {
        this.playHandle = playHandle;
    }


    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public VcrChannel getChannel() {
        return channel;
    }

    public void setChannel(VcrChannel channel) {
        this.channel = channel;
    }

    public Long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public Integer getPlayHandle() {
        return playHandle;
    }

    public void setPlayHandle(Integer playHandle) {
        this.playHandle = playHandle;
    }

    public HCNetSDK.NET_DVR_TIME getDvrStartTime() {
        return dvrStartTime;
    }

    public void setDvrStartTime(HCNetSDK.NET_DVR_TIME dvrStartTime) {
        this.dvrStartTime = dvrStartTime;
    }

    public HCNetSDK.NET_DVR_TIME getDvrEndTime() {
        return dvrEndTime;
    }

    public void setDvrEndTime(HCNetSDK.NET_DVR_TIME dvrEndTime) {
        this.dvrEndTime = dvrEndTime;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public BlockingQueue<byte[]> getPlayDataQueue() {
        return playDataQueue;
    }

    public Long getPushDataTimestamp() {
        return pushDataTimestamp;
    }

    public void setPushDataTimestamp(Long pushDataTimestamp) {
        this.pushDataTimestamp = pushDataTimestamp;
    }

    public Long getCreateVcrPlayStartTimestamp() {
        return createVcrPlayStartTimestamp;
    }

    public void setCreateVcrPlayStartTimestamp(Long createVcrPlayStartTimestamp) {
        this.createVcrPlayStartTimestamp = createVcrPlayStartTimestamp;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
