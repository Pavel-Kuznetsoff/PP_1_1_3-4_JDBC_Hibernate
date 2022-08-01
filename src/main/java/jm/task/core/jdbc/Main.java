package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoJDBCImpl();

//        Создание таблицы User(ов)
        userDao.createUsersTable();

//        Добавление 4 User(ов) в таблицу с данными на свой выбор.
//        После каждого добавления должен быть вывод в консоль
//        ( User с именем – name добавлен в базу данных )
        userDao.saveUser("Иван", "Иванов", (byte) 18);
        userDao.saveUser("Сергей", "Сергеев", (byte) 30);
        userDao.saveUser("Алексей", "Алексеев", (byte) 25);
        userDao.saveUser("Семен", "Семенов", (byte) 26);

//        Получение всех User из базы и вывод в консоль (должен быть переопределен toString в классе User)
        userDao.getAllUsers().forEach(System.out::println);

//        Очистка таблицы User(ов)
        userDao.cleanUsersTable();

//        Удаление таблицы
        userDao.dropUsersTable();
    }
}
