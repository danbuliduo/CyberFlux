package io.cyberflux.utils.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public final class YamlLoaderUtils {
    private final static Logger log = LoggerFactory.getLogger(YamlLoaderUtils.class);
    private final static YAMLMapper mapper = new YAMLMapper();

    static {
        mapper.findAndRegisterModules();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    public static String toYamlString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
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
}
