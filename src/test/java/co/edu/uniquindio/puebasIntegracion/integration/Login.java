package co.edu.uniquindio.puebasIntegracion.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {

    // Configuración de la base de datos MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/pruebasintegracion"; // Cambia esto a la URL de tu base de datos MySQL
    private static final String USER = "root"; // Cambia esto a tu nombre de usuario de MySQL
    private static final String PASSWORD = "12345"; // Cambia esto a tu contraseña de MySQL
    private static final String USER_TABLE = "user";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            createTableIfNotExists(connection);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese su nombre de usuario: ");
            String username = scanner.nextLine();
            System.out.print("Ingrese su contraseña: ");
            String password = scanner.nextLine();

            if (authenticateUser(connection, username, password)) {
                System.out.println("Inicio de sesión exitoso.");
            } else {
                System.out.println("Nombre de usuario o contraseña incorrectos.");
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Crear la tabla de usuarios si no existe
    private static void createTableIfNotExists(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "username VARCHAR(255) NOT NULL UNIQUE,"
                + "password VARCHAR(255) NOT NULL)";
        connection.createStatement().executeUpdate(createTableSQL);
    }

    // Autenticar al usuario
    private static boolean authenticateUser(Connection connection, String username, String password) throws SQLException {
        String selectUserSQL = "SELECT * FROM " + USER_TABLE + " WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectUserSQL);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
}
