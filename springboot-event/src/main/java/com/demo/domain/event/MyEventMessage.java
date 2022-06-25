package com.demo.domain.event;

/**
 * 用来保存你要发布事件的内容
 * @author shenguangyang
 * @date 2022-01-01 17:27
 */
public class MyEventMessage {
    private String eventName;
    private String taskContext;
    private boolean finish;

    @Override
    public String toString() {
        return "MyEventMessage{" +
                "eventName='" + eventName + '\'' +
                ", taskContext='" + taskContext + '\'' +
                ", finish=" + finish +
                '}';
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getTaskContext() {
        return taskContext;
    }

    public void setTaskContext(String taskContext) {
        this.taskContext = taskContext;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
}
