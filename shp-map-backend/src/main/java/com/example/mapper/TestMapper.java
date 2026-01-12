package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestMapper {

    @Select("SELECT 1 FROM DUAL")
    Integer test();

    // 临时接口：列出当前用户下的表名
    @Select("SELECT table_name FROM user_tables")
    List<String> listUserTables();
}
