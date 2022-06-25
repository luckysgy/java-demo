package com.demo;

/**
 * @author shenguangyang
 * @date 2022-01-03 11:43
 */
public class ConstructorParam {
    /**
     * 参数类型
     */
    private Class<?> type;
    /**
     * 参数名称
     */
    private String paramName;

    public ConstructorParam(Class<?> type, String paramName) {
        this.type = type;
        this.paramName = paramName;
    }


    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
}
