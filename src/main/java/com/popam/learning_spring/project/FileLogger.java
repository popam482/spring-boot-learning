package com.popam.learning_spring.project;

import org.springframework.context.annotation.Profile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Profile("prod")
public class FileLogger implements Logger{
    @Override
    public void log(String message) {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("payment.txt"));
            bw.write(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
