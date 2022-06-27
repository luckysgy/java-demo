package com.demo.leetcode;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = new int[] { 1, 2, -1, 20, 10, 4};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] in) {
        for (int i = 0; i < in.length; i++) {
            for (int j = i; j < in.length; j++) {
                if (in[i] > in[j]) {
                    swap(in, i, j);
                }
            }
        }
    }

    /**
     * i 和 j是同一个会报错
     */
    public static void swap(int[] in, int i, int j) {
        in[i] = in[i] ^ in[j];
        in[j] = in[i] ^ in[j];
        in[i] = in[i] ^ in[j];
    }
}
