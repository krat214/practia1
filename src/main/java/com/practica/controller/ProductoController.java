package com.practica.controller;

import com.practica.model.Producto;
import com.practica.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public Flux<Producto> listar() {
        return productoService.listarProductos();
    }

    @GetMapping("/{id}")
    public Mono<Producto> obtener(@PathVariable String id) {
        return productoService.obtenerProductoPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Producto> crear(@RequestBody Producto producto) {
        return productoService.crearProducto(producto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminar(@PathVariable String id) {
        return productoService.eliminarProducto(id);
    }
}

