package com.tonyleisurecentre.bookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tonyleisurecentre.bookingsystem")
public class BookingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingsystemApplication.class, args);
	}

}
