package com.demo.spring_aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author shenguangyang
 * @date 2022-06-22 20:50
 */
@Aspect
@Component
public class MyAspect {
    @Before("execution(public void com.concise.demo.spring_aop.CalcServiceImpl.*(..))")
    public void beforeNotify() {
        System.out.println("********@Before我是前置通知");
    }

    @After("execution(public void com.concise.demo.spring_aop.CalcServiceImpl.*(..)))")
    public void afterNotify() {
        System.out.println("********@After我是后置通知");
    }

    @AfterThrowing("execution(public void com.concise.demo.spring_aop.CalcServiceImpl.*(..)))")
    public void afterThrowingNotify() {
        System.out.println("********@AfterThrowing我是异常通知");
    }

    @AfterReturning("execution(public void com.concise.demo.spring_aop.CalcServiceImpl.*(..))")
    public void afterReturningNotify() {
        System.out.println("********@AfterReturning我是返回后通知");
    }

    @Around("execution(public void com.concise.demo.spring_aop.CalcServiceImpl.*(..)))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object retvalue = null;
        System.out.println("我是环绕通知之前AAA");
        retvalue = proceedingJoinPoint.proceed();
        System.out.println("我是环绕通知之后BBB");
        return retvalue ;
    }
}
