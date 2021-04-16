package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com")
@EnableJpaRepositories(basePackages="com")
@EntityScan(basePackages="com")
public class MicroServiceDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceDbApplication.class, args);
	}

}
