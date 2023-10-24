package com.company.libmanagementbook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan("com.company.libmanagementbook.mapper")
@EnableTransactionManagement
public class LibmanagementBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibmanagementBookApplication.class, args);
    }

}
