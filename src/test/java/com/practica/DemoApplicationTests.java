package com.practica;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.data.mongodb.auto-index-creation=false")
@ActiveProfiles("test")
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}
}