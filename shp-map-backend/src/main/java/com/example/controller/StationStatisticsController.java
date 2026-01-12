package com.example.controller;

import com.example.dto.StationBuildDateDTO;
import com.example.entity.Station;
import com.example.service.StationService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/stations/statistics")
public class StationStatisticsController {
    private final StationService stationService;

    public StationStatisticsController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/build-date")
    public List<StationBuildDateDTO> getStationBuildDates() {
        List<Station> all = stationService.getAllStations();
        return all.stream()
                .map(station -> new StationBuildDateDTO(
                        station.getId(),
                        station.getBuildDate() != null ? station.getBuildDate().toString() : null
                ))
                .collect(Collectors.toList());
    }
}
