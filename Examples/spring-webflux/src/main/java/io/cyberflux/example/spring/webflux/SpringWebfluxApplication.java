package io.cyberflux.example.spring.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.cyberflux.spring.engine.EnableCyberFluxNodeEngine;


@EnableCyberFluxNodeEngine
@SpringBootApplication
public class SpringWebfluxApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringWebfluxApplication.class, args);
	}
}
