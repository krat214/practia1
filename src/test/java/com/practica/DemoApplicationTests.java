package com.practica;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import com.practica.repository.ProductoRepository;

@SpringBootTest
@ActiveProfiles("test")
@EnableReactiveMongoRepositories
class DemoApplicationTests {

	@MockBean
	private ProductoRepository productoRepository;

	@Test
	void contextLoads() {
		// Este test simplemente verifica que el contexto cargue
	}
}