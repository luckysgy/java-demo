package com.demo;

import com.concise.component.core.exception.BizException;

/**
 * @author shenguangyang
 * @date 2022-01-06 21:54
 */
public class UserId {
    private Integer userId;

    public UserId(Integer id) {
        if (id == null || id == 0) {
            throw new BizException("非法userId");
        }
        this.userId = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
