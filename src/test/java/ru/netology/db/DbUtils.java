package ru.netology.db;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtils {

    private DbUtils() {}

    private static final String DB_URL = System.getProperty("db.url");
    private static final String DB_USER = "app";
    private static final String DB_PASSWORD = System.getProperty("db.password", "App!Secur3#2026");

    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        QueryRunner runner = new QueryRunner();
        String sql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (Connection conn = getConnection()) {
            return runner.query(conn, sql, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static String getCreditStatus() {
        QueryRunner runner = new QueryRunner();
        String sql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (Connection conn = getConnection()) {
            return runner.query(conn, sql, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static int getOrderCount() {
        QueryRunner runner = new QueryRunner();
        String sql = "SELECT COUNT(*) FROM order_entity";
        try (Connection conn = getConnection()) {
            Number count = runner.query(conn, sql, new ScalarHandler<>());
            return count != null ? count.intValue() : 0;
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        QueryRunner runner = new QueryRunner();
        try (Connection conn = getConnection()) {
            runner.update(conn, "DELETE FROM payment_entity");
            runner.update(conn, "DELETE FROM credit_request_entity");
            runner.update(conn, "DELETE FROM order_entity");
        }
    }
}