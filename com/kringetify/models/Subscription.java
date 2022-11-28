package com.kringetify.models;

public class Subscription {
    private int creatorId;
    private int subscriberId;
    private Status status;

    public Subscription(int creatorId, int subscriberId) {
        this.creatorId = creatorId;
        this.subscriberId = subscriberId;
    }

    public Subscription(int creatorId, int subscriberId, Status status) {
        this.creatorId = creatorId;
        this.subscriberId = subscriberId;
        this.status = status;
    }

    public void setCreatorId(int creator_id) {
        this.creatorId = creator_id;
    }
    public int getCreatorId() {
        return this.creatorId;
    }
    public void setSubscriberId(int subscriber_id) {
        this.subscriberId = subscriber_id;
    }
    public int getSubscriberId() {
        return this.subscriberId;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Status getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        String request = "";
        if (this.status == Status.ACCEPTED || this.status == Status.REJECTED || this.status == Status.PENDING) {
            request = "Subscribe request " + this.subscriberId + " to " + this.creatorId +
                    (this.status.equals(Status.ACCEPTED) ?
                            " accepted" : this.status.equals(Status.REJECTED) ?
                                            " rejected" : " pending");
        } else {
            request = this.subscriberId + " request to subscribe " + this.creatorId;
        }
        return request;
    }
}
