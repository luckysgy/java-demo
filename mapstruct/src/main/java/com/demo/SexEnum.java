package com.demo;

public enum SexEnum {

    //保密
    SECRECY( "保密"),
    //男
    MAN( "男"),
    //女
    WOMAN("女");

    private String desc;

    SexEnum(String desc) {
        this.desc = desc;
    }

    public static SexEnum get(String sex) {
        if (SexEnum.MAN.getDesc().equals(sex)) {
            return SexEnum.MAN;
        } else if (SexEnum.WOMAN.getDesc().equals(sex)) {
            return SexEnum.WOMAN;
        }
        return SexEnum.SECRECY;
    }


    public String getDesc() {
        return desc;
    }
}