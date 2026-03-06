package org.kfe.api.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PosApplication {

	public static void main(String[] args) {

        SpringApplication.run(PosApplication.class, args);
       // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      //  System.out.println(encoder.encode("123456789"));
	}

}
