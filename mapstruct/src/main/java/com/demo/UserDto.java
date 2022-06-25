package com.demo;

public class UserDto {
    private Integer id;
    private String name;
    private Integer age;
    private Sex2Enum sex2Enum;


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

    public Sex2Enum getSex2Enum() {
        return sex2Enum;
    }

    public void setSex2Enum(Sex2Enum sex2Enum) {
        this.sex2Enum = sex2Enum;
    }
}