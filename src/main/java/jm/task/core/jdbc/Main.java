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
        //UserDao userDao = new UserDaoJDBCImpl();
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        userDaoHibernate.createUsersTable();

        //Создание таблицы
//        userDao.createUsersTable();

        //Добавление сущностей
//        userDao.saveUser("Ivan", "Ivanov", (byte) 21);
//        userDao.saveUser("Petr", "Petrov", (byte) 23);
//        userDao.saveUser("Anton", "Titov", (byte) 23);
//        userDao.saveUser("Dmitry", "Dmitriev", (byte) 24);

        //Список Юзеров
//        System.out.println(userDao.getAllUsers());

        //Очистка
        //userDao.cleanUsersTable();

        //Удаление
        //userDao.dropUsersTable();

        //Закрытие соединения
        Util.closeConnection();
    }
}
