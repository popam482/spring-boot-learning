package com.popam.learning_spring.project_dependency_injection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DecimalFormat;

@Configuration
public class AppConfig {

    @Value("${number.format}")
    private String numberFormat;

    @Bean
    public DecimalFormat numberFormater() {
        return new DecimalFormat(numberFormat);
    }
}
