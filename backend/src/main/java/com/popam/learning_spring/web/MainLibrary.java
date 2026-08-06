package com.popam.learning_spring.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
public class MainLibrary implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MainLibrary.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
