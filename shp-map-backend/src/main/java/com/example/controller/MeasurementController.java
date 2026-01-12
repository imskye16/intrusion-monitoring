package com.example.controller;

import com.example.entity.Measurement;
import com.example.service.MeasurementService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin // 允许前端跨域调用
@RequestMapping("/api/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;

    // 构造方法注入（Spring自动装配）
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    // 关键接口：根据站点名查询数据（前端点击站点时调用）
    @GetMapping("/{stationName}")
    public List<Measurement> getByStationName(@PathVariable String stationName) {
        try {
            // 1. 调用Service获取数据库数据
            List<Measurement> measurements = measurementService.getMeasurementsByStationName(stationName);
            // 2. 补充：确保monitorItem固定为“电导率”（数据库无该列，强制赋值）
            measurements.forEach(measurement -> measurement.setMonitorItem("电导率"));
            return measurements;
        } catch (Exception e) {
            // 打印错误日志，方便排查（后端控制台可见）
            System.err.println("查询站点[" + stationName + "]数据失败：" + e.getMessage());
            e.printStackTrace();
            throw e; // 抛出异常，让前端提示用户
        }
    }

    // 接口：获取所有监测数据（前端“表格形式展示”时调用）
    @GetMapping
    public List<Measurement> getAllMeasurements() {
        List<Measurement> allData = measurementService.getAllMeasurements();
        // 同样补充monitorItem字段
        allData.forEach(data -> data.setMonitorItem("电导率"));
        return allData;
    }
}