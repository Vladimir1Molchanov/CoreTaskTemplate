package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService us = new UserServiceImpl();
        us.createUsersTable();
        us.saveUser("Ted", "Rockfield", (byte) 23);
        us.saveUser("Michael", "Wood", (byte) 45);
        us.saveUser("Bob", "Jonson", (byte) 37);
        us.saveUser("Nick", "Crook", (byte) 19);
        System.out.println(us.getAllUsers());
        us.cleanUsersTable();
        us.dropUsersTable();
    }
}
