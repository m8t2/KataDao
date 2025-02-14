package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        //Создание таблицы
        userService.createUsersTable();

        //Добавление сущностей
        userService.saveUser("Ivan", "Ivanov", (byte) 23);
        userService.saveUser("Neil", "Alishev", (byte) 50);
        userService.saveUser("Oleg", "Sidorov", (byte) 13);
        userService.saveUser("Zaur", "Tregulov", (byte) 33);

        // Удаление пользователя по id
        userService.removeUserById(2);

        //Список Юзеров
        userService.getAllUsers();

        //Очистка таблицы
        userService.cleanUsersTable();

        //Удаление таблицы
        userService.dropUsersTable();

        //Закрытие соединения
        Util.closeConnection();
    }
}
