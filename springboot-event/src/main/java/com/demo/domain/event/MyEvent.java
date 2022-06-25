package com.demo.domain.event;

import com.concise.component.core.event.BaseEvent;

/**
 * 事件类需要继承org.springframework.context.ApplicationEvent，这样发布的事件才能被Spring所识别
 * @author shenguangyang
 * @date 2022-01-01 17:29
 */
public class MyEvent extends BaseEvent<MyEventMessage> {

    public MyEvent(MyEventMessage myEventMessage) {
        super(myEventMessage);
    }

    public MyEvent(Object source, MyEventMessage eventData) {
        super(source, eventData);
    }
}
