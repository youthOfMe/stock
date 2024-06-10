package com.chenhai.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.chenhai.stock.mapper")
@SpringBootApplication
public class JobApp {

    public static void main(String[] args) {
        SpringApplication.run(JobApp.class, args);
    }
}
