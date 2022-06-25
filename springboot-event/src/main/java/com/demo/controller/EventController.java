package com.demo.controller;

import com.concise.component.core.utils.id.UUIDUtil;
import com.demo.domain.event.MyEvent;
import com.demo.domain.event.MyEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shenguangyang
 * @date 2022-01-01 17:48
 */
@RestController
public class EventController {
    private static final Logger log = LoggerFactory.getLogger(EventController.class);
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @GetMapping("/event")
    public void event() {
        MyEventMessage myEventMessage = new MyEventMessage();
        myEventMessage.setEventName("test");
        myEventMessage.setTaskContext(UUIDUtil.randomUUID());
        MyEvent myEvent = new MyEvent(myEventMessage);
        eventPublisher.publishEvent(myEvent);
    }
}
