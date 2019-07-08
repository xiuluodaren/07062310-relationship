package com.relationship.domain;

import com.relationship.util.DateUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 作者: 修罗大人<br>
 * 时间: 2019-07-07 19:43<br>
 * 邮箱: 1448564807@qq.com<br>
 * 描述: 礼物实体<br>
 */
public class Gift {

    private Long id;

    /**
     * 礼物名称
     */
    private String name;

    /**
     * 收送方式 0收 1送
     */
    private Integer recvOrSend;

    /**
     * 喜欢程度 0一般 1喜欢 2很喜欢
     */
    private Integer liking;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 赠送原因
     */
    private String cause;

    /**
     * 时间
     */
    private LocalDateTime time;

    /**
     * 备注
     */
    private String remark;

    public String getTimeStr()
    {
        return DateUtil.getDateTimeAsString(this.time,"yyyy-MM-dd HH:mm:ss");
    }

    public String getRecvOrSendStr()
    {
        if (recvOrSend == 0)
        {
            return "收到";
        }else{
            return "送出";
        }
    }

    public String getLikingStr()
    {
        if (liking == 0)
        {
            return "一般";
        }else if (liking == 1){
            return "喜欢";
        }else{
            return "很喜欢";
        }
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

    public Integer getRecvOrSend() {
        return recvOrSend;
    }

    public void setRecvOrSend(Integer recvOrSend) {
        this.recvOrSend = recvOrSend;
    }

    public Integer getLiking() {
        return liking;
    }

    public void setLiking(Integer liking) {
        this.liking = liking;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
