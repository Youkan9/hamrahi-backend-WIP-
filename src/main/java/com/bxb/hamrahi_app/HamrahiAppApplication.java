package com.bxb.hamrahi_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(
		exclude = UserDetailsServiceAutoConfiguration.class
)
@EnableKafka
public class HamrahiAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HamrahiAppApplication.class, args);
	}

}
