package com.practica;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import com.practica.repository.ProductoRepository;

@DataMongoTest
@ActiveProfiles("test")
class DemoApplicationTests {

	@MockBean
	private ProductoRepository productoRepository;

	@Test
	void contextLoads() {
		// Este test simplemente verifica que el contexto cargue
	}
}