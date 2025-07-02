package com.project.homer;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping; // New import
import org.springframework.web.bind.annotation.RestController; // New impor

import com.project.homer.model.Area;
import com.project.homer.model.Service;

@RestController 
@SpringBootApplication
public class HomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomerApplication.class, args);
	}

	// New method to handle web requests
    @GetMapping("/") // Maps HTTP GET requests to the root URL ("/")
    public ArrayList<Object> hello() {
        Area a = new Area("a",1,"a");
        Service s = new Service();
        s.setName("my service");
        ArrayList<Object> l = new ArrayList<>();
        l.add(a);
        l.add(s);
        return l;
    }
}
