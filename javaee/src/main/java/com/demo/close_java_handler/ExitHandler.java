package com.demo.close_java_handler;

import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * @author shenguangyang
 * @date 2022-05-18 6:56
 */
public class ExitHandler implements SignalHandler {
    public static void init() {
        // 程序启动时实例化一个KillHandler，注册TERM信号。
        // 这样，在进程被kill的时候就会触发KillHandler的handle方法。
        ExitHandler exitHandler = new ExitHandler();
        // kill -15 pid: 正常终止
        exitHandler.registerSignal("TERM");
        // kill -2 pid: 中断（同 ctrl + c ）
        exitHandler.registerSignal("INT");
        // kill -12 pid : 用户自定义信号
        // exitHandler.registerSignal("USR2");

    }
    public void registerSignal(String signalName) {
        Signal signal = new Signal(signalName);
        Signal.handle(signal, this);
    }

    @Override
    public void handle(Signal signal) {
        System.out.println(signal.getName() + ":" + signal.getNumber());
        if (signal.getName().equals("TERM")) {
            System.out.println("1");
        } else if (signal.getName().equals("INT") || signal.getName().equals("HUP")) {
            System.out.println("2");
        } else {
            System.out.println("3");
        }
        System.exit(1);
    }
}
