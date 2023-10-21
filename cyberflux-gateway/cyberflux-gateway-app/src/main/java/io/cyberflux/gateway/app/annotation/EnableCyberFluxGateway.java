package io.cyberflux.gateway.app.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import io.cyberflux.engine.spring.config.EnableCyberFluxEngine;
import io.cyberflux.gateway.app.context.GatewayAutoConfiguration;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(GatewayAutoConfiguration.class)
@EnableAsync
@EnableAutoConfiguration
@EnableCyberFluxEngine
public @interface EnableCyberFluxGateway {

}
