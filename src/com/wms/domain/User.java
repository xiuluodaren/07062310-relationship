package com.wms.domain;

import java.io.Serializable;

/**
 * 作者:修罗大人<br>
 * 时间:2019-05-26 22:19<br>
 * 描述:<br>
 */
public class User implements Serializable {

    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户类型 0管理员 1客户
     */
    private int type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return userName;
    }
}
