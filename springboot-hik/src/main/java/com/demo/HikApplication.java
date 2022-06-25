package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * java -Dfile.encoding=utf-8 -Dloader.path=lib -jar app.jar --spring.profiles.active=test
 * @author shenguangyang
 * @date 2021-12-31 20:59
 */
@SpringBootApplication
public class HikApplication {
    public static void main(String[] args) {
        SpringApplication.run(HikApplication.class, args);
    }
}
