package com.challenge.bittracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.challenge.bittracker.repository") 
@EntityScan("com.challenge.bittracker.domain")
@SpringBootApplication
public class BitTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitTrackerApplication.class, args);
	}

}
