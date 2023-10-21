package io.cyberflux.engine.spring.config;

import io.cyberflux.engine.spring.web.*;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.cyberflux.engine.spring.SpringEngine;
import io.cyberflux.engine.spring.service.EngineActuatorService;
import io.cyberflux.engine.spring.web.annotation.EngineController;

@SpringBootConfiguration(proxyBeanMethods = false)
public class SpringEngineWebConfiguration {

    @SpringBootConfiguration(proxyBeanMethods = false)
    public static class EngineControllerConfiguration {
        @Bean
        @ConditionalOnMissingBean
        EngineActuatorController engineActuatorController(EngineActuatorService service) {
            return new EngineActuatorController(service);
        }

        @Bean
        @ConditionalOnMissingBean
        EngineChannelController engineChannelController() {
            return new EngineChannelController();
        }

        @Bean
        @ConditionalOnMissingBean
        EngineReactorController engineReactorController() {
            return new EngineReactorController();
        }

        @Bean
        @ConditionalOnMissingBean
        EngineSendEventController engineSendEventController(EngineActuatorService service) {
            return  new EngineSendEventController(service);
        }

        @Bean
        @ConditionalOnMissingBean
        EngineSubscriptionController engineSubscriptionController() {
            return new EngineSubscriptionController();
        }
    }

    @SpringBootConfiguration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    public static class ReactiveRestApiConfiguration implements WebFluxConfigurer {
        private final SpringEngineProperties properties;

        public ReactiveRestApiConfiguration(SpringEngineProperties properties) {
            this.properties = properties;
        }

        @Override
        public void configurePathMatching(org.springframework.web.reactive.config.PathMatchConfigurer configurer) {
            configurer.addPathPrefix(properties.getApiPath(), clasz -> clasz.isAnnotationPresent(EngineController.class));
        }


        @Bean
        @ConditionalOnMissingBean
        io.cyberflux.engine.spring.web.reactive.EngineBootstrapHandler bootstrapHandler(SpringEngine springEngine) {
            return new io.cyberflux.engine.spring.web.reactive.EngineBootstrapHandler(springEngine);
        }


        @Bean
        org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping engineHandlerMapping(
            RequestedContentTypeResolver webFluxContentTypeResolver
        ) {
            org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping mapping =
                new io.cyberflux.engine.spring.web.reactive.EngineControllerHandlerMapping(properties.getApiPath());
            mapping.setOrder(0);
            mapping.setContentTypeResolver(webFluxContentTypeResolver);
            return mapping;
        }
    }

    @SpringBootConfiguration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public static class ServletRestApiConfiguration implements WebMvcConfigurer {

        private final SpringEngineProperties properties;

        public ServletRestApiConfiguration(SpringEngineProperties properties) {
            this.properties = properties;
        }

        @Override
        public void configurePathMatch(org.springframework.web.servlet.config.annotation.PathMatchConfigurer configurer) {
            configurer.addPathPrefix(properties.getApiPath(), clasz -> clasz.isAnnotationPresent(EngineController.class));
        }

        @Bean
        @ConditionalOnMissingBean
        io.cyberflux.engine.spring.web.servlet.EngineBootstrapHandler bootstrapHandler(SpringEngine springEngine) {
            return new io.cyberflux.engine.spring.web.servlet.EngineBootstrapHandler(springEngine);
        }


        @Bean
        org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping engineHandlerMapping(
            ContentNegotiationManager contentNegotiationManager
        ) {
            org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping mapping =
                new io.cyberflux.engine.spring.web.servlet.EngineControllerHandlerMapping(properties.getApiPath());
            mapping.setOrder(0);
            mapping.setContentNegotiationManager(contentNegotiationManager);
            return mapping;
        }
    }
}
