package com.example.entity;

import java.time.LocalDate;

public class Station {
    private Integer id;
    private String region;
    private String stationName;
    private Double longitude;
    private Double latitude;
    private LocalDate buildDate;
    private String stationType;

    public Station() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public LocalDate getBuildDate() { return buildDate; }
    public void setBuildDate(LocalDate buildDate) { this.buildDate = buildDate; }

    public String getStationType() { return stationType; }
    public void setStationType(String stationType) { this.stationType = stationType; }
}
