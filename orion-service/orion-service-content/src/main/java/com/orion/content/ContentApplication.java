package com.orion.content;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 描述
 *
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.changgou.content.dao"})
public class ContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class);
    }
}