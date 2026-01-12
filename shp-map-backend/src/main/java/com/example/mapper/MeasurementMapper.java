package com.example.mapper;

import com.example.entity.Measurement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MeasurementMapper {

    /**
     * 根据站点名称查询监测数据
     */
    @Select("""
        SELECT
            "id"                AS id,
            "station_name"      AS stationName,
            "measurement_date"  AS collectTime,
            "conductivity"      AS monitorValue
        FROM SEAWATER_INVASION."MEASUREMENTS"
        WHERE "station_name" = #{stationName}
        ORDER BY "measurement_date" DESC
    """)
    List<Measurement> selectByStationName(@Param("stationName") String stationName);

    /**
     * 查询全部监测数据
     */
    @Select("""
        SELECT
            "id"                AS id,
            "station_name"      AS stationName,
            "measurement_date"  AS collectTime,
            "conductivity"      AS monitorValue
        FROM SEAWATER_INVASION."MEASUREMENTS"
        ORDER BY "measurement_date" DESC
    """)
    List<Measurement> selectAll();
}
