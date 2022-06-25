package com.demo;

import com.demo.domain.Demo1;
import com.demo.domain.Demo2;

/**
 * @author shenguangyang
 * @date 2022-01-03 9:34
 */
public interface TestInfer {
    Demo2 toDemo2From(Demo1 from);
}
