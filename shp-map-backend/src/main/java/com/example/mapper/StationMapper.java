package com.example.mapper;

import com.example.entity.Station;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StationMapper {

    @Select("SELECT \"id\" AS id, \"region\" AS region, \"station_name\" AS stationName, \"longitude\" AS longitude, \"latitude\" AS latitude, \"build_date\" AS buildDate, \"station_type\" AS stationType FROM SEAWATER_INVASION.\"STATIONS\"")
    List<Station> selectAll();
}
