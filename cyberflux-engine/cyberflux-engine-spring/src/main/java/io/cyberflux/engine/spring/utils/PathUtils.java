package io.cyberflux.engine.spring.utils;

import org.springframework.util.StringUtils;

public final class PathUtils {

    private PathUtils() {}

    public static String normalizePath(String path) {
        if (!StringUtils.hasText(path)) {
            return path;
        }
        String normalizedPath = path;
        if (!normalizedPath.startsWith("/")) {
            normalizedPath = "/" + normalizedPath;
        }
        if (normalizedPath.endsWith("/")) {
            normalizedPath = normalizedPath.substring(0, normalizedPath.length() - 1);
        }
        if (normalizedPath.startsWith("//")) {
            normalizedPath = normalizedPath.substring(1);
        }
        return normalizedPath;
    }
}
