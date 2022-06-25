package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * java -Dfile.encoding=utf-8 -Dloader.path=lib -Duser.timezone=GMT+08 -jar app.jar --spring.profiles.active=test,mq
 * @author shenguangyang
 * @date 2022-01-02 10:12
 */
@SpringBootApplication
public class PackagePomApplication {
    public static void main(String[] args) {
        SpringApplication.run(PackagePomApplication.class, args);
    }
}
