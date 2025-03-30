package com.practica;

import com.practica.controller.ProductoController;
import com.practica.model.Producto;
import com.practica.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(ProductoController.class)
@ActiveProfiles("test")
public class ProductoIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductoService productoService;  // Nota: Aquí estamos simulando el servicio, no el repositorio

    @Test
    void testCrearProducto() {
        Producto producto = new Producto(null, "Prueba API", 99.99);
        Producto productoGuardado = new Producto("1", "Prueba API", 99.99);

        when(productoService.crearProducto(any(Producto.class))).thenReturn(Mono.just(productoGuardado));

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

        when(productoService.obtenerProductoPorId("1")).thenReturn(Mono.just(producto));

        webTestClient.get()
                .uri("/api/productos/{id}", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.nombre").isEqualTo("Guardado");
    }

    @Test
    void testEliminarProducto() {
        when(productoService.eliminarProducto(anyString())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/productos/{id}", "1")
                .exchange()
                .expectStatus().isNoContent();
    }

    // Podemos agregar un test para el método de listar productos también
    @Test
    void testListarProductos() {
        Producto p1 = new Producto("1", "Producto A", 100.0);
        Producto p2 = new Producto("2", "Producto B", 200.0);

        when(productoService.listarProductos()).thenReturn(Flux.just(p1, p2));

        webTestClient.get()
                .uri("/api/productos")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Producto.class)
                .hasSize(2)
                .contains(p1, p2);
    }
}