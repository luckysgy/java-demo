package com.demo;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

/**
 * @author shenguangyang
 * @date 2022-01-03 9:33
 */
class MapObjectDefinitionTestJavaPost {
    @Test
    public void methodParamAndReturnClass() {
        try {
            Class<?> clazz = TestInfer.class;
            //获取本类的所有方法，存放入数组
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println("方法名："+method.getName());
                //获取本方法所有参数类型，存入数组
                Class<?>[] getTypeParameters = method.getParameterTypes();
                if(getTypeParameters.length==0){
                    System.out.println("此方法无参数");
                }
                for (Class<?> class1 : getTypeParameters) {
                    String parameterName = class1.getName();
                    System.out.println("参数类型："+parameterName);
                }

                Class<?> returnType = method.getReturnType();
                System.out.println("返回参数型: " + returnType.getName());
                System.out.println("****************************");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}