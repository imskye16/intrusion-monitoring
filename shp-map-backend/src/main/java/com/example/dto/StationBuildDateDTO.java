package com.example.dto;

// 只包含站点ID和创立时间（用于统计，无需其他字段）
public class StationBuildDateDTO {
    private Integer id;
    private String buildDate; // 后端返回字符串格式（如"2005-09-27"）

    // 构造方法
    public StationBuildDateDTO(Integer id, String buildDate) {
        this.id = id;
        this.buildDate = buildDate;
    }

    // Getter和Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getBuildDate() { return buildDate; }
    public void setBuildDate(String buildDate) { this.buildDate = buildDate; }
}