package io.cyberflux.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class ObjectUtils {

	private final static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.findAndRegisterModules();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }
    public static <T> void requireNonNullElse(T object, T substitute) {
        if(object == null) object = substitute;
    }

	@SafeVarargs
	public static <T> T returnNonNullUntil(T... objects) {
		for(T object : objects) {
			if(object != null) return object;
		}
		return null;
	}

	public static <T> T returnNonNullUntil(Iterable<T> objects) {
		for (T object : objects) {
			if (object != null) return object;
		}
		return null;
	}

	public static <T> T convertValue(Object object, Class<T> clasz) {
		return mapper.convertValue(object, clasz);
	}

	public static <T> T convertValue(Object object, TypeReference<T> reference) {
		return mapper.convertValue(object, reference);
	}
}
