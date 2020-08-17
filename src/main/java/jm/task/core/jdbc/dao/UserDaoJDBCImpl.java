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
    private Connection conn;
    private Statement statement;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            conn = Util.getMyConnection();
            statement = conn.createStatement();

            int resultRows = statement.executeUpdate("CREATE TABLE Users(" +
                    "id INTEGER AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "last_name VARCHAR(255) NOT NULL," +
                    "age INTEGER NOT NULL);");

            System.out.println("Создана таблица, количество строк: " + resultRows);

        } catch (SQLException e) {
            System.out.println("Таблица уже существует.");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public void dropUsersTable() {
        try {
            conn = Util.getMyConnection();
            statement = conn.createStatement();

            int resultRows = statement.executeUpdate("DROP TABLE Users");

            System.out.println("Таблица удалена, количество строк: " + resultRows);

        } catch (SQLException e) {
            System.out.println("Таблицы не существует.");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            conn = Util.getMyConnection();
            statement = conn.createStatement();

            String sql = "INSERT INTO Users(name , last_name, age) " +
                    "VALUES ('" + name + "', '" + lastName + "', " + age + ");";

            int resultRows = statement.executeUpdate(sql);

            System.out.println("Добавлен User, количество: " + resultRows);

        } catch (SQLException e) {
            System.out.println("User уже существует.");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public void removeUserById(long id) {
        try {
            conn = Util.getMyConnection();
            statement = conn.createStatement();

            int resultRows = statement.executeUpdate("DELETE FROM Users WHERE ID = " + id + ";");

            System.out.println("Удалено строк: " + resultRows);


        } catch (SQLException e) {
            System.out.println("Строка отсутствует.");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try {
            conn = Util.getMyConnection();
            statement = conn.createStatement();

            ResultSet rS = statement.executeQuery("SELECT * FROM Users;");

            while (rS.next()) {
                User tempUser = new User();

                long id = rS.getInt(1);
                String name = rS.getString(2);
                String lastName = rS.getString(3);
                byte age = (byte) rS.getInt(4);

                tempUser.setId(id);
                tempUser.setName(name);
                tempUser.setLastName(lastName);
                tempUser.setAge(age);
                list.add(tempUser);

                System.out.println("----------------------");
                System.out.println("User id: " + id);
                System.out.println("User name: " + name);
                System.out.println("User lastName: " + lastName);
                System.out.println("User age: " + age);


            }
        } catch (SQLException e) {
            System.out.println("!");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {

            }
        }
        System.out.println("----------------------");
        return list;
    }

    public void cleanUsersTable() {
        try {
            conn = Util.getMyConnection();
            statement = conn.createStatement();

            int resultRows = statement.executeUpdate("DELETE FROM Users;");

            System.out.println("Удалено строк: " + resultRows);

        } catch (SQLException e) {
            System.out.println("Строки отсутствуют");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public void close() {

    }
}
