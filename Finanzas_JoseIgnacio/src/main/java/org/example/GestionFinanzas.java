package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GestionFinanzas {

    //atributos
    private Usuario usuario;

    private enum TipoGasto{Vacaciones, Alquiler, IRPF, Vicios}

    public GestionFinanzas(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // Función para registrar un ingreso
    public boolean registrarIngreso(Usuario usuario, double monto) {
        String url = "jdbc:sqlite:Satander.db";
        String sql = "UPDATE Usuarios SET ahorros = ahorros + ? WHERE DNI = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, monto);
            pstmt.setString(2, usuario.getDNI());

            int filasActualizadas = pstmt.executeUpdate();
            if (filasActualizadas > 0) {
                usuario.setAhorros(usuario.getAhorros() + monto);
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar el ingreso: " + e.getMessage());
        }

        return false;
    }

    // Función para registrar un gasto
    public boolean registrarGasto(double monto, String tipo) {
        String url = "jdbc:sqlite:Satander.db";

        // Verificar fondos suficientes
        if (usuario.getAhorros() < monto) {
            System.out.println("Fondos insuficientes.");
            return false;
        }

        // Validar tipo de gasto
        try {
            TipoGasto.valueOf(tipo);
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo de gasto invalido.");
            return false;
        }

        // Transacción para registrar el gasto y actualizar los ahorros
        String updateAhorrosSQL = "UPDATE Usuarios SET ahorros = ahorros - ? WHERE DNI = ?";
        String insertGastoSQL = "INSERT INTO Gastos (DNI, monto, tipo) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url)) {
            conn.setAutoCommit(false);

            // Actualizar ahorros
            try (PreparedStatement pstmt1 = conn.prepareStatement(updateAhorrosSQL)) {
                pstmt1.setDouble(1, monto);
                pstmt1.setString(2, usuario.getDNI());
                pstmt1.executeUpdate();
            }

            // Registrar gasto
            try (PreparedStatement pstmt2 = conn.prepareStatement(insertGastoSQL)) {
                pstmt2.setString(1, usuario.getDNI());
                pstmt2.setDouble(2, monto);
                pstmt2.setString(3, tipo);
                pstmt2.executeUpdate();
            }

            // Confirmar transacción
            conn.commit();

            // Actualizar objeto usuario
            usuario.setAhorros(usuario.getAhorros() - monto);
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar el gasto: " + e.getMessage());
            return false;
        }
    }

}