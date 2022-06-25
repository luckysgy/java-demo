package com.demo;

import java.lang.annotation.*;

/**
 * 所有的消息监听者都需要实现该接口
 * @author shenguangyang
 * @date 2021-12-24 8:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface MapperFiled {
    Class<?>[] value();
}
