package io.cyberflux.gateway.app;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.cyberflux.gateway.app.annotation.EnableCyberFluxGateway;


@SpringBootApplication
@EnableCyberFluxGateway
public class CyberFluxGatewayApplication {

	public static void main(String[] args) {
    	SpringApplication.run(CyberFluxGatewayApplication.class, args);

	}
}