package com.demo.application.callback;

import com.demo.domain.vcr.VcrPlayStart;
import com.demo.sdk.HCNetSDK;
import com.sun.jna.ptr.ByteByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 回放回调类
 * @author shenguangyang
 * @date 2021-12-04 16:40
 */
public class PlayCallBack implements HCNetSDK.FPlayDataCallBack {
    private static final Logger log = LoggerFactory.getLogger(PlayCallBack.class);
    private VcrPlayStart vcrPlayStart;
    public PlayCallBack(VcrPlayStart vcrPlayStart) {
        this.vcrPlayStart = vcrPlayStart;
    }

    private int count = 1;
    @Override
    public void invoke(int lPlayHandle, int dwDataType, ByteByReference pBuffer, int dwBufSize, int dwUser) {
        if (dwDataType == HCNetSDK.NET_DVR_STREAMDATA) {
            if (count++ % 100 == 0) {
                log.info("streamId: {}, playCallBackDataSize: {}", vcrPlayStart.getStreamId(), dwBufSize);
                // vcrPlayStart.pushPlayDataQueue(pBuffer.getPointer().getByteArray(0, dwBufSize));
            }
        }
    }
}
