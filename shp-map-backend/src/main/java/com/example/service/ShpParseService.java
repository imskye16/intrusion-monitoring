package com.example.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShpParseService {

    private static final int HEADER_LENGTH = 100;
    private static final int RECORD_HEADER_LENGTH = 8;

    // 返回类型改为Map<String, Object>（Spring自动转JSON）
    public Map<String, Object> parseShpToGeoJson(String shpPath) throws IOException {
        File shpFile = new File(shpPath);
        if (!shpFile.exists()) {
            throw new RuntimeException("SHP文件不存在：" + shpPath);
        }

        try (FileInputStream fis = new FileInputStream(shpFile)) {
            // 1. 读取文件头
            byte[] headerBytes = new byte[HEADER_LENGTH];
            fis.read(headerBytes);
            ByteBuffer headerBuffer = ByteBuffer.wrap(headerBytes).order(ByteOrder.BIG_ENDIAN);

            // 2. 获取图形类型
            int shapeType = headerBuffer.getInt(32);
            String geoType = getGeoType(shapeType);

            // 3. 读取所有图形数据
            List<Map<String, Object>> features = new ArrayList<>();
            byte[] recordHeader = new byte[RECORD_HEADER_LENGTH];
            while (fis.read(recordHeader) != -1) {
                ByteBuffer recordBuffer = ByteBuffer.wrap(recordHeader).order(ByteOrder.BIG_ENDIAN);
                int recordLength = recordBuffer.getInt(4) * 2;

                // 读取图形数据
                byte[] shapeData = new byte[recordLength];
                fis.read(shapeData);
                ByteBuffer shapeBuffer = ByteBuffer.wrap(shapeData).order(ByteOrder.LITTLE_ENDIAN);

                // 解析图形数据
                Map<String, Object> geometry = parseGeometry(shapeBuffer, shapeType);
                if (geometry != null) {
                    Map<String, Object> feature = new HashMap<>();
                    feature.put("type", "Feature");
                    feature.put("geometry", geometry);
                    feature.put("properties", new HashMap<>());
                    features.add(feature);
                }
            }

            // 组装GeoJSON Map（直接返回，Spring自动转JSON）
            Map<String, Object> geoJson = new HashMap<>();
            geoJson.put("type", "FeatureCollection");
            geoJson.put("features", features);
            return geoJson;
        }
    }

    // 原getGeoType方法不变
    private String getGeoType(int shapeType) {
        return switch (shapeType) {
            case 1 -> "Point";
            case 3 -> "LineString";
            case 5 -> "Polygon";
            default -> "Unknown";
        };
    }

    // 原parseGeometry方法不变（但确保坐标是数字类型，不是字符串）
    private Map<String, Object> parseGeometry(ByteBuffer buffer, int shapeType) {
        Map<String, Object> geometry = new HashMap<>();
        int type = buffer.getInt(0);
        geometry.put("type", getGeoType(type));

        try {
            switch (type) {
                case 1: // 点
                    double x = buffer.getDouble(4);
                    double y = buffer.getDouble(12);
                    List<Double> coordinates = new ArrayList<>();
                    coordinates.add(x);
                    coordinates.add(y);
                    geometry.put("coordinates", coordinates);
                    break;
                case 3: // 线
                case 5: // 面
                    // 1. 读取边界框（不需要使用，但必须跳过）
                    double minX = buffer.getDouble(4);
                    double minY = buffer.getDouble(12);
                    double maxX = buffer.getDouble(20);
                    double maxY = buffer.getDouble(28);
                    // 2. 读取parts数量和points数量
                    int numParts = buffer.getInt(36);
                    int numPoints = buffer.getInt(40);

                    // 3. 读取parts数组（关键：必须跳过这部分才能读到正确的points）
                    int[] parts = new int[numParts];
                    for (int i = 0; i < numParts; i++) {
                        parts[i] = buffer.getInt(44 + i * 4); // parts数组从44字节开始
                    }

                    // 4. 计算points数组的起始偏移量（44 + parts数组长度）
                    int pointsStartOffset = 44 + numParts * 4;

                    // 5. 读取所有点坐标
                    List<List<Double>> pointList = new ArrayList<>();
                    for (int i = 0; i < numPoints; i++) {
                        // 每个点占16字节（2个double）
                        double px = buffer.getDouble(pointsStartOffset + i * 16);
                        double py = buffer.getDouble(pointsStartOffset + i * 16 + 8);
                        List<Double> point = new ArrayList<>();
                        point.add(px);
                        point.add(py);
                        pointList.add(point);
                    }

                    // 6. 组装线/面的坐标（线是一维列表，面是二维列表）
                    if (type == 3) {
                        geometry.put("coordinates", pointList);
                    } else {
                        List<List<List<Double>>> polygon = new ArrayList<>();
                        polygon.add(pointList);
                        geometry.put("coordinates", polygon);
                    }
                    break;
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return geometry;
    }

    // 删除原来的toJsonString方法（不需要手动拼接了）
}