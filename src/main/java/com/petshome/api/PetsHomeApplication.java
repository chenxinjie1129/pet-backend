package com.petshome.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 萌宠之家应用启动类
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.petshome.api.repository")
public class PetsHomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetsHomeApplication.class, args);
    }
} 