package com.falcao.cordstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CordStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CordStoreApplication.class, args);
    }

}
