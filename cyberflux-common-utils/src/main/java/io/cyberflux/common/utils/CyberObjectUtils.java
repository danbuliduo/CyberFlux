package io.cyberflux.common.utils;

public final class CyberObjectUtils {
    public static <T> void requireNonNull(T object, T substitute) {
        if(object == null) object = substitute;
    }

	@SafeVarargs
	public static <T> T returnNonNull(T... objects) {
		for(T object : objects) {
			if(object != null) return object;
		}
		return null;
	}
}
