package net.johanbasson.neo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NeoApplication {

	public static void main(String[] args) {
		System.setProperty("org.jooq.no-logo", "true");
		SpringApplication.run(NeoApplication.class, args);
	}

}
