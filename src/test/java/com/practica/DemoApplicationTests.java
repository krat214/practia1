package com.practica;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import com.practica.repository.ProductoRepository;

@SpringBootTest
@ActiveProfiles("test")
class DemoApplicationTests {

	@MockBean
	private ProductoRepository productoRepository;

	@Test
	@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
	void contextLoads() {
		// Este test simplemente verifica que el contexto cargue
	}
}