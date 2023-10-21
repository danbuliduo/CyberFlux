package io.cyberflux.examples.node2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.cyberflux.engine.spring.config.EnableCyberFluxEngine;


@EnableCyberFluxEngine
@SpringBootApplication
public class CyberFluxNodeApplication {
	public static void main(String[] args) {
		SpringApplication.run(CyberFluxNodeApplication.class, args);
	}

}
