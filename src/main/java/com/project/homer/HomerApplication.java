package com.project.homer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping; // New import
import org.springframework.web.bind.annotation.RestController; // New impor

@RestController 
@SpringBootApplication
public class HomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomerApplication.class, args);
	}

	// New method to handle web requests
    @GetMapping("/") // Maps HTTP GET requests to the root URL ("/")
    public String hello() {
        return "Hello, hehe :)";
    }
}
