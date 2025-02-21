package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner l = new Scanner(System.in);

        //iniciamos la base de datos
        SQLite db = new SQLite();

        System.out.println("Bienvenido al Banco Satander");

        Usuario usuario = null;
        GestionFinanzas gestion =null;

        int op;

        do {

            System.out.println("\nQue quiere hacer hoy:");
            System.out.println("1. Agregar un nuevo usuario");
            System.out.println("2. Registrar un ingreso");
            System.out.println("3. Registrar un gasto");
            System.out.println("4. Mostrar datos del usuario");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            op = l.nextInt();
            l.nextLine();

            switch (op) {
                case 1:

                    System.out.print("Ingrese el nombre: ");
                    String nombre = l.nextLine();
                    System.out.print("Ingrese el DNI: ");
                    String dni = l.nextLine();
                    System.out.print("Ingrese los ahorros iniciales: ");
                    double ahorros = l.nextDouble();
                    l.nextLine();

                    usuario = new Usuario(nombre, dni, ahorros);
                    if (usuario.AgregarUsuario()) {
                        System.out.println("Usuario agregado exitosamente.");
                        gestion = new GestionFinanzas(usuario);
                    } else {
                        System.out.println("Error al agregar el usuario.");
                    }
                    break;

                case 2:

                    if (usuario == null) {
                        System.out.println("Debe agregar un usuario primero.");
                        break;
                    }
                    System.out.print("Ingrese el monto a ingresar: ");
                    double montoIngreso = l.nextDouble();
                    if (gestion.registrarIngreso(usuario, montoIngreso)) {
                        System.out.println("Ingreso registrado exitosamente.");
                    } else {
                        System.out.println("Error al registrar el ingreso.");
                    }
                    break;

                case 3:

                    if (usuario == null) {
                        System.out.println("Debe agregar un usuario primero.");
                        break;
                    }
                    System.out.print("Ingrese el monto del gasto: ");
                    double montoGasto = l.nextDouble();
                    l.nextLine();
                    System.out.print("Ingrese el tipo de gasto (Vacaciones, Alquiler, IRPF, Vicios): ");
                    String tipoGasto = l.nextLine();
                    if (gestion.registrarGasto(montoGasto, tipoGasto)) {
                        System.out.println("Gasto registrado exitosamente.");
                    } else {
                        System.out.println("Error al registrar el gasto.");
                    }
                    break;

                case 4:

                    if (usuario == null) {
                        System.out.println("Debe agregar un usuario primero.");
                        break;
                    }
                    System.out.println("Datos del usuario:");
                    System.out.println("Nombre: " + usuario.getNombre());
                    System.out.println("DNI: " + usuario.getDNI());
                    System.out.println("Ahorros: " + usuario.getAhorros());

                    break;

                case 5:

                    System.out.println("Cerrando...");

                    break;

                default:
                    System.out.println("Numero invalido, intente de nuevo");

            }

        } while (op != 5);


    }
}