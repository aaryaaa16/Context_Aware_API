package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RtspServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RtspServerApplication.class, args);
        //System.out.println("server is running");
    }
}
