package io.cyberflux.node.engine.core.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.cyberflux.node.engine.core.handler.http.HttpMethods;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestRouter {
	String path() default "";
	HttpMethods method() default HttpMethods.GET;
}
