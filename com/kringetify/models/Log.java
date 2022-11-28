package com.kringetify.models;

import java.sql.Timestamp;

public class Log {
    private String description;
    private String IP;
    private String endpoint;
    private Timestamp requestedAt;

    public Log(String description, String IP, String endpoint, long requested_at) {
        this.description = description;
        this.IP = IP;
        this.endpoint = endpoint;
        this.requestedAt = new Timestamp(requested_at);
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setIP(String IP) {
        this.IP = IP;
    }
    public String getIP() {
        return IP;
    }
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    public String getEndpoint() {
        return endpoint;
    }
    public void setRequested_at(Timestamp requested_at) {
        this.requestedAt = requested_at;
    }
    public void setRequested_at(long requested_at) {
        this.requestedAt = new Timestamp(requested_at);
    }
    public Timestamp getRequestedAt() {
        return requestedAt;
    }
    @Override
    public String toString() {
        String log =
                this.requestedAt.toString() +
                " | " + this.IP + " | " +
                this.endpoint + " | " +
                this.description;
        return log;
    }
}
