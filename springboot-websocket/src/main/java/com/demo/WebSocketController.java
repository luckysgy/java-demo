package com.demo;

import com.concise.component.core.utils.id.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author shenguangyang
 * @date 2021-12-12 21:22
 */
@RestController
@RequestMapping("/websocket")
public class WebSocketController {
    private static final Logger log = LoggerFactory.getLogger(WebSocketController.class);

    @GetMapping("/sendMessage/{userId}/{message}")
    public String sendMessage(@PathVariable(value = "userId") String userId, @PathVariable(value = "message") String message) throws IOException {
        WebSocketServer.sendInfo(UUIDUtil.randomUUID(), userId);
        return "ok";
    }
}
