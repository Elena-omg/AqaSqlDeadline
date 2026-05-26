package ru.netology.aqa.db;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DbUtils {
    private static final DataSource ds;

    static {
        MysqlDataSource mds = new MysqlDataSource();
        mds.setUrl("jdbc:mysql://localhost:3306/app");
        mds.setUser("app");
        mds.setPassword("pass");
        ds = mds;
    }

    public static String getVerificationCode(String login) throws SQLException {
        QueryRunner runner = new QueryRunner(ds);
        String sql = "SELECT code FROM auth_codes " +
                "WHERE user_id = (SELECT id FROM users WHERE login = ?) " +
                "ORDER BY created DESC LIMIT 1";
        return runner.query(sql, new ScalarHandler<>(), login);
    }

    public static void clearData() throws SQLException {
        QueryRunner runner = new QueryRunner(ds);
        runner.update("DELETE FROM auth_codes");
        runner.update("DELETE FROM card_transactions");
        runner.update("DELETE FROM cards");
        runner.update("DELETE FROM users");
    }

    public static void setUserPassword(String login, String hashedPassword) throws SQLException {
        QueryRunner runner = new QueryRunner(ds);
        runner.update("UPDATE users SET password = ? WHERE login = ?", hashedPassword, login);
    }
}