package com.capsool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // ✅ This enables Spring Scheduler
public class CapsoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapsoolApplication.class, args);
	}

}
