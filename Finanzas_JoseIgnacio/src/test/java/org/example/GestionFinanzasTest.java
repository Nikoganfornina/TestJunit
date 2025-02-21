package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestionFinanzasTest {

    @Test
    void testRegistrarIngreso() {
        Usuario usuario = new Usuario("12345678A", "Juan", 1000);
        GestionFinanzas gestion = new GestionFinanzas(usuario);
        boolean resultado = gestion.registrarIngreso(usuario, 500);
        assertTrue(resultado);
        assertEquals(1500, usuario.getAhorros());
    }

    @Test
    void testRegistrarGastoConFondosSuficientes() {
        Usuario usuario = new Usuario("12345678A", "Juan", 1000);
        GestionFinanzas gestion = new GestionFinanzas(usuario);
        boolean resultado = gestion.registrarGasto(500, "Alquiler");
        assertTrue(resultado);
        assertEquals(500, usuario.getAhorros());
    }

    @Test
    void testRegistrarGastoSinFondosSuficientes() {
        Usuario usuario = new Usuario("12345678A", "Juan", 300);
        GestionFinanzas gestion = new GestionFinanzas(usuario);
        boolean resultado = gestion.registrarGasto(500, "Alquiler");
        assertFalse(resultado);
        assertEquals(300, usuario.getAhorros());
    }

    @Test
    void testRegistrarGastoTipoInvalido() {
        Usuario usuario = new Usuario("12345678A", "Juan", 1000);
        GestionFinanzas gestion = new GestionFinanzas(usuario);
        boolean resultado = gestion.registrarGasto(200, "Comida");
        assertFalse(resultado);
        assertEquals(1000, usuario.getAhorros());
    }

}