package io.cyberflux.common.utils;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CyberConfigLoaderUtils {
    private final static Logger log = LoggerFactory.getLogger(CyberConfigLoaderUtils.class);

    public static String XML_FORMAT = ".xml";
    public static String YML_FORMAT = ".yml";
    public static String YAML_FORMAT = ".yaml";
    public static String JSON_FORMAT = ".json";
    public static String CONF_FORMAT = ".conf";
    public static String PROPERTIES_FORMAT = ".properties";


    public static <T> T fromResource(String path, Class<T> clasz) {
        try {
            if (path.endsWith(YML_FORMAT) || path.endsWith(YAML_FORMAT)) {
                return CyberYamlUtils.toObject(clasz.getResourceAsStream(path), clasz);
            } else if (path.endsWith(JSON_FORMAT)) {
                return CyberJsonUtils.toObject(clasz.getResourceAsStream(path), clasz);
            } else if (path.endsWith(PROPERTIES_FORMAT)) {
                return CyberPropsUtils.toObject(clasz.getResourceAsStream(path), clasz);
            } else if (path.endsWith(XML_FORMAT)) {
                return CyberXmlUtils.toObject(clasz.getResourceAsStream(path), clasz);
            }
            throw new IllegalArgumentException("This file format is not supported.");
        } catch (IllegalArgumentException e) {
            log.error("{}", e);
        }
        return null;
    }

    public static <T> T fromFile(String path, Class<T> clasz) {
        try {
            if (path.endsWith(YML_FORMAT) || path.endsWith(YAML_FORMAT)) {
                return CyberYamlUtils.toObject(new File(path), clasz);
            } else if (path.endsWith(JSON_FORMAT)) {
                return CyberJsonUtils.toObject(new File(path), clasz);
            } else if (path.endsWith(PROPERTIES_FORMAT)) {
                return CyberPropsUtils.toObject(new File(path), clasz);
            } else if (path.endsWith(XML_FORMAT)) {
                return CyberXmlUtils.toObject(new File(path), clasz);
            };
            throw new IllegalArgumentException("This file format is not supported.");
        } catch (IllegalArgumentException e) {
            log.error("{}", e);
        }
        return null;
    }

    public static <T> T auto(String path, Class<T> clasz) {
        if(path.charAt(0) == '/') {
            return CyberConfigLoaderUtils.fromResource(path, clasz);
        }
        return CyberConfigLoaderUtils.fromFile(path, clasz);
    }
}
