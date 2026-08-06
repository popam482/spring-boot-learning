package com.popam.learning_spring.project_web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainTask implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(MainTask.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
