package com.demo;

public class User {
    private Integer id;
    private String name;
    private Integer myage;
    private SexEnum sexEnum;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMyage() {
        return myage;
    }

    public void setMyage(Integer myage) {
        this.myage = myage;
    }

    public SexEnum getSexEnum() {
        return sexEnum;
    }

    public void setSexEnum(SexEnum sexEnum) {
        this.sexEnum = sexEnum;
    }
}