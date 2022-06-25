package com.demo.language_vs;

import org.junit.jupiter.api.Test;

/**
 * @author shenguangyang
 * @date 2022-04-28 20:03
 */
public class VsGo {
    static class E {
        int a;

        E(int a) {
            this.a = a;
        }
    }

    /**
     * vs 频繁内存分配/垃圾回收
     * go代码
     * package main
     *
     * type E struct {
     * 	a int32
     * }
     *
     * func main() {
     * 	const ARRAY_COUNT int64 = 10000
     * 	const TEST_COUNT int64 = ARRAY_COUNT * 100000
     *
     * 	var es [ARRAY_COUNT]*E
     * 	for i := int64(0); i < TEST_COUNT; i++ {
     * 		es[i*123456789%ARRAY_COUNT] = &E{a: int32(i)}
     *        }
     *
     * 	n := int64(0)
     * 	for i := int64(0); i < ARRAY_COUNT; i++ {
     * 		e := es[i]
     * 		if e != nil {
     * 			n += int64(e.a)
     *        }
     *    }
     * 	println(n)
     * }
     */
    @Test
    public void test1() {
        long t1 = System.currentTimeMillis();
        final long ARRAY_COUNT = 10000L;
        final long TEST_COUNT = ARRAY_COUNT * 10_0000L;

        E[] es = new E[(int)ARRAY_COUNT];
        for(long i = 0; i < TEST_COUNT; i++)
            es[(int)(i * 123456789L % ARRAY_COUNT)] = new E((int)i);

        long n = 0;
        for(long i = 0; i < ARRAY_COUNT; i++) {
            E e = es[(int)i];
            if(e != null)
                n += e.a;
        }
        System.out.println(n);
        long t2 = System.currentTimeMillis();
        System.out.println("time: " + (t2 - t1) + " ms");
    }
}
