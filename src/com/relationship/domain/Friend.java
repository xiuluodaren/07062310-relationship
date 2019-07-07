package com.relationship.domain;

import java.io.Serializable;

/**
 * 作者:修罗大人<br>
 * 时间:2019-05-26 22:19<br>
 * 描述:<br>
 */
public class Friend implements Serializable {

    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机
     */
    private String mobilePhone;

    /**
     * QQ
     */
    private String QQ;

    /**
     * 微信
     */
    private String wx;

    public Friend() {}

    public Friend(Long id, String name, String sex, String mobilePhone, String QQ, String wx) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.mobilePhone = mobilePhone;
        this.QQ = QQ;
        this.wx = wx;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }
}
