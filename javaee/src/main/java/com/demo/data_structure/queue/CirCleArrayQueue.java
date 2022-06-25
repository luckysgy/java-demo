package com.demo.data_structure.queue;

import java.util.Scanner;

/**
 * 使用数组模拟一个环形队列
 * @author shenguangyang
 * @date 2022-02-27 9:54
 */
public class CirCleArrayQueue {
    public static void main(String[] args) {
        boolean loop = true;
        System.out.println("a [add], 添加数据");
        System.out.println("g [get], 获取数据");
        System.out.println("sa [showAll], 展示全部数据");
        System.out.println("sh [showHeader], 展示头部数据");
        System.out.println("e [exit], 退出");
        CirCleArrayQueue queue = new CirCleArrayQueue(4);
        Scanner scanner = new Scanner(System.in);
        while (loop) {
            String key = scanner.nextLine();
            switch(key) {
                case "a":
                    try {
                        System.out.print("请输入一个数字: ");
                        int data = scanner.nextInt();
                        queue.add(data);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case "g":
                    try {
                        System.out.println(queue.get());
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case "sa":
                    try {
                        queue.showAll();
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case "sh":
                    try {
                        queue.showHeader();
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case "e":
                    loop = false;
                    break;
                default:
                    break;
            }
        }

        System.out.println("程序退出了");
    }

    private final int[] arr;
    /**
     * front 调整后的含义为： 指向队列头（即：队列中的第一个数据），front的初始值为 0
     */
    private int front;
    /**
     * rear 调整后的含义为：指向队列中的最后一个数据的前一个位置 ，rear 的初始值为0
     */
    private int real;
    /**
     * maxsize 表示队列的最大容量，队列中的数据是存放在数组中的
     */
    private final int maxSize;

    public CirCleArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        this.arr = new int[maxSize];
        this.front = 0;
        this.real = 0;
    }

    /**
     * 判断队列是否已满
     */
    public boolean isFull() {
        return (this.real + 1) % maxSize == this.front;
    }

    /**
     * 判断队列是否为空
     */
    public boolean isEmpty() {
        return this.real == this.front;
    }

    public void add(int data) {
        if (this.isFull()) {
            throw new RuntimeException("队列已经满了");
        }
        this.arr[this.real] = data;
        this.real = (this.real + 1) % this.maxSize;
    }

    public int get() {
        if (this.isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        // 第一步 先把queueArr[front]对应的保存在一个临时变量中
        // 为什么要将queueArr[front]对应的保存在一个临时变量中? 因为 若直接返回的话,就没有往后移的机会了
        int value = this.arr[this.front];
        this.front = (this.front + 1) % this.maxSize;
        return value;
    }

    public void showAll() {
        if (this.isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        for (int i = front; i < front + size(); i++) {
            System.out.printf("\tarr[%d] = %d\n", i % this.maxSize, this.arr[i % this.maxSize]);
        }
    }

    public void showHeader() {
        if (this.isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        System.out.printf("\tarr[%d] = %d\n", this.front, this.arr[this.front]);
    }

    public int size() {
        return (this.real + this.maxSize - this.front) % this.maxSize;
    }
}
