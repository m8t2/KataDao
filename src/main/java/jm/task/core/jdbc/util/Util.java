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
    private static final String HIBERNATE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_ADDRESS = "jdbc:mysql://localhost:3306/users";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";
    private static final String DB_DIALECT = "org.hibernate.dialect.MySQLDialect";
    private static final String HIBERNATE_SHOW_SQL= "true";
    private static final String HIBERNATE_FORMAT_SQL = "true";

    private static Connection connection = null;
    private static SessionFactory sessionFactory = null;

    private Util() {

    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            try {
                Configuration configuration = new Configuration();

                configuration.setProperty("hibernate.connection.driver_class", HIBERNATE_DRIVER);
                configuration.setProperty("hibernate.connection.url", DB_ADDRESS);
                configuration.setProperty("hibernate.connection.username", DB_USERNAME);
                configuration.setProperty("hibernate.connection.password", DB_PASSWORD);
                configuration.setProperty("hibernate.dialect", DB_DIALECT);
                configuration.setProperty("hibernate.show_sql", HIBERNATE_SHOW_SQL);
                configuration.setProperty("hibernate.format_sql", HIBERNATE_FORMAT_SQL);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.err.println("Ошибка создания SessionFactory: " + e);
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void closeConnection() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("SessionFactory успешно закрыт");
        } else {
            System.out.println("SessionFactory уже закрыт или не инициализирован");
        }
    }
}
