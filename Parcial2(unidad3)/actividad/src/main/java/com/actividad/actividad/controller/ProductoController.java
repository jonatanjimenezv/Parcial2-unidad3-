package com.actividad.actividad.controller;

import com.actividad.actividad.model.Producto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final List<Producto> productos = new ArrayList<>();
    private final MessageSource messageSource;

    @Autowired
    public ProductoController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostMapping
    public ResponseEntity<Mono<String>> crearProducto(
            @Valid @RequestBody Producto producto,
            BindingResult result,
            Locale locale) {

        if (result.hasErrors()) {
            String mensajeError = messageSource.getMessage("error.validacion", null, locale);
            return ResponseEntity.badRequest().body(Mono.just(mensajeError));
        }

        productos.add(producto);
        String mensajeExito = messageSource.getMessage("success.created", null, locale);
        return ResponseEntity.ok(Mono.just(mensajeExito));
    }

    @GetMapping
    public Flux<Producto> listarProductos() {
        return Flux.fromIterable(productos);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Producto>> obtenerProducto(
            @PathVariable String id,
            Locale locale) {

        return Flux.fromIterable(productos)
                .filter(p -> p.getId().equals(id))
                .next()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity
                        .status(404)
                        .body(new Producto()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<String>> actualizarProducto(
            @PathVariable String id,
            @Valid @RequestBody Producto producto,
            BindingResult result,
            Locale locale) {

        if (result.hasErrors()) {
            String error = messageSource.getMessage("error.validation", null, locale);
            return Mono.just(ResponseEntity.badRequest().body(error));
        }

        productos.removeIf(p -> p.getId().equals(id));
        productos.add(producto);
        String mensaje = messageSource.getMessage("success.updated", null, locale);
        return Mono.just(ResponseEntity.ok(mensaje));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> eliminarProducto(
            @PathVariable String id,
            Locale locale) {

        boolean removed = productos.removeIf(p -> p.getId().equals(id));

        if (removed) {
            String mensaje = messageSource.getMessage("success.deleted", null, locale);
            return Mono.just(ResponseEntity.ok(mensaje));
        }

        String error = messageSource.getMessage("error.not_found", null, locale);
        return Mono.just(ResponseEntity.status(404).body(error));
    }

    @GetMapping("/reactivo")
    public Flux<Producto> listarProductosReactivos() {
        return Flux.just(
                new Producto("1", "Laptop", 1200.0),
                new Producto("2", "Mouse", 25.0),
                new Producto("3", "Teclado", 45.0)
        );
    }
}