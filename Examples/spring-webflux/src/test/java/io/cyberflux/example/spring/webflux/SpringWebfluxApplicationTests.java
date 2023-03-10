package io.cyberflux.example.spring.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;


@SpringBootTest
class SpringWebfluxApplicationTests {

	@Test
	@Bean("d")
	void contextLoads() throws InterruptedException {
		//System.out.println(reactor);
		Thread.sleep(10000);
	}

}
