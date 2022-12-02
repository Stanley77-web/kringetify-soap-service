package com.kringetify.handlers;

import com.kringetify.dao.*;
import com.kringetify.models.Status;
import com.kringetify.models.Subscription;
import com.kringetify.utils.Helper;
import javax.xml.ws.handler.MessageContext;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionHandler {
    private SubscriptionDAO subscriptionDAO;
    private Helper helper;
    private CallbackHandler callbackHandler;
    private final String endPoint = "/ws/subscription";
    private final String URLCallback = "http://localhost:3000/api/update-subs";


    public SubscriptionHandler() {
        this.subscriptionDAO = new SubscriptionDAO();
        this.helper = new Helper();
    }
    public String createSubscription(int creator_id, int subscriber_id, MessageContext context) {
        Subscription subscription = new Subscription(creator_id, subscriber_id);
        String desc = subscription.toString();
        desc = this.helper.validate(context, desc);

        if ((desc).startsWith("Failed")) {
            System.out.println(desc);
            return desc;
        }
        this.helper.writeToLog(context, desc, this.endPoint);
        return this.subscriptionDAO.create(subscription);
    }

    public String makeApproval(int creatorId, int subscriberId, boolean approval, MessageContext context) {
        Subscription subscription = new Subscription(creatorId, subscriberId, approval ? Status.ACCEPTED : Status.REJECTED);
        String desc = (approval ? "accept " : "reject ") + subscriberId + " subscription request to " + creatorId;
        desc = this.helper.validate(context, desc);

        if ((desc).startsWith("Failed")) {
            System.out.println(desc);
            return desc;
        }
        this.helper.writeToLog(context, desc, this.endPoint);
        String res = this.subscriptionDAO.updateStatus(subscription);

        if (!res.startsWith("Failed")) {
            this.callbackHandler = new CallbackHandler(this.URLCallback);
            this.callbackHandler.setEntityBody(
                    creatorId, subscriberId, approval ?
                            Status.ACCEPTED: Status.REJECTED);
            res = this.callbackHandler.post();
        }
        return res;
    }

    public List<Subscription> subscriptions(MessageContext context, Status status) {
        List request = new ArrayList<>();
        String desc = "fetch all subscription";
        if (status != null) {
            String stringStatus = status.equals(Status.PENDING) ?
                                    "pending" :
                                        status.equals(Status.ACCEPTED) ?
                                                "accepted" : "rejected";
            desc = "fetch all " + stringStatus + " subscription";
        }

        desc = this.helper.validate(context, desc);

        if ((desc).startsWith("Failed")) {
            System.out.println(desc);
            return request;
        }
        this.helper.writeToLog(context, desc, this.endPoint);
        request = this.subscriptionDAO.findAllStatus(status);
        return request;
    }
}
