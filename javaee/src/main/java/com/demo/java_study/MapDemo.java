package com.demo.java_study;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author shenguangyang
 * @date 2022-05-13 20:43
 */
public class MapDemo {
    private ConcurrentMap<String, String> concurrentMap;

    public MapDemo() {
        concurrentMap = new ConcurrentHashMap<>();
    }

    @Test
    public void test1() {
        concurrentMap.merge("key1","value1", (key, value) -> {
            System.out.println(key + "\t" + value);
            return key + "_" + value;
        });
    }
}
