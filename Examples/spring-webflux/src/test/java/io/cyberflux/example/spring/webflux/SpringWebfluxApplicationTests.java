package io.cyberflux.example.spring.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.cyberflux.reactor.mqtt.MqttReactor;


@SpringBootTest
class SpringWebfluxApplicationTests {
	@Autowired
	MqttReactor reactor;
	@Test
	void contextLoads() throws InterruptedException {
		System.out.println(reactor.uuid());
		Thread.sleep(10000);
	}

}
