package demo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class MultiModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiModuleApplication.class, args);
	}

}
