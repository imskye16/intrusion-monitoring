package com.example.service;

import com.example.entity.Measurement;
import com.example.mapper.MeasurementMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService {
    private final MeasurementMapper measurementMapper;

    public MeasurementService(MeasurementMapper measurementMapper) {
        this.measurementMapper = measurementMapper;
    }

    public List<Measurement> getMeasurementsByStationName(String stationName) {
        return measurementMapper.selectByStationName(stationName);
    }

    public List<Measurement> getAllMeasurements() {
        return measurementMapper.selectAll();
    }
}
