package com.demo.spring_aop;

import org.springframework.stereotype.Service;

/**
 * @author shenguangyang
 * @date 2022-06-22 20:46
 */
@Service
public class CalcServiceImpl implements CalcService {
    @Override
    public void div(int x, int y) {
        int result = x / y;
        System.out.println("===>CalcServiceImpl被调用，计算结果为：" + result);
    }
}
