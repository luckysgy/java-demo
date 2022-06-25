package com.demo;

/**
 * @author shenguangyang
 * @date 2022-01-06 20:35
 */
public class Target1 {
    private Integer id;
    private String name;

    public Target1(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
