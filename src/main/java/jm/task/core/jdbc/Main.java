package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();

        //Создание таблицы
        userDaoHibernate.createUsersTable();

        //Добавление сущностей
        userDaoHibernate.saveUser("Ivan", "Ivanov", (byte) 23);
        userDaoHibernate.saveUser("Neil", "Alishev", (byte) 50);
        userDaoHibernate.saveUser("Oleg", "Sidorov", (byte) 13);
        userDaoHibernate.saveUser("Zaur", "Tregulov", (byte) 33);

        // Удаление пользователя по id
        //userDaoHibernate.removeUserById(2);

        //Список Юзеров
        userDaoHibernate.getAllUsers().forEach(System.out::println);

        //Очистка таблицы
        //userDaoHibernate.cleanUsersTable();

        //Удаление таблицы
        //userDaoHibernate.dropUsersTable();

        //Закрытие соединения
        Util.closeConnection();
    }
}
