package com.jietu.admin;

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
public class LaundryAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaundryAdminApplication.class, args);
    }

}

