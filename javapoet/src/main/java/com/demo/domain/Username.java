package com.demo.domain;

import com.demo.MapperFiled;

/**
 * @author shenguangyang
 * @date 2022-01-02 22:22
 */
public class Username {
    @MapperFiled(String.class)
    private String username;

    public Username(String username) {
        System.out.println(username);
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
