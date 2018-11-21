package org.n26.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * This is the entry point of the application.
 * 
 * @author manjit kumar
 *
 */
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
@ComponentScan
public class ChallengeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeServiceApplication.class, args);
	}

}
