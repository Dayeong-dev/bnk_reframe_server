package com.example.reframe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BnkProject1Application {

	public static void main(String[] args) {
		SpringApplication.run(BnkProject1Application.class, args);
	}

}
