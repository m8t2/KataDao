package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        Util.SQLconnect();

        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();

        userDao.saveUser("asrd","fds",(byte) 21);
        userDao.saveUser("asr2d","fds",(byte) 23);
        userDao.saveUser("as3rd","fds",(byte) 24);

        System.out.println(userDao.getAllUsers());
    }
}
