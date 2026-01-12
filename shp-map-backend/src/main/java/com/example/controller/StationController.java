package com.example.controller;

import com.example.entity.Station;
import com.example.service.StationService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 标记这是REST接口控制器
@RestController
// 允许跨域（前端Vue和后端端口不同，必须加，否则前端调不了接口）
@CrossOrigin
// 接口统一前缀（比如所有站点接口都以/api/stations开头）
@RequestMapping("/api/stations")
public class StationController {
    // 注入Service
    private final StationService stationService;

    // 构造方法注入
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    // 核心接口：GET请求，获取所有有效站点（前端调用这个接口）
    @GetMapping
    public List<Station> getValidStations() {
        // 调用Service的方法，返回有效站点列表（自动转成JSON给前端）
        return stationService.getAllValidStations();
    }
}