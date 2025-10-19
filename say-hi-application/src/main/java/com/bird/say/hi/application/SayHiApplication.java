package com.bird.say.hi.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.bird.say.hi"
})
public class SayHiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SayHiApplication.class, args);
	}

}
