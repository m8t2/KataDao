package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private static Connection connection = null;

    private Util() {

    }

    public static Connection SQLconnect() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE SCHEMA IF NOT EXISTS Users");
            stmt.execute("USE Users");
            System.out.println("Подключение успешно");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось подключиться");
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Соединение закрыто");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
