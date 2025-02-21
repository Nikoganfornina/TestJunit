package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testValidarDNI() {
        Usuario usuario = new Usuario("12345678A", "Niko", 1000.0);
        assertTrue(usuario.validarDNI("12345678A"));
        assertFalse(usuario.validarDNI("1234A"));
    }

    @Test
    void testSetNombre() {
        Usuario usuario = new Usuario("12345678A", "Niko", 1000.0);
        usuario.setNombre("Martín");
        assertEquals("Martín", usuario.getNombre());
    }

    @Test
    void testSetAhorros() {
        Usuario usuario = new Usuario("12345678A", "Niko", 1000.0);
        usuario.setAhorros(500.0);
        assertEquals(500.0, usuario.getAhorros());
    }
}
