package jm.task.core.jdbc.dao;

import com.mysql.cj.CancelQueryTask;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.tool.schema.internal.StandardTableExporter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static UserDaoJDBCImpl instance;
    private static final String DELETE_USER = "DELETE FROM users.users WHERE id=?";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users.users (id INT AUTO_INCREMENT PRIMARY KEY , name VARCHAR(255), lastName VARCHAR(255), age TINYINT)";
    private static final String SAVE_USER = "INSERT INTO users.users(id, name, lastName, age) VALUES(id, ?, ?, ?)";
    private static final String GET_ALL_USERS = "SELECT * FROM users.users";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users.users";
    private static final String CLEAR_TABLE = "TRUNCATE TABLE users.users";


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.SQLconnect()) {
            PreparedStatement statement = connection.prepareStatement(CREATE_TABLE);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания таблицы", e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.SQLconnect()) {
            PreparedStatement statement = connection.prepareStatement(DROP_TABLE);
            statement.execute();
            System.out.println("Таблица удалена успешно");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления таблицы",e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.SQLconnect()) {
            PreparedStatement statement = connection.prepareStatement(SAVE_USER);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось добавить пользователя", e);
        }
        ;
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.SQLconnect()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_USER);
            statement.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось удалить пользователя", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.SQLconnect()) {
            Statement statement = connection.createStatement();
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
            throw new RuntimeException("Не получить список пользователей", e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.SQLconnect()) {
            PreparedStatement statement = connection.prepareStatement(CLEAR_TABLE);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Не очистить таблицу", e);
        }
    }
}
