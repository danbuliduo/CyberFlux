package io.cyberflux.cloud.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cyberflux.cloud.core.cache.ActiveDeviceCache;
import io.cyberflux.cloud.core.service.ActiveDeviceService;
import io.cyberflux.meta.models.device.ActiveDeviceModel;

@Service
public class ActiveDeviceServiceImpl implements ActiveDeviceService {

	@Autowired
	ActiveDeviceCache cache;

	@Override
	public List<ActiveDeviceModel> findAll() {
		return cache.findAll();
	}
}
