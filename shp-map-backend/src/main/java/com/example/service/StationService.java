package com.example.service;

import com.example.entity.Station;
import com.example.mapper.StationMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {
    private final StationMapper stationMapper;

    public StationService(StationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }

    public List<Station> getAllValidStations() {
        // 这里示例直接返回所有站点
        return stationMapper.selectAll();
    }

    public List<Station> getAllStations() {
        return stationMapper.selectAll();
    }
}
