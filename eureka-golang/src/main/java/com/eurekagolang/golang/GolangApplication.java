package com.eurekagolang.golang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

@SpringBootApplication
@EnableSidecar
public class GolangApplication {

    public static void main(String[] args) {
        SpringApplication.run(GolangApplication.class, args);
    }

}
