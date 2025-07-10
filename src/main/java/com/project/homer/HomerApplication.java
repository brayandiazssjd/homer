package com.project.homer;


import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RestController;
import com.project.homer.model.DBConnection; 


@RestController 
@SpringBootApplication
public class HomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomerApplication.class, args);
	}

	// New method to handle web requests
    @GetMapping("/") // Maps HTTP GET requests to the root URL ("/")
    public String hello() {
        String test = "failed";
        Connection c = null;
        try {
            c = DBConnection.getInstance(DBConnection.ROLE);
            test = c.getSchema();
        } catch (SQLException sqle) {
            System.out.println(sqle); 
        }
        System.out.println(test);
        return "Success:" ;
    }
}
