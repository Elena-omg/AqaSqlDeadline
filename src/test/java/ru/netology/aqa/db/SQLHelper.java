package ru.netology.aqa.db;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/app";
    private static final String USER = "app";
    private static final String PASS = "pass";
    private static final QueryRunner runner = new QueryRunner();

    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    @SneakyThrows
    public static String getVerificationCode(String login) {
        try (var conn = getConnection()) {
            String sql = "SELECT code FROM auth_codes " +
                    "WHERE user_id = (SELECT id FROM users WHERE login = ?) " +
                    "ORDER BY created DESC LIMIT 1";
            return runner.query(conn, sql, new ScalarHandler<>(), login);
        }
    }

    @SneakyThrows
    public static void setUserPassword(String login, String hashedPassword) {
        try (var conn = getConnection()) {
            runner.update(conn, "UPDATE users SET password = ? WHERE login = ?", hashedPassword, login);
        }
    }
        @SneakyThrows
        public static void clearData () {
            try (var conn = getConnection()) {
                runner.update(conn, "DELETE FROM auth_codes");
                runner.update(conn, "DELETE FROM card_transactions");
                runner.update(conn, "DELETE FROM cards");
            }
        }
    }
