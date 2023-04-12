package io.cyberflux.common.utils;

public final class CyberObjectUtils {
    public static <T> void requireNonNullElse(T object, T substitute) {
        if(object == null) object = substitute;
    }

	@SafeVarargs
	public static <T> T returnNonNullUntil(T... objects) {
		for(T object : objects) {
			if(object != null)
				return object;
		}
		return null;
	}

	public static <T> T returnNonNullUntil(Iterable<T> objects) {
		for (T object : objects) {
			if (object != null)
				return object;
		}
		return null;
	}
}
