package com.demo;

public class UserDto2 {
    private Integer id;
    private String name;
    private Integer age;
    private Sex3Enum sex3Enum;

    public UserDto2(Integer id, String name, Integer age, Sex3Enum sex3Enum) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex3Enum = sex3Enum;
    }


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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Sex3Enum getSex3Enum() {
        return sex3Enum;
    }

    public void setSex3Enum(Sex3Enum sex3Enum) {
        this.sex3Enum = sex3Enum;
    }
}