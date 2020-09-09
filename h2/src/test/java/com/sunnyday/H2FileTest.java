package com.sunnyday;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

/**
 * 持久化模式
 *
 * @author TMW
 * @date 2020/9/9 15:15
 */
public class H2FileTest {

    private static Connection connection;

    final static String DDL_CREATE_TABLE = "CREATE TABLE `user` (\n" +
            "  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,\n" +
            "  `name` VARCHAR(45) NOT NULL ,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ")";

    @BeforeAll
    public static void init() throws SQLException {
        // connection = DriverManager.getConnection("jdbc:h2:file:./db/h2_mem_test;MODE=MYSQL");
        connection = DriverManager.getConnection("jdbc:h2:file:./db/h2_mem_test;MODE=MYSQL", "admin", "123456");
    }

    @Test
    public void createTest() throws SQLException {
        final Statement statement = connection.createStatement();
        final boolean execute = statement.execute(DDL_CREATE_TABLE);
        statement.close();
        System.out.println("创建表成功");
    }

    @Test
    public void insertTest() throws SQLException {
        final Statement statement = connection.createStatement();
        statement.execute("insert into user(name) values('张三')");
        statement.execute("insert into user(name) values('李四')");
        System.out.println("插入数据成功");
        statement.close();
    }

    @Test
    public void findAllTest() throws SQLException {
        final Statement statement = connection.createStatement();
        final ResultSet resultSet = statement.executeQuery("select * from user;");
        while (resultSet.next()) {
            System.out.println("id: " + resultSet.getLong("id") + "   name: " + resultSet.getString("name"));
        }
        resultSet.close();
        statement.close();
    }

    @AfterAll
    public static void destroy() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
