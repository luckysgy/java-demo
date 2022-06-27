package com.demo.leetcode;

import cn.hutool.core.lang.Console;

/**
 * 通过位运算查找奇数
 * 给定一个数组: 只有1个数是奇数个, 其余都是偶数个, 查找该数, 要求时间复杂度O(N)
 * 给定一个数组: 只有2个数是奇数个, 其余都是偶数个, 查找这两个数(这连个数一定不同), 要求时间复杂度O(N)
 * @author shenguangyang
 * @date 2022-06-27 6:12
 */
public class FindOddNum {
    public static void main(String[] args) {
        int[] arr1 = new int[] { 1, 2, 3, 1, 6, 6, 2, 8, 8, 10, 12, 10, 12};
        sort1(arr1); // 3

        int[] arr2 = new int[] { 1, 2, 3, 1, 6, 6, 2, 8, 8, 10, 12, 10, 12, 33};
        sort2(arr2); //
    }

    public static int[] genData(int maxSize, int maxValue) {
        int[] out = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < out.length; i++) {
            out[i] = (int) ((maxValue + 1) * Math.random());
        }
        return out;
    }

    /**
     * 给定一个数组: 只有1个数是奇数个, 其余都是偶数个, 查找该数, 要求时间复杂度O(N)
     */
    public static void sort1(int[] in) {
        int eor = 0;
        for (int j : in) {
            eor ^= j;
        }

        System.out.println("find num is " + eor);
    }

    /**
     * 给定一个数组: 只有2个数是奇数个, 其余都是偶数个, 查找这两个数, 要求时间复杂度O(N)
     * @param in
     */
    public static void sort2(int[] in) {
        int eor = 0;
        for (int j : in) {
            eor ^= j;
        }
        // 假设这两个数是a, b, 则 eor = a ^ b ！= 0;, a 和 b肯定有1bit, 一个是1, 另一个是0, 假设是第8bit
        // 将所有数划分两个类别, 8bit = 1 and 8bit = 0
        // 这样的话将所有的8bit = 1的数进行异或, 肯定会得到 a / b (部分 8bit = 1 的偶数个的数字相互异或, 会变成 0, 相当于没了)

        // 取最右侧bit = 1, bitEqual1 = 0000 0000 1000 (示例)
        int bitEqual1 = eor & (~eor + 1);
        int eor2 = 0;
        for (int j : in) {
            // 这块必须等于0 或者 > 0, 才能证明j = a / b
            if ((j & bitEqual1) > 0) {
                eor2 ^= j;
            }
        }

        Console.log("find num1 is {}, num2 is {}", eor2, eor2 ^ eor);
    }
}
