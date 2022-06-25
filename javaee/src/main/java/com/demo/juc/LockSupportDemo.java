package com.demo.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 相当于等唤醒机制(wait / notify) 的加强版本
 * @author shenguangyang
 * @date 2022-06-14 21:26
 */
public class LockSupportDemo {
    public static void m1() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "1");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t" + "2");
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "1");
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName() + "\t" + "2");
        }, "t2");
        t2.start();

    }

    public static void main(String[] args) {
        m1();
    }
}
