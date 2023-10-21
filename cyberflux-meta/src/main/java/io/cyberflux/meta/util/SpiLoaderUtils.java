package io.cyberflux.meta.util;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class SpiLoaderUtils {

	private SpiLoaderUtils() {}

	public static <T> Optional<T> findFirst(Class<T> clazz) {
		ServiceLoader<T> load = ServiceLoader.load(clazz);
		return StreamSupport.stream(load.spliterator(), false).findFirst();
	}

	public static <T> Optional<T> findFirst(Class<T> clazz, Predicate<? super T> predicate) {
		ServiceLoader<T> load = ServiceLoader.load(clazz);
		Stream<T> stream = StreamSupport.stream(load.spliterator(), false);
		return stream.filter(predicate).findFirst();
	}

	public static <T> Stream<T> findAll(Class<T> clazz) {
		ServiceLoader<T> load = ServiceLoader.load(clazz);
		return StreamSupport.stream(load.spliterator(), false);
	}

	public static <T> Stream<T> findAll(Class<T> clazz, Predicate<? super T> predicate) {
		ServiceLoader<T> load = ServiceLoader.load(clazz);
		return StreamSupport.stream(load.spliterator(), false).filter(predicate);
	}
}
