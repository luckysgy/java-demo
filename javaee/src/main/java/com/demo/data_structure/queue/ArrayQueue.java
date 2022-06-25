package com.demo.data_structure.queue;

import java.util.Scanner;

/**
 * 使用数组模拟一个队列
 * @author shenguangyang
 * @date 2022-02-27 9:54
 */
public class ArrayQueue {
    public static void main(String[] args) {
        boolean loop = true;
        System.out.println("a [add], 添加数据");
        System.out.println("g [get], 获取数据");
        System.out.println("sa [showAll], 展示全部数据");
        System.out.println("sh [showHeader], 展示头部数据");
        System.out.println("e [exit], 退出");
        ArrayQueue queue = new ArrayQueue(4);
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
     * 数组队列头部
     */
    private int front;
    /**
     * 数组队列尾部
     */
    private int real;
    /**
     * 表示数组队列的最大容量
     */
    private final int maxSize;

    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        this.arr = new int[maxSize];
        // 指向队列头部，front是指向队列头部数据的前一个位置
        this.front = -1;
        // 指向队列尾部，rear是指向队列尾部数据
        this.real = -1;
    }

    /**
     * 判断队列是否已满
     * @return
     */
    public boolean isFull() {
        // rear队列尾部数据==最大容量，说明队列已满
        return this.real == this.maxSize - 1;
    }

    /**
     * 判断队列是否为空
     * @return
     */
    public boolean isEmpty() {
        // 队列头部指针==队列尾部指针，说明队列为空
        return this.real == this.front;
    }

    public void add(int data) {
        if (this.isFull()) {
            throw new RuntimeException("队列已经满了");
        }
        // 队列尾部指针向后移
        this.real = this.real + 1;
        this.arr[this.real] = data;
    }

    public int get() {
        if (this.isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        // 队列头部指针向后移动
        this.front = this.front + 1;
        return this.arr[this.front];
    }

    public void showAll() {
        if (this.isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        for (int i = 0; i < this.arr.length; i++) {
            System.out.printf("\tarr[%d] = %d\n", i, this.arr[i]);
        }
    }

    public void showHeader() {
        if (this.isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        System.out.printf("\tarr[%d] = %d\n", this.front + 1, this.arr[this.front + 1]);
    }
}
