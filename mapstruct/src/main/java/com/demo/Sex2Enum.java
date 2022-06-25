package com.demo;

public enum Sex2Enum {
    //保密
    SECRECY( "保密"),
    //男
    MAN( "男"),
    //女
    WOMAN("女");

    private String desc;

    Sex2Enum(String desc) {
        this.desc = desc;
    }


    public String getDesc() {
        return desc;
    }
}