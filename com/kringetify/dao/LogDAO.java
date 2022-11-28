package com.kringetify.dao;

import com.kringetify.database.Database;
import com.kringetify.models.Log;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class LogDAO {
    private final Database db;

    public LogDAO() {
        this.db = new Database();
    }

    public void insert(Log log) {
        String query = "INSERT INTO logging (description, IP, endpoint, requested_at) VALUES (?,?,?,?)";
        PreparedStatement stmt = db.prepare(query);
        try {
            System.out.println(log);
            stmt.setString(1, log.getDescription());
            stmt.setString(2, log.getIP());
            stmt.setString(3, log.getEndpoint());
            stmt.setTimestamp(4, log.getRequestedAt());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to Insert. " + e.getMessage());
        }
    }

}
