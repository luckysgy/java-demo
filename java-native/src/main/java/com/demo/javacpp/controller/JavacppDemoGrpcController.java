package com.demo.javacpp.controller;

import com.concise.component.core.entity.response.Response;
import com.demo.javacpp.JavacppDemoGrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author shenguangyang
 * @date 2022-05-15 18:35
 */
@RestController
@RequestMapping("/javacpp/grpc")
public class JavacppDemoGrpcController {
    @Resource
    private JavacppDemoGrpcClient javacppDemoGrpcClient;

    @GetMapping("/start")
    public Response start(@RequestParam(value = "num", defaultValue = "1") Integer num) {
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                try {
                    javacppDemoGrpcClient.findDemo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        return Response.buildSuccess();
    }
}
