package com.practica;

import com.practica.model.Producto;
import com.practica.repository.ProductoRepository;
import com.practica.service.ProductoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarProductos() {
        Producto p1 = new Producto("1", "Producto A", 100.0);
        Producto p2 = new Producto("2", "Producto B", 200.0);

        when(productoRepository.findAll()).thenReturn(Flux.just(p1, p2));

        StepVerifier.create(productoService.listarProductos())
                .expectNext(p1)
                .expectNext(p2)
                .verifyComplete();

        verify(productoRepository).findAll();
    }

    @Test
    void testObtenerProductoPorId() {
        Producto producto = new Producto("1", "Producto X", 123.0);
        when(productoRepository.findById("1")).thenReturn(Mono.just(producto));

        StepVerifier.create(productoService.obtenerProductoPorId("1"))
                .expectNext(producto)
                .verifyComplete();

        verify(productoRepository).findById("1");
    }

    @Test
    void testCrearProducto() {
        Producto nuevo = new Producto(null, "Producto Nuevo", 300.0);
        Producto guardado = new Producto("3", "Producto Nuevo", 300.0);

        when(productoRepository.save(nuevo)).thenReturn(Mono.just(guardado));

        StepVerifier.create(productoService.crearProducto(nuevo))
                .expectNext(guardado)
                .verifyComplete();

        verify(productoRepository).save(nuevo);
    }
}
