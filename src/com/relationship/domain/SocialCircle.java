package com.relationship.domain;

import java.math.BigDecimal;

public class SocialCircle {
    private Long id;

    /**
     * 编号
     */
    private String number;

    private String name;

    /**
     * 负责人
     */
    private String principal;

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

    @Override
    public String toString() {
        return name;
    }

    public SocialCircle()
    {
        super();
    }

    public SocialCircle(Long id, String number, String name, String principal, String mobilePhone, String QQ, String wx) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.principal = principal;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
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
