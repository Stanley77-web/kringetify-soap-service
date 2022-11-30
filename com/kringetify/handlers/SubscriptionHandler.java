package com.kringetify.handlers;

import com.kringetify.dao.*;
import com.kringetify.models.Log;
import com.kringetify.models.Status;
import com.kringetify.models.Subscription;
import com.kringetify.utils.Helper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.internal.ws.api.message.Header;
import com.sun.xml.internal.ws.api.message.HeaderList;
import com.sun.xml.internal.ws.developer.JAXWSProperties;
import io.github.cdimascio.dotenv.Dotenv;

import javax.xml.ws.handler.MessageContext;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionHandler {
    private SubscriptionDAO subscriptionDAO;
    private Helper helper;

    public SubscriptionHandler() {
        this.subscriptionDAO = new SubscriptionDAO();
        this.helper = new Helper();
    }
    public String createSubscription(int creator_id, int subscriber_id, MessageContext context) {
        Subscription subscription = new Subscription(creator_id, subscriber_id);
        String desc = subscription.toString();
        desc = this.helper.validate(context, desc);

        if ((desc.substring(0,6)).equals("Failed")) {
            System.out.println(desc);
            return desc;
        }
        this.helper.writeToLog(context, desc, "/ws/subscription");
        return this.subscriptionDAO.create(subscription);
    }

    public String makeApproval(int creator_id, int subscriber_id, boolean approval, MessageContext context) {
        Subscription subscription = new Subscription(creator_id, subscriber_id, approval ? Status.ACCEPTED : Status.REJECTED);
        String desc = (approval ? "accept " : "reject ") + subscriber_id + " subscription request to " + creator_id;
        desc = this.helper.validate(context, desc);

        if ((desc.substring(0,6)).equals("Failed")) {
            System.out.println(desc);
            return desc;
        }
        this.helper.writeToLog(context, desc, "/ws/subscription");
        return this.subscriptionDAO.updateStatus(subscription);
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

        if ((desc.substring(0,6)).equals("Failed")) {
            System.out.println(desc);
            return request;
        }
        this.helper.writeToLog(context, desc, "/ws/subscription");
        request = this.subscriptionDAO.findAllStatus(status);
        return request;
    }
}
