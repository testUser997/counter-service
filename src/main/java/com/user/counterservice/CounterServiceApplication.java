package com.user.counterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.user.counterservice.data.repository")
@EnableRetry
public class CounterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CounterServiceApplication.class, args);
	}
}
