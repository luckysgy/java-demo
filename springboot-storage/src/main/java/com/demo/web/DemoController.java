package com.demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shenguangyang
 * @date 2022-02-04 22:28
 */
@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping()
    public void test(@RequestParam(value = "p1") String p1, @RequestParam(value = "p2") String p2) {
        System.out.println(p1 + "\t" + p2);
    }
}
