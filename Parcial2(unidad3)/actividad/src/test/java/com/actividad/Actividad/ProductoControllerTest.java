package com.actividad.Actividad;

import com.actividad.actividad.controller.ProductoController;
import com.actividad.actividad.model.Producto;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ProductoControllerTest {

	private final ProductoController productoController = new ProductoController();

	@Test
	void testListaProductos() {
		Flux<Producto> productos = productoController.listarProductosReactivos();

		StepVerifier.create(productos)
				.expectNextMatches(p -> p.getNombre().equals("Laptop"))
				.expectNextMatches(p -> p.getNombre().equals("Mouse"))
				.expectNextMatches(p -> p.getNombre().equals("Teclado"))
				.verifyComplete();
	}
}