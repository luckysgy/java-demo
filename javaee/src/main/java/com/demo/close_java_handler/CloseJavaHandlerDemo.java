package com.demo.close_java_handler;

public class CloseJavaHandlerDemo {
    public static void exec() {
        ExitHandler.init();
        JobThread jobThread = new JobThread();
        jobThread.start();
    }
}
