package com.demo.application;

import com.demo.domain.vcr.VcrDownload;
import com.demo.domain.vcr.VcrLogin;
import com.demo.domain.vcr.VcrPlayStart;
import com.demo.sdk.HCNetSDK;

/**
 * @author shenguangyang
 * @date 2021-12-04 8:49
 */
public interface Vcr {
    /**
     * 登录
     * @param vcrLogin 登录信息
     * @return true登录成功
     */
    boolean login(VcrLogin vcrLogin);

    /**
     * 退出设备
     * @return true退出成功
     */
    boolean logout();

    /**
     * 下载视频
     * @param vcrDownload 下载信息
     * @return true下载成功
     */
    boolean download(VcrDownload vcrDownload);

    /**
     * 启动回放, 注意startPlay所在线程不能中断或退出, 否则sdk回调将会中断
     * @param vcrPlayStart 播放信息
     * @param fPlayDataCallBack 回调函数
     * @return 数据已经存放在 vcrPlayStart 对象中了
     */
    void playStart(VcrPlayStart vcrPlayStart,
                   HCNetSDK.FPlayDataCallBack fPlayDataCallBack);

    /**
     * 停止回放
     * @param streamId 流id
     */
    boolean playStop(String streamId);
}
