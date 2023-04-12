package io.cyberflux.reactor.mqtt.utils;

public final class MqttTopicRegexUtils {

	public static String regexTopic(String topic) {
		if(topic.charAt(0) == '$') {topic = '\\' + topic;}
		return topic.replaceAll("/", "\\\\/").replaceAll("\\+", "[^/]+").replaceAll("#", "(.+)") + '$';
	}

	public static boolean containWildcard(String topic) {
		return topic.contains("+") || topic.contains("#");
	}
}
