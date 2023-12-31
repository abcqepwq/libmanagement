package com.company.libmanagementorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan("com.company.libmanagementorder.mapper")
@EnableTransactionManagement
public class LibmanagementOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibmanagementOrderApplication.class, args);
    }

}
