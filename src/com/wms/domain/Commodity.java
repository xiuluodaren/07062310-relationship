package com.wms.domain;

import java.math.BigDecimal;

public class Commodity {
    private Long id;

    private String name;

    private BigDecimal storage;

    private BigDecimal price;

    private Long customerid;

    private String customername;

    private String warehouse;

    private String number;

    public Commodity()
    {
        super();
    }

    public Commodity(Long id, String number, String name, BigDecimal storage, BigDecimal price, Long customerid, String customername, String warehouse) {
        this.id = id;
        this.name = name;
        this.storage = storage;
        this.price = price;
        this.customerid = customerid;
        this.customername = customername;
        this.warehouse = warehouse;
        this.number = number;
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

    public BigDecimal getStorage() {
        return storage;
    }

    public void setStorage(BigDecimal storage) {
        this.storage = storage;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return name;
    }
}
