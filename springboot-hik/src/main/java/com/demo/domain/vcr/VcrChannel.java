package com.demo.domain.vcr;

import com.concise.component.core.exception.Assert;

/**
 * @author shenguangyang
 * @date 2022-01-03 19:59
 */
public class VcrChannel {
    /**
     * 通道号
     */
    private final Integer channel;

    public VcrChannel(Integer channel) {
        Assert.isTrue(channel > 0, "channel > 0");
        this.channel = channel;
    }

    public Integer getChannel() {
        return channel;
    }
}
