package io.cyberflux.examples.node1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.cyberflux.spring.engine.EnableCyberFluxNodeEngine;

@EnableCyberFluxNodeEngine
@SpringBootApplication
public class CyberFluxNodeApplication {
	public static void main(String[] args) {
		SpringApplication.run(CyberFluxNodeApplication.class, args);
	}

}
