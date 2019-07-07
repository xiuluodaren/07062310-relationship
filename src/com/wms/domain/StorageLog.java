package com.wms.domain;

import com.wms.util.DateUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StorageLog {
    private Long id;

    private String number;

    private Long commodityId;

    private String commodityName;

    private String name;

    private Integer storagetype;

    private BigDecimal beforestorage;

    private BigDecimal changestoreage;

    private BigDecimal currentstorage;

    private BigDecimal price;

    private Long customerid;

    private String customername;

    private String warehouse;

    private LocalDateTime createtime;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStoragetype() {
        return storagetype;
    }

    public void setStoragetype(Integer storagetype) {
        this.storagetype = storagetype;
    }

    public BigDecimal getBeforestorage() {
        return beforestorage;
    }

    public void setBeforestorage(BigDecimal beforestorage) {
        this.beforestorage = beforestorage;
    }

    public BigDecimal getChangestoreage() {
        return changestoreage;
    }

    public void setChangestoreage(BigDecimal changestoreage) {
        this.changestoreage = changestoreage;
    }

    public BigDecimal getCurrentstorage() {
        return currentstorage;
    }

    public void setCurrentstorage(BigDecimal currentstorage) {
        this.currentstorage = currentstorage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public String getCreatetimeStr() {
        return DateUtil.getDateTimeAsString(this.createtime,"yyyy-MM-dd HH:mm:ss");
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public StorageLog() {
        super();
    }

    public StorageLog(Long id, String number, Long commodityId,String commodityName, String name, Integer storagetype, BigDecimal beforestorage, BigDecimal changestoreage, BigDecimal currentstorage, BigDecimal price, Long customerid, String customername, String warehouse, LocalDateTime createtime) {
        this.id = id;
        this.number = number;
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.name = name;
        this.storagetype = storagetype;
        this.beforestorage = beforestorage;
        this.changestoreage = changestoreage;
        this.currentstorage = currentstorage;
        this.price = price;
        this.customerid = customerid;
        this.customername = customername;
        this.warehouse = warehouse;
        this.createtime = createtime;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }
}