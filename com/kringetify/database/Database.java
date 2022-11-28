package com.kringetify.database;

import java.sql.*;

public class Database {
    Connection conn;

    public Database() {
        try {
            this.conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/kringetify_soap_service",
                    "kringetify",
                    "soap_service"
            );
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
