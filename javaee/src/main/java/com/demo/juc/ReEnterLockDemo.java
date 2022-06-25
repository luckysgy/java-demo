package com.demo.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 * 可重入锁又名递归锁，是指在同一个线程在外层方法获取锁的时候，再进入该线程的的内层方法会自动获取锁
 * （前提是锁对象得是同一个对象）, 不会因为之前已经获取过还没释放而阻塞。
 *
 * Java中ReentrantLock和synchronized都是可重入锁, 可重入锁的一个优点是可一定程度避免死锁。
 * @author shenguangyang
 * @date 2022-06-14 20:43
 */
public class ReEnterLockDemo {
    static final Object lockObject = new Object();
    static final Lock lock = new ReentrantLock();
    public static void m1() throws Exception {
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("外层");
                lock.lock();
                try {
                    System.out.println("内层");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();
    }

    public static void main(String[] args) throws Exception {
        m1();
    }
}
