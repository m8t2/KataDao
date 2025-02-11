package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String DELETE_USER = "DELETE FROM users.users WHERE id=?";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users.users (id INT AUTO_INCREMENT PRIMARY KEY , name VARCHAR(255), lastName VARCHAR(255), age TINYINT)";
    private static final String SAVE_USER = "INSERT INTO users.users(name, lastName, age) VALUES(?, ?, ?)";
    private static final String GET_ALL_USERS = "SELECT * FROM users.users";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users.users";
    private static final String CLEAR_TABLE = "TRUNCATE TABLE users.users";

    private final Connection connection;

    public UserDaoJDBCImpl() {
        this.connection = Util.SQLconnect();
    }

    @Override
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания таблицы", e);
        }     }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(DROP_TABLE);
            System.out.println("Таблица удалена успешно");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления таблицы", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement statement = connection.prepareStatement(SAVE_USER)) {
            connection.setAutoCommit(false);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            throw new RuntimeException("Не удалось добавить пользователя", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось удалить пользователя", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить список пользователей", e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement(CLEAR_TABLE)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось очистить таблицу", e);
        }
    }
}
