package io.cyberflux.engine.spring.web.servlet;

import java.lang.reflect.Method;
import java.util.Set;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import io.cyberflux.engine.spring.utils.PathUtils;
import io.cyberflux.engine.spring.web.annotation.EngineController;

public class EngineControllerHandlerMapping extends RequestMappingHandlerMapping {
    private final String contextPath;

    public EngineControllerHandlerMapping(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
	protected boolean isHandler(Class<?> beanType) {
		return AnnotatedElementUtils.hasAnnotation(beanType, EngineController.class);
	}

	@Override
	protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
		super.registerHandlerMethod(handler, method, withPrefix(mapping));
	}

    @SuppressWarnings("null")
    private RequestMappingInfo withPrefix(RequestMappingInfo mapping) {
		if (!StringUtils.hasText(contextPath)) {
			return mapping;
		}
		RequestMappingInfo.Builder mutate = mapping.mutate();
        return mutate.paths(withNewPatterns(mapping.getPathPatternsCondition().getPatterns())).build();
	}

    private String[] withNewPatterns(Set<PathPattern> patterns) {
		return patterns.stream()
			.map(pattern-> PathUtils.normalizePath(this.contextPath + pattern.getPatternString()))
			.toArray(String[]::new);
	}

}
