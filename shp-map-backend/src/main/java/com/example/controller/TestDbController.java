package com.example.controller;

import com.example.mapper.TestMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestDbController {

    private final TestMapper testMapper;

    public TestDbController(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    @GetMapping("/api/test-db")
    public String testDb() {
        return "DB OK: " + testMapper.test();
    }

    // 新增调试：列出当前 schema 下的表
    @GetMapping("/api/test-tables")
    public List<String> listTables() {
        return testMapper.listUserTables();
    }
}
