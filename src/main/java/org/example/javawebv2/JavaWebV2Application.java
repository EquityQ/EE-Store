package org.example.javawebv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaWebV2Application {

	public static void main(String[] args) {
		SpringApplication.run(JavaWebV2Application.class, args);
		System.out.println("应用启动在：http://localhost:8081");
	}

}
