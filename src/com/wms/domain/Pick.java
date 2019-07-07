package com.wms.domain;

import com.wms.util.DateUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 文件名称：StorageLogPane<br>
 * 初始作者：修罗大人<br>
 * 创建日期：2019-06-17 10:27<br>
 * 功能说明：提货单<br>
 */
public class Pick {
    private Long id;

    private String number;

    private BigDecimal nums;

    private BigDecimal price;

    private Long commodityId;

    private String commodityName;

    private LocalDateTime createtime;

    private Long customerid;

    private String customername;

    private Integer examineFlag;

    private String examineUser;

    private LocalDateTime examineTime;

    public Pick(Long id, String number, BigDecimal nums, BigDecimal price, Long commodityId, String commodityName, LocalDateTime createtime, Long customerid, String customername, Integer examineFlag, String examineUser, LocalDateTime examineTime) {
        this.id = id;
        this.number = number;
        this.nums = nums;
        this.price = price;
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.createtime = createtime;
        this.customerid = customerid;
        this.customername = customername;
        this.examineFlag = examineFlag;
        this.examineUser = examineUser;
        this.examineTime = examineTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getNums() {
        return nums;
    }

    public void setNums(BigDecimal nums) {
        this.nums = nums;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public Integer getExamineFlag() {
        return examineFlag;
    }

    public void setExamineFlag(Integer examineFlag) {
        this.examineFlag = examineFlag;
    }

    public String getExamineUser() {
        return examineUser;
    }

    public void setExamineUser(String examineUser) {
        this.examineUser = examineUser;
    }

    public LocalDateTime getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(LocalDateTime examineTime) {
        this.examineTime = examineTime;
    }

    public String getCreatetimeStr() {
        return DateUtil.getDateTimeAsString(this.createtime,"yyyy-MM-dd HH:mm:ss");
    }

    public String getExamineTimeStr() {
        return DateUtil.getDateTimeAsString(this.examineTime,"yyyy-MM-dd HH:mm:ss");
    }

    public String getExamineFlagStr()
    {
        return examineFlag == 1 ? "已批准" : "未批准";
    }

}
