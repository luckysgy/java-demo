package com.demo.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 * @author shenguangyang
 * @date 2022-06-14 9:13
 */
public class TwoSum {
    /**
     * 暴力破解
     */
    public static int[] exec1(int[] in, int target) {
        for (int i = 0; i < in.length; i++) {
            for (int j = i + 1; j < in.length; j++) {
                if (in[i] + in[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[]{};
    }

    /**
     * 哈希表查找
     */
    public static int[] exec2(int[] in, int target) {
        Map<Integer, Integer> cache = new HashMap<>();
        for (int i = 0; i < in.length; i++) {
            int num = target - in[i];
            Integer index = cache.get(num);
            if (index == null) {
                cache.put(in[i], i);
            } else {
                return new int[] {index, i};
            }
        }
        return new int[]{};
    }

    public static void main(String[] args) {
        int[] numArr = new int[]{11, 2, 15, 7};
        int target = 9;

        long t1 = System.currentTimeMillis();
//        System.out.println(Arrays.toString(exec1(numArr, target)));
        System.out.println(Arrays.toString(exec2(numArr, target)));
        long t2 = System.currentTimeMillis();
        System.out.println("time: " + (t2 - t1));
    }
}
