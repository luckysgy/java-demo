package com.demo;

public enum Sex3Enum {

    //保密
    SECRECY3( "保密"),
    //男
    MAN3( "男"),
    //女
    WOMAN3("女");

    private String desc;

    Sex3Enum(String desc) {
        this.desc = desc;
    }


    public String getDesc() {
        return desc;
    }
}