package com.qq.common;

import java.io.Serializable;

/**
 *  用户信息类
 */
public class User implements Serializable {

    private static final long serialVersionUID = 421L;
    private String userId;
    private String passWd;

    public User() { }

    public User(String userId, String passWd) {
        this.userId = userId;
        this.passWd = passWd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassWd() {
        return passWd;
    }

    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }
}
