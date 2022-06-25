package com.demo;

/**
 * @author shenguangyang
 * @date 2022-01-06 20:35
 */
public class Target2 {
    private Integer id;
    private Long age;
    private String username;
    private String name;
    private String data;

    public Target2(Integer id, Long age, String username, String name, String data) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.data = data;
        this.age = age;
    }

    public Target2() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
