package io.cyberflux.cloud.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.cyberflux.cloud.core.service.ActiveDeviceService;
import io.cyberflux.meta.models.device.ActiveDeviceModel;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("device")
public class ActiveDeviceController {

	@Autowired
	ActiveDeviceService service;

	@GetMapping("/query/all")
	public Flux<ActiveDeviceModel> query_all() {
		return Flux.fromIterable(service.findAll());
	}
}
