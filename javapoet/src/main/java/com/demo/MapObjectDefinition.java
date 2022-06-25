package com.demo;

import java.util.List;

/**
 * 映射对象定义
 * @author shenguangyang
 * @date 2022-01-03 9:31
 */
public class MapObjectDefinition {
    /**
     * 方法参数的类
     */
    private List<Class<?>> methodParameterTypes;
    /**
     * 方法返回的类
     */
    private Class<?> methodReturnType;

    /**
     * 方法返回类的构造器的参数类
     */
    private List<Class<?>> methodReturnConstructorParamTypes;

    /**
     * 方法名称
     */
    private String methodName;

    public MapObjectDefinition(List<Class<?>> methodParameterTypes, Class<?> methodReturnType,
                               List<Class<?>> methodReturnConstructorParamTypes,
                               String methodName) {
        this.methodParameterTypes = methodParameterTypes;
        this.methodReturnType = methodReturnType;
        this.methodReturnConstructorParamTypes = methodReturnConstructorParamTypes;
        this.methodName = methodName;
    }
}
