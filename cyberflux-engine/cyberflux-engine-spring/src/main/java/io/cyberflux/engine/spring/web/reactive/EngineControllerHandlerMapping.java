package io.cyberflux.engine.spring.web.reactive;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.result.condition.PatternsRequestCondition;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
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

    private RequestMappingInfo withPrefix(RequestMappingInfo mapping) {
		if (!StringUtils.hasText(contextPath)) {
			return mapping;
		}
		PatternsRequestCondition patternsCondition = new PatternsRequestCondition(
			withNewPatterns(mapping.getPatternsCondition().getPatterns())
        );
        return mapping.mutate()
            .customCondition(patternsCondition)
            .customCondition(mapping.getMethodsCondition())
            .customCondition(mapping.getParamsCondition())
            .customCondition(mapping.getHeadersCondition())
            .customCondition(mapping.getConsumesCondition())
            .customCondition(mapping.getProducesCondition())
            .customCondition(mapping.getCustomCondition())
            .build();
	}

	private List<PathPattern> withNewPatterns(Set<PathPattern> patterns) {
		return patterns.stream()
			.map(pattern -> getPathPatternParser().parse(PathUtils.normalizePath(contextPath + pattern)))
			.collect(Collectors.toList());
	}
}
