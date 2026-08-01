package com.popam.learning_spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LearningSpringApplication implements CommandLineRunner {

	@Autowired
	private NotificationManager notificationManager;

	@Autowired
	private Counter counter;

	public static void main(String[] args) {

		SpringApplication.run(LearningSpringApplication.class, args);
	}
	public void run(String... args) throws Exception {
		notificationManager.showIdentity();
		counter.increment();
		System.out.println("Sending notification...");
		notificationManager.notifyUser();
	}
}
