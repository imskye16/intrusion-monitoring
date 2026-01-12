package com.example.entity;

import java.time.LocalDateTime;

public class Measurement {
    private Integer id;
    private String stationName;
    private LocalDateTime collectTime;
    // 仅用作前端显示，不映射数据库字段（MyBatis 不影响）
    private String monitorItem = "电导率";
    private Double monitorValue;

    public Measurement() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    public LocalDateTime getCollectTime() { return collectTime; }
    public void setCollectTime(LocalDateTime collectTime) { this.collectTime = collectTime; }

    public String getMonitorItem() { return monitorItem; }
    public void setMonitorItem(String monitorItem) { this.monitorItem = monitorItem; }

    public Double getMonitorValue() { return monitorValue; }
    public void setMonitorValue(Double monitorValue) { this.monitorValue = monitorValue; }
}
