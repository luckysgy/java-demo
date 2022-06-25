package com.demo;

/**
 * @author shenguangyang
 * @date 2021-12-31 7:53
 */
public interface BizService {
    default void say(WebSocketServer socketServer, String streamId) {}
}
