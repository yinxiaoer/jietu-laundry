package com.jietu.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = "com.jietu")
@MapperScan("com.jietu.*.dao")
public class LaundryAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaundryAppApplication.class, args);
    }

}

