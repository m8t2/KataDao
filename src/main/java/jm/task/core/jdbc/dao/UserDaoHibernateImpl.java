package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
            String sql = "DROP TABLE IF EXISTS users";
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
            session.save(new User(name, lastName, age));
            tx.commit();
            System.out.println("Пользователь добавлен успешно");
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            System.out.println("Ошибка добавления пользователя Hibernate");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(session.get(User.class, id));
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                System.out.println("Пользователь удален успешно");
            } else {
                System.out.println("Пользователь с id=" + id + " не найден");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            System.out.println("Ошибка удаления пользователя Hibernate");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            List<User> entities = session.createQuery("from User", User.class).list();
            entities.stream().forEach(System.out::println);
            return entities;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            throw new HibernateException("Не удалось очистить таблицу", e);
        }
    }
}
