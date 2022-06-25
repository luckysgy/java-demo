package com.demo.close_java_handler;

public class JobThread extends Thread {

    int count = 0;

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Work Thread: " + count++);
        }
    }
}