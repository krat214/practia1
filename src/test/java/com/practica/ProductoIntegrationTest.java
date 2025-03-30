package com.practica;

import com.practica.config.TestMongoConfig;
import com.practica.model.Producto;
import com.practica.repository.ProductoRepository;
import com.practica.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@Import(TestMongoConfig.class)
public class ProductoIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductoRepository productoRepository;

    @BeforeEach
    void setUp() {
        // No necesitamos limpiar la BD porque estamos usando un mock
        // Configuramos el comportamiento del mock para cada prueba
    }

    @Test
    void testCrearProducto() {
        Producto producto = new Producto(null, "Prueba API", 99.99);
        Producto productoGuardado = new Producto("1", "Prueba API", 99.99);

        when(productoRepository.save(any(Producto.class))).thenReturn(Mono.just(productoGuardado));

        webTestClient.post()
                .uri("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(producto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.nombre").isEqualTo("Prueba API")
                .jsonPath("$.precio").isEqualTo(99.99);
    }

    @Test
    void testObtenerProductoPorId() {
        Producto producto = new Producto("1", "Guardado", 123.0);

        when(productoRepository.findById("1")).thenReturn(Mono.just(producto));

        webTestClient.get()
                .uri("/api/productos/{id}", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.nombre").isEqualTo("Guardado");
    }

    @Test
    void testEliminarProducto() {
        when(productoRepository.deleteById(anyString())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/productos/{id}", "1")
                .exchange()
                .expectStatus().isNoContent();
    }
}