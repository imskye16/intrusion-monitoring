package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器，提供基础接口用于测试
 */
@RestController
public class HelloController {

    /**
     * 根路径接口，返回固定字符串
     * 
     * @return "Hello World!"
     */
    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }

    /**
     * 计算接口，接收两个整数参数并返回计算结果
     * 
     * @param left  第一个整数
     * @param right 第二个整数
     * @return 包含输入参数和求和结果的CalcResult对象
     */
    @GetMapping("/calc")
    public CalcResult calc(@RequestParam int left, @RequestParam int right) {
        int answer = left + right; // 实现简单的加法逻辑
        return new CalcResult(left, right, answer);
    }

    /**
     * 计算结果封装类，用于接口返回JSON格式数据
     */
    public static class CalcResult {
        private final int left;
        private final int right;
        private final int answer;

        public CalcResult(int left, int right, int answer) {
            this.left = left;
            this.right = right;
            this.answer = answer;
        }

        // Getter方法用于JSON序列化
        public int getLeft() {
            return left;
        }

        public int getRight() {
            return right;
        }

        public int getAnswer() {
            return answer;
        }
    }
}