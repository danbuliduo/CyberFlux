package io.cyberflux.cloud.core.service.impl;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cyberflux.cloud.core.cache.NodeEngineCache;
import io.cyberflux.cloud.core.service.NodeEngineService;
import io.cyberflux.meta.models.node.NodeEngineModel;

@Service
public class NodeEngineServiceImpl implements NodeEngineService {

	@Autowired
	NodeEngineCache cache;

	@Override
	public void register(NodeEngineModel model) {
		cache.save(model);
	}

	@Override
	public Map<String, Set<NodeEngineModel>> findAll() {
		return cache.findAll();
	}
}
