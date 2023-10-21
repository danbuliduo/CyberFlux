package io.cyberflux.cluster.codec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

public class ClusterMessageMapper {

	public final static ObjectMapper INSTANCE = new ObjectMapper();

	static {
		INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		INSTANCE.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		INSTANCE.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
		INSTANCE.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		INSTANCE.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		INSTANCE.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		INSTANCE.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
		INSTANCE.activateDefaultTyping(
			LaissezFaireSubTypeValidator.instance,
			ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT,
			JsonTypeInfo.As.WRAPPER_OBJECT
		);
		INSTANCE.findAndRegisterModules();
	}

	private ClusterMessageMapper() {
        // Do not instantiate
    }
}
