package io.cyberflux.cloud.core.service;

import java.util.List;

import io.cyberflux.meta.models.device.ActiveDeviceModel;

public interface ActiveDeviceService {
	List<ActiveDeviceModel> findAll();
}
