package com.company.libmanagementmain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LibmanagementMainApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LibmanagementMainApplication.class, args);
    }

    private static final Logger log = LoggerFactory.getLogger(LibmanagementMainApplication.class);

    @Autowired
    private UIMonitor uiMonitor;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Welcome!");
        Scanner sc = new Scanner(System.in);
        while(true){
            try{

                uiMonitor.showHome();
            }catch (Exception ex){
                log.error(ex.getMessage());
            }
        }
    }
}
