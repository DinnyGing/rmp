package com.my.mrp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MrpApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrpApplication.class, args);
    }

}
