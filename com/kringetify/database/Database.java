package com.kringetify.database;

import java.sql.*;

public class Database {
    Connection conn;

    public Database() {
        try {
            String url = System.getenv("JDBC_URL") == null || System.getenv("DATABASE_NAME") == null ?
                    "jdbc:mysql://localhost:3306/kringetify_soap_service" :
                    System.getenv("JDBC_URL") + System.getenv("DATABASE_NAME");

            String user = System.getenv("JDBC_USER") == null ?
                    "kringetify" : System.getenv("JDBC_USER");

            String password = System.getenv("JDBC_PASS") == null ?
                    "soap_service" : System.getenv("JDBC_PASS");

            this.conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Failed to connect");
            System.out.println(e.getMessage());
        }
    }

    public PreparedStatement prepare(String query) {
        try {
            return this.conn.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println("Failed to prepare");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            System.out.println("Failed to close connection");
            System.out.println(e.getMessage());
        }
    }

}
