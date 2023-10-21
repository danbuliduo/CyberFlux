package io.cyberflux.examples.node1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.cyberflux.engine.spring.config.EnableCyberFluxEngine;

@SpringBootApplication
@EnableCyberFluxEngine
public class CyberFluxNodeApplication {
	public static void main(String[] args) {
		SpringApplication.run(CyberFluxNodeApplication.class, args);
	}
}
