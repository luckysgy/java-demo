package com.demo.domain.model.playvideo;

import com.demo.domain.event.PlayStopEvent;
import com.demo.domain.event.PlayStopEventMessage;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author shenguangyang
 * @date 2022-01-01 18:28
 */
public class PlayVideo {
    private final ApplicationEventPublisher eventPublisher;

    public PlayVideo(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void start() {
        PlayStopEventMessage message = new PlayStopEventMessage();
        message.setMessage("播放异常");
        PlayStopEvent event = new PlayStopEvent(message);
        eventPublisher.publishEvent(event);
    }

    public ApplicationEventPublisher getEventPublisher() {
        return eventPublisher;
    }
}
