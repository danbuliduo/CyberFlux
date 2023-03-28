package io.cyberflux.common.utils;

public final class CyberObjectUtils {
    public static <T> void requireNonNull(T object, T substitute) {
        if(object == null) object = substitute;
    }
}
