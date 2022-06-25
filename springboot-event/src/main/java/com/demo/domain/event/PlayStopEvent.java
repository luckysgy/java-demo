package com.demo.domain.event;

import com.concise.component.core.event.BaseEvent;

/**
 * @author shenguangyang
 * @date 2022-01-01 18:30
 */
public class PlayStopEvent extends BaseEvent<PlayStopEventMessage> {
    public PlayStopEvent(Object source, PlayStopEventMessage eventData) {
        super(source, eventData);
    }

    public PlayStopEvent(PlayStopEventMessage eventData) {
        super(eventData);
    }
}
