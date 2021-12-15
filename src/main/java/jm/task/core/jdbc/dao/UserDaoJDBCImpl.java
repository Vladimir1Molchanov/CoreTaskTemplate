package jm.task.core.jdbc.dao;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    Util util = new Util();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Connection conn = util.connect()) {
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS test (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(20) NOT NULL,\n" +
                    "  `lastname` VARCHAR(20) NOT NULL,\n" +
                    "  `age` INT(3) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `idusers_UNIQUE` (`id` ASC));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection conn = util.connect()) {
            Statement statement = conn.createStatement();
            statement.execute("drop table if exists test;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = util.connect()) {
            PreparedStatement preparedStatement = conn.prepareStatement("insert into test(name, lastname, age) values(?,?,?);");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Connection conn = util.connect()) {
            PreparedStatement preparedStatement = conn.prepareStatement("delete from test where id = ?;");
            preparedStatement.setInt(1, (int) id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Connection conn = util.connect()) {
            List<User> ul = new ArrayList<>();
            ResultSet resultSet = conn.createStatement().executeQuery("select * from test;");
            while (resultSet.next()) {
                User u = new User();
                u.setName(resultSet.getString("name"));
                u.setLastName(resultSet.getString("lastname"));
                u.setAge((byte) resultSet.getInt("age"));

                u.setId((long) resultSet.getInt("id"));
                ul.add(u);
            }
            return ul;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection conn = util.connect()) {
            Statement statement = conn.createStatement();
            statement.execute("TRUNCATE table test");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
