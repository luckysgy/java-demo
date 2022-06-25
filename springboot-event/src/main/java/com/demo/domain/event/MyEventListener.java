package com.demo.domain.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author shenguangyang
 * @date 2022-01-01 17:31
 */
@Component
public class MyEventListener {
    private static final Logger log = LoggerFactory.getLogger(MyEventListener.class);

    @EventListener(classes = MyEvent.class)
    public void listener(MyEvent myEvent) {
        log.info(myEvent.getEventData().toString());
    }

    @EventListener(classes = PlayStopEvent.class)
    public void listener(PlayStopEvent event) {
        log.info(event.getEventData().toString());
    }
}
