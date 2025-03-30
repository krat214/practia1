package com.practica;

import com.practica.model.Producto;
import com.practica.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class ProductoIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProductoRepository productoRepository;

    @BeforeEach
    void setUp() {
        productoRepository.deleteAll().block();
    }

    @Test
    void testCrearProducto() {
        Producto producto = new Producto(null, "Prueba API", 99.99);

        webTestClient.post()
                .uri("/api/productos")
                .bodyValue(producto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.nombre").isEqualTo("Prueba API")
                .jsonPath("$.precio").isEqualTo(99.99);
    }

    @Test
    void testObtenerProductoPorId() {
        Producto guardado = productoRepository.save(new Producto(null, "Guardado", 123.0)).block();

        webTestClient.get()
                .uri("/api/productos/{id}", guardado.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.nombre").isEqualTo("Guardado");
    }

    @Test
    void testEliminarProducto() {
        Producto producto = productoRepository.save(new Producto(null, "Eliminar", 50.0)).block();

        webTestClient.delete()
                .uri("/api/productos/{id}", producto.getId())
                .exchange()
                .expectStatus().isNoContent();
    }
}
