package com.demo;

import com.concise.component.datasource.mybatisplus.register.EnableMybatisPlus;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shenguangyang
 * @date 2022-02-01 19:52
 */
@EnableMybatisPlus
@SpringBootApplication
@MapperScan(basePackages = {
        "com.demo.mapper",
        "com.demo.mysql_big_data"
})
public class DemoDatasourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoDatasourceApplication.class, args);
    }
}
