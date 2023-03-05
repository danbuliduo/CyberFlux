package io.cyberflux.node.engine.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CyberComponent {
    String name() default "";
}
