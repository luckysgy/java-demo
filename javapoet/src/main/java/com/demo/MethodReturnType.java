package com.demo;

import java.util.List;

/**
 * 方法返回类型node
 * @author shenguangyang
 * @date 2022-01-03 10:13
 */
public class MethodReturnType {
    /**
     * 方法返回类的构造器的参数node
     */
    private List<ConstructorParamTypeNode> constructorParamTypeNodes;

    public MethodReturnType(List<ConstructorParamTypeNode> constructorParamTypeNodes) {
        this.constructorParamTypeNodes = constructorParamTypeNodes;
    }
}
