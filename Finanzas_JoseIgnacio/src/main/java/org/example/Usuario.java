package org.example;

import java.sql.*;

public class Usuario {


    //atributos
    private String Nombre;
    private String DNI;
    private double Ahorros;

    //constructor
    public Usuario(String nombre, String DNI, double ahorros) {
        Nombre = nombre;
        this.DNI = DNI;
        Ahorros = ahorros;
    }

    //getters y setters
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public double getAhorros() {
        return Ahorros;
    }

    public void setAhorros(double ahorros) {
        Ahorros = ahorros;
    }

    // Validar formato básico del DNI
    public boolean validarDNI(String s) {
        final String letrasDNI = "TRWAGMYFPDXBNJZSQVHLCKE";
        if (!DNI.matches("\\d{8}[A-Za-z]")) {
            return false;
        }
        int numero = Integer.parseInt(DNI.substring(0, 8));
        char letra = Character.toUpperCase(DNI.charAt(8));
        return letra == letrasDNI.charAt(numero % 23);
    }

    // Verificar DNI en la base de datos
    public boolean verificarDNIEnBaseDeDatos() {
        String url = "jdbc:sqlite:Satander.db";
        String sql = "SELECT COUNT(*) FROM Usuarios WHERE DNI = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, DNI);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt(1) > 0;

        } catch (Exception e) {
            System.out.println("Error al verificar el DNI: " + e.getMessage());
            return false;
        }
    }

    // Validación completa
    public boolean validarDNICompleto() {
        return validarDNI("12345678A") && verificarDNIEnBaseDeDatos();
    }

    // Función para agregar un usuario a la base de datos
    public boolean AgregarUsuario() {
        String url = "jdbc:sqlite:Satander.db";
        String sql = "INSERT INTO Usuarios (nombre, DNI, ahorros) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, Nombre);
            pstmt.setString(2, DNI);
            pstmt.setDouble(3, Ahorros);

            int filasInsertadas = pstmt.executeUpdate();
            return filasInsertadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar el usuario: " + e.getMessage());
            return false;
        }
    }

    // Función para encontrar un usuario por DNI
    public static Usuario EncontrarUsuario(String dniBuscado) {
        String url = "jdbc:sqlite:Satander.db";
        String sql = "SELECT nombre, DNI, ahorros FROM Usuarios WHERE DNI = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dniBuscado);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Usuario(rs.getString("nombre"), rs.getString("DNI"), rs.getDouble("ahorros"));
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el usuario: " + e.getMessage());
        }

        return null;
    }

}

