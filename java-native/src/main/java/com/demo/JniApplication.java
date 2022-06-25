package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * java -Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar demo-javacpp.jar
 * @author shenguangyang
 * @date 2022-02-20 16:02
 */
@SpringBootApplication
public class JniApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(JniApplication.class);
    }
}
