package com.demo;

import com.demo.close_java_handler.CloseJavaHandlerDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shenguangyang
 * @date 2022-04-27 21:29
 */
@SpringBootApplication()
public class DemoApplication {
    public static void main(String[] args) throws Exception {
        // TestJarUtils.copyDir();

//        String[] cmd = {"/bin/bash", "-c", "sh build.sh && cd ../cpp-base && sh build.sh"};
////        String[] cmd = {"cmd.exe", "/c", "ping www.baidu.com"};
//        CommandExecutor commandExecutor = new CommandExecutor();
//        Map<String, String> environmentVariables = System.getenv();
//        // commandExecutor.executeCommand(command, new File("/mnt/project/javacpp-native/cpp-project"), environmentVariables);
//        CommandExecutor.Result result = commandExecutor.executeCommandAndReturnResult(cmd, new File("/mnt/project/javacpp-native/cpp-project"), environmentVariables);
//        if (result.isSuccess()) {
//            log.info("exec success: \n{}", result.getSuccessResult());
//        } else {
//            log.error("exec fail: \n{}", result.getErrorResult());
//        }
//
//        if (result.hasWarn()){
//            log.warn("exec warn: \n{}", result.getWarnResult());
//        }
////        if (commandExecutor.executeCommand(cmd, new File("/code/demo"), environmentVariables)) {
////            System.out.println("exec success");
////        }

        CloseJavaHandlerDemo.exec();
        SpringApplication.run(DemoApplication.class, args);
    }
}
