package org.example.proyectoartistasdi;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:sqlite:" + Paths.get("database", "chinook.db").toAbsolutePath();
    private static Connection conexion;
    private ConexionDB() {}
    public static Connection obtenerConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos" + e.getMessage());        }
        return conexion;
    }



}
