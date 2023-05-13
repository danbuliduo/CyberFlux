package io.cyberflux.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

public final class CyberJsonUtils {
    private final static Logger log = LoggerFactory.getLogger(CyberJsonUtils.class);
    private final static JsonMapper mapper = new JsonMapper();

    static {
        mapper.findAndRegisterModules();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    public static String toJsonString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch(JsonProcessingException e) {
            log.error("{}", e);
        }
        return null;
    }

    public static <T> T toObject(String content, Class<T> clasz) {
        try {
            return mapper.readValue(content, clasz);
        } catch (JsonProcessingException e) {
            log.error("{}", e);
        }
        return null;
    }

    public static <T> T toObject(File file, Class<T> clasz) {
        try {
            return mapper.readValue(file, clasz);
        } catch (IOException e) {
            log.error("{}", e);
        }
        return null;
    }

    public static <T> T toObject(InputStream stream, Class<T> clasz) {
        try {
            return mapper.readValue(stream, clasz);
        } catch (IOException e) {
            log.error("{}", e);
        }
        return null;
    }

    public static <T> List<T> toList(String content, Class<T> clasz) {
        try {
            return mapper.readValue(content, new TypeReference<List<T>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("{}", e);
        }
        return null;
    }

    public static <K, V> Map<K, V> toMap(String content, Class<K> k, Class<V> v) {
        try {
            return mapper.readValue(content, new TypeReference<Map<K, V>>(){});
        } catch (JsonProcessingException e) {
            log.error("{}", e);
        }
        return null;
    }

    public static Object[] toArray(String content) {
        return CyberJsonUtils.toList(content, Object.class).toArray();
    }
}
