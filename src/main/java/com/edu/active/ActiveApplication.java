package com.edu.active;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ActiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActiveApplication.class, args);
	}
}
