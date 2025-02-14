package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        //Создание таблицы
        userService.createUsersTable();

        //Добавление сущностей
        userService.saveUser("Ivan", "Ivanov", (byte) 21);
        userService.saveUser("Petr", "Petrov", (byte) 23);
        userService.saveUser("Anton", "Titov", (byte) 23);
        userService.saveUser("Dmitry", "Dmitriev", (byte) 24);

        // Удаление пользователя по id
        userDao.removeUserById(2);

        //Список Юзеров
        System.out.println(userService.getAllUsers());

        //Очистка
        userService.cleanUsersTable();

        //Удаление
        userService.dropUsersTable();

        //Закрытие соединения
        Util.closeConnection();
    }
}
