package io.cyberflux.cloud.core.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Component;

import io.cyberflux.meta.models.node.NodeEngineModel;

@Component
public final class NodeEngineCache {

	private final Map<String, Set<NodeEngineModel>> models;

	public NodeEngineCache() {
		models = new ConcurrentHashMap<>(1);
	}

	public void save(NodeEngineModel model) {
		models.computeIfAbsent(model.getNamespace(), key ->
			new CopyOnWriteArraySet<>()
		).add(model);
	}

	public void remove(NodeEngineModel model) {
		if(models.containsKey(model.getNamespace())) {
			models.get(model.getNamespace()).remove(model);
		}
	}

	public Map<String, Set<NodeEngineModel>> findAll() {
		Map<String, Set<NodeEngineModel>> map = new HashMap<>(models.size());
		map.putAll(models);
		return map;
	}

	public Set<NodeEngineModel> findByNamespace(String namespace) {
		return models.get(namespace);
	}

}
