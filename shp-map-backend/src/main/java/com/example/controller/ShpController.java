package com.example.controller;

import com.example.service.ShpParseService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.io.File;
// 1. 必须导入java.util.Map！
import java.util.Map;

@RestController
@CrossOrigin
public class ShpController {

  private final ShpParseService shpParseService;

  public ShpController(ShpParseService shpParseService) {
    this.shpParseService = shpParseService;
  }

  // 2. 把返回类型从String改成Map<String, Object>！
  @GetMapping("/api/shp/geojson")
  public Map<String, Object> getShpGeoJson() throws Exception {
    String shpFileName = "ssp126_risk_2026.shp";
    URI shpUri = getClass().getClassLoader().getResource("shp/" + shpFileName).toURI();
    String shpPath = URLDecoder.decode(shpUri.getPath(), StandardCharsets.UTF_8);
    File shpFile = new File(shpPath);
    System.out.println("实际读取的SHP路径：" + shpFile.getAbsolutePath());

    // 返回Map，和方法声明的类型一致
    return shpParseService.parseShpToGeoJson(shpFile.getAbsolutePath());
  }
}