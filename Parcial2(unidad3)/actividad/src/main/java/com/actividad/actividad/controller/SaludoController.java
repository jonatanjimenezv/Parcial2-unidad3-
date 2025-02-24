package com.actividad.actividad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class SaludoController {

    private final MessageSource messageSource;

    @Autowired
    public SaludoController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/saludo")
    public ResponseEntity<String> obtenerSaludo(Locale locale) {
        try {
            String saludo = messageSource.getMessage("saludo", null, locale);
            return ResponseEntity.ok(saludo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno: " + e.getMessage());
        }
    }
}