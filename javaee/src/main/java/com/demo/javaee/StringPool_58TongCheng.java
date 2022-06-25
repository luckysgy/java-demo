package com.demo.javaee;

/**
 * 58同城面试题
 * @author shenguangyang
 * @date 2022-06-14 8:59
 */
public class StringPool_58TongCheng {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        String str1 = new StringBuilder().append("re").append("dis").toString();
        System.out.println(str1);
        System.out.println(str1.intern());
        System.out.println(str1 == str1.intern());

        System.out.println();
        String str2 = new StringBuilder().append("ja").append("va").toString();
        System.out.println(str2);
        System.out.println(str2.intern());
        System.out.println(str2 == str2.intern());
    }
}
