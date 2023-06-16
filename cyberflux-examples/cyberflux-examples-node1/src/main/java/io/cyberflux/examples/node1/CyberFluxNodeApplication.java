package io.cyberflux.examples.node1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import io.cyberflux.spring.engine.EnableCyberFluxNodeEngine;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCyberFluxNodeEngine
public class CyberFluxNodeApplication {
	public static void main(String[] args) {
		SpringApplication.run(CyberFluxNodeApplication.class, args);
	}
}
