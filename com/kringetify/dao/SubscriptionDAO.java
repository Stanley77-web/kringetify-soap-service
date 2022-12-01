package com.kringetify.dao;

import com.kringetify.database.Database;
import com.kringetify.models.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

public class SubscriptionDAO {
    private Database db;

    public SubscriptionDAO() {    }

    public String create(Subscription subs) {
        this.db = new Database();
        try {
            String query = "INSERT INTO subscription (creator_id, subscriber_id) VALUES (?,?)";
            PreparedStatement stmt = db.prepare(query);
            stmt.setInt(1, subs.getCreatorId());
            stmt.setInt(2, subs.getSubscriberId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            this.db.close();
            return  "Failed. Request has been made";
        }
        this.db.close();
        return "Success to make a request";
    }

    public String updateStatus(Subscription subs) {
        this.db = new Database();
        String query = "SELECT * FROM subscription WHERE creator_id = ? AND subscriber_id = ?";
        try {
            PreparedStatement stmt = db.prepare(query);
            stmt.setInt(1, subs.getCreatorId());
            stmt.setInt(2, subs.getSubscriberId());
            ResultSet resultSet = stmt.executeQuery();
            
            if (!resultSet.next()) {
                return "Failed to " + subs.getStatus().toString().toLowerCase()
                        + " " + subs.getSubscriberId() + " subscribe request to " + subs.getCreatorId() + ". Request not found";
            } else if (!resultSet.getString(3).equals(Status.PENDING.toString())) {
                return "Failed to " + subs.getStatus().toString().toLowerCase()
                        + " " + subs.getSubscriberId() + " subscribe request to " + subs.getCreatorId()
                        + ". Already " + (resultSet.getString(3).equals("ACCEPTED") ? "accept" : "reject")
                        + " this subscription request";
            }

            query = "UPDATE subscription SET status = ? WHERE creator_id = ? AND subscriber_id = ?";
            stmt = db.prepare(query);

            stmt.setString(1, subs.getStatus().toString());
            stmt.setInt(2, subs.getCreatorId());
            stmt.setInt(3, subs.getSubscriberId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            this.db.close();
            return "Failed to " + subs.getStatus().toString().toLowerCase()
                    + " " + subs.getSubscriberId() + " subscribe request to " + subs.getCreatorId() + ". " + e.getMessage();
        }
        this.db.close();
        return "Success to " + subs.getStatus().toString().toLowerCase()
                + " " + subs.getSubscriberId() + " subscribe request to " + subs.getCreatorId();
    }

    public List<Subscription> findAllStatus(Status status) {
        this.db = new Database();
        List<Subscription> subscriptionList = new ArrayList<>();
        String query = "SELECT * FROM subscription";
        boolean all = true;

        if (status != null) {
            query = "SELECT * FROM subscription WHERE status = ?";
            all = false;
        }
        try {
            PreparedStatement stmt = db.prepare(query);
            if (status != null) {
                stmt.setString(1, status.toString());
            }
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int creator_id = resultSet.getInt(1);
                int subscriber_id = resultSet.getInt(2);

                if (all) {
                    String stringStatus = resultSet.getString(3);

                    status = stringStatus.equals("PENDING") ?
                            Status.PENDING :
                            stringStatus.equals("ACCEPTED") ?
                                    Status.ACCEPTED : Status.REJECTED;
                }

                subscriptionList.add(new Subscription(creator_id, subscriber_id, status));
            }
        } catch (SQLException e) {
            this.db.close();
            System.out.println("Failed to fetch data. " + e.getMessage());
        }
        this.db.close();
        return subscriptionList;
    }

    public Status findStatusById(Subscription subs) {
        this.db = new Database();
        String query = "SELECT * FROM subscription WHERE creator_id = ? AND subscriber_id = ?";
        Status status = null;

        try {
            PreparedStatement stmt = db.prepare(query);
            stmt.setInt(1, subs.getCreatorId());
            stmt.setInt(2, subs.getSubscriberId());
            ResultSet resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                System.out.println("Failed. Don't have any subscription");
                return status;
            }

            query = "SELECT status FROM subscription WHERE creator_id = ? AND subscriber_id = ?";

            stmt = db.prepare(query);
            stmt.setInt(1, subs.getCreatorId());
            stmt.setInt(2, subs.getSubscriberId());

            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String stringStatus = resultSet.getString(1);
                status = stringStatus.equals("PENDING") ?
                        Status.PENDING :
                        stringStatus.equals("ACCEPTED") ?
                                Status.ACCEPTED : Status.REJECTED;
            }
        } catch (SQLException e) {
            this.db.close();
            System.out.println("Failed to fetch data. " + e.getMessage());
        }
        this.db.close();
        return status;
    }

    public List<Subscription> findById(int subscriberId) {
        this.db = new Database();
        List<Subscription> subscriptionList = new ArrayList<>();
        String query = "SELECT * FROM subscription WHERE subscriber_id = ?";
        try {
            PreparedStatement stmt = db.prepare(query);
            stmt.setInt(1, subscriberId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int creator_id = resultSet.getInt(1);
                int subscriber_id = resultSet.getInt(2);
                String stringStatus = resultSet.getString(3);

                Status status = stringStatus.equals("PENDING") ?
                                    Status.PENDING :
                                        stringStatus.equals("ACCEPTED") ?
                                                Status.ACCEPTED : Status.REJECTED;

                subscriptionList.add(new Subscription(creator_id, subscriber_id, status));
            }
        } catch (SQLException e) {
            this.db.close();
            System.out.println("Failed to fetch data. " + e.getMessage());
        }
        this.db.close();
        return subscriptionList;
    }
}
