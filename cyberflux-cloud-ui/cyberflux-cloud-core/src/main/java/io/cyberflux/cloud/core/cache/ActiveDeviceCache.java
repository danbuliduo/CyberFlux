package io.cyberflux.cloud.core.cache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import io.cyberflux.meta.models.device.ActiveDeviceModel;

@Component
public final class ActiveDeviceCache {

	private final LoadingCache<String, ActiveDeviceModel> models;

	public ActiveDeviceCache() {
		models = Caffeine.newBuilder().build(key -> {
			return null;
		});
	}

	public List<ActiveDeviceModel> findAll() {
		List<ActiveDeviceModel> list = new ArrayList<>();
		list.addAll(models.asMap().values());
		return list;
	}

	public void save(ActiveDeviceModel model) {
		models.put(model.getId(), model);
	}

	public void remove(ActiveDeviceModel model) {
		models.invalidate(model.getId());
	}

}
