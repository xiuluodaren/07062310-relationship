package com.relationship.domain;

import com.relationship.util.DateUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 作者: 修罗大人<br>
 * 时间: 2019-07-07 19:43<br>
 * 邮箱: 1448564807@qq.com<br>
 * 描述: 活动实体<br>
 */
public class Active {

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
     * 发起方
     */
    private String initiator;

    /**
     * 活动地址
     */
    private String address;

    /**
     * 活动总结
     */
    private String summary;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     *参与者列表,逗号分隔
     */
    private String participant;

    public String getStartTimeStr()
    {
        return DateUtil.getDateTimeAsString(this.startTime,"yyyy-MM-dd HH:mm:ss");
    }

    public String getEndTimeStr()
    {
        return DateUtil.getDateTimeAsString(this.endTime,"yyyy-MM-dd HH:mm:ss");
    }

    public String getCreateTimeStr()
    {
        return DateUtil.getDateTimeAsString(this.createTime,"yyyy-MM-dd HH:mm:ss");
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
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

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
