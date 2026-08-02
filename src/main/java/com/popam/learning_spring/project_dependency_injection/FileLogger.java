package com.popam.learning_spring.project_dependency_injection;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Component
@Profile("prod")
public class FileLogger implements Logger {
    @Override
    public void log(String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("payment.txt", true))) {
            bw.write(message);
            bw.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
