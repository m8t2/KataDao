package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        this.sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(255) NOT NULL, "
                    + "lastname VARCHAR(255) NOT NULL, "
                    + "age TINYINT NOT NULL)";

            session.createNativeQuery(sql).executeUpdate();
            tx.commit();
            System.out.println("Таблица создана с помощью Hibernate");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            String sql = "DROP TABLE users.users";
            session.createNativeQuery(sql).executeUpdate();
            tx.commit();
        }catch (Exception e) {
            throw new RuntimeException("Ошибка удаления таблицы",e);
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            String sql = "TRUNCATE TABLE users.users";
            session.createNativeQuery(sql).executeUpdate();
        }
    }
}
