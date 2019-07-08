package com.relationship.domain;

import com.relationship.util.DateUtil;

import java.time.LocalDateTime;

/**
 * 作者: 修罗大人<br>
 * 时间: 2019-07-07 19:43<br>
 * 邮箱: 1448564807@qq.com<br>
 * 描述: 合作实体<br>
 */
public class Cooperation {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 详情
     */
    private String detail;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 合作人姓名
     */
    private String cooperationerName;

    /**
     * 合作状态  0进行中 1已结束
     */
    private String status;

    public String getStatusStr()
    {
        if ("0".equals(status))
        {
            return "进行中";
        }else{
            return "已结束";
        }
    }

    public String getStartTimeStr()
    {
        return DateUtil.getDateTimeAsString(this.startTime,"yyyy-MM-dd HH:mm:ss");
    }

    public String getEndTimeStr()
    {
        return DateUtil.getDateTimeAsString(this.endTime,"yyyy-MM-dd HH:mm:ss");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getCooperationerName() {
        return cooperationerName;
    }

    public void setCooperationerName(String cooperationerName) {
        this.cooperationerName = cooperationerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
