package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public final class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private static Connection connection = null;
    private static SessionFactory sessionFactory;


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

    public static SessionFactory getSessionFactory() {
        try {
            // Создаём объект Configuration
            Configuration configuration = new Configuration();

            // Устанавливаем свойства подключения и другие параметры Hibernate
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/users");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "root");
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

            // Дополнительные настройки
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");

            // Регистрируем классы-сущности (аннотированные @Entity)
            configuration.addAnnotatedClass(User.class);

            // Создаём ServiceRegistry на основе настроек
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            // Создаём SessionFactory
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            System.err.println("Ошибка создания SessionFactory: " + e);
            e.printStackTrace();
        }
        return sessionFactory;
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
