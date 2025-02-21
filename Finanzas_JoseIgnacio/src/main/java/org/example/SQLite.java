package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite {

    private String url = "jdbc:sqlite:Satander.db"; // Ruta

    public SQLite(){

        try(Connection conn = DriverManager.getConnection(url)){
            if (conn != null) {
                System.out.println("¡Conexión establecida!");
                //creacion de las tablas
                TablaUsuarios(conn);
                TablaGastos(conn);
            }
        } catch(SQLException e)

        {
            System.out.println("No se pudo conectar: " + e.getMessage());

        }

    }



    //tabla de usuarios
    private void TablaUsuarios (Connection conn){

        String sql = "CREATE TABLE IF NOT EXISTS Usuarios (" +
                "nombre VARCHAR(100) NOT NULL, " +
                "DNI VARCHAR(20) UNIQUE NOT NULL, " +
                "ahorros REAL NOT NULL DEFAULT 0.0" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("La tabla Usuarios ha sido creada o ya existe.");
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla Usuarios: " + e.getMessage());
        }

    }

    //tabla de gastos
    private void TablaGastos(Connection conn){

        String sql = "CREATE TABLE IF NOT EXISTS Gastos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DNI VARCHAR(9) NOT NULL, " +
                "monto REAL NOT NULL, " +
                "tipo VARCHAR(15) NOT NULL CHECK(tipo IN ('Vacaciones', 'Alquiler', 'IRPF', 'Vicios')), " +
                "fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (DNI) REFERENCES Usuarios(DNI)" +
                ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("La tabla Gastos ha sido creada o ya existe.");
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla Usuarios: " + e.getMessage());
        }

    }


}
