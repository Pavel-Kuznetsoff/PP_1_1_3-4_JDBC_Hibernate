package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String createTableQuery = """
                CREATE TABLE IF NOT EXISTS user_schema.user (
                user_id INT NOT NULL AUTO_INCREMENT, 
                user_name VARCHAR(20) NOT NULL,
                user_lastName VARCHAR(50) NOT NULL , 
                user_age TINYINT NOT NULL,
                PRIMARY KEY (user_id))
                """;
        try (Connection con = Util.getConnection();
             Statement statement = con.createStatement()) {
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String dropTableQuery = """
                DROP TABLE IF EXISTS user_schema.user
                """;
        try (Connection con = Util.getConnection();
             Statement statement = con.createStatement()) {
            statement.executeUpdate(dropTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertQuery = String.format(
                "INSERT INTO user_schema.user (user_name, user_lastName, user_age) " +
                "VALUES ('%s', '%s', '%d')", name, lastName, age);
        try (Connection con = Util.getConnection();
             Statement statement = con.createStatement()) {
            statement.executeUpdate(insertQuery);
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String deleteQuery = String.format(
                "DELETE FROM user_schema.user WHERE user_id = %d", id);
        try (Connection con = Util.getConnection();
             Statement statement = con.createStatement()) {
            statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String getUsersQuery = """
                SELECT * FROM user_schema.user
                """;
        List<User> users = new ArrayList<>();
        try (Connection con = Util.getConnection();
             Statement statement = con.createStatement()) {
            statement.executeQuery(getUsersQuery);
            ResultSet resultSet = statement.executeQuery(getUsersQuery);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String clearTableQuery = """
                TRUNCATE user_schema.user
                """;
        try (Connection con = Util.getConnection();
             Statement statement = con.createStatement()) {
            statement.executeUpdate(clearTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
