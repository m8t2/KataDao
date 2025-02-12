package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory;
    private static Transaction tx = null;

    public UserDaoHibernateImpl() {
        this.sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(255) NOT NULL, "
                    + "lastname VARCHAR(255) NOT NULL, "
                    + "age TINYINT NOT NULL)";

            session.createNativeQuery(sql).executeUpdate();
            tx.commit();
            System.out.println("Таблица создана с помощью Hibernate");
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            System.out.println("Ошибка создания таблицы Hibernate");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            String sql = "DROP TABLE users.users";
            session.createNativeQuery(sql).executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            System.out.println("Ошибка создания таблицы Hibernate");
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            String sql = "INSERT INTO users.users(name, lastName, age) VALUES(?, ?, ?)";
            session.createSQLQuery(sql).executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            System.out.println("Ошибка добавления пользователя Hibernate");
        }
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
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            String sql = "TRUNCATE TABLE users.users";
            session.createNativeQuery(sql).executeUpdate();
        }
    }
}
