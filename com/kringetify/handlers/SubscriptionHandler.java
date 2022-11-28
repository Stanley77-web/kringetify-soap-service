package com.kringetify.handlers;

import com.kringetify.dao.*;
import com.kringetify.models.Log;
import com.kringetify.models.Status;
import com.kringetify.models.Subscription;
import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.internal.ws.developer.JAXWSProperties;

import javax.xml.ws.handler.MessageContext;
import java.util.List;

public class SubscriptionHandler {
    private SubscriptionDAO subscriptionDAO;
    private LogDAO logDAO;

    public SubscriptionHandler() {
        this.subscriptionDAO = new SubscriptionDAO();
        this.logDAO = new LogDAO();
    }
    public String createRequest(int creator_id, int subscriber_id, MessageContext context) {
        Subscription subscription = new Subscription(creator_id, subscriber_id);
        this.writeToLog(context, subscription.toString());
        return this.subscriptionDAO.create(subscription);
    }

    public String makeApproval(int creator_id, int subscriber_id, boolean approval, MessageContext context) {
        Subscription subscription = new Subscription(creator_id, subscriber_id, approval ? Status.ACCEPTED : Status.REJECTED);
        String description = (approval ? "Accept " : "Reject ") + subscriber_id + " subscription request to " + creator_id;
        this.writeToLog(context, description);
        return this.subscriptionDAO.updateStatus(subscription);
    }

    public List<Subscription> pendingRequest(MessageContext context) {
        this.writeToLog(context, "fetch all pending Request");
        return this.subscriptionDAO.findAllWithStatus(Status.PENDING);
    }

    private void writeToLog(MessageContext messageContext, String description) {
        HttpExchange exchange = (HttpExchange) messageContext.get(JAXWSProperties.HTTP_EXCHANGE);
        String ip = exchange.getRemoteAddress().getHostName();

        String endpoint = (String) messageContext.get(MessageContext.PATH_INFO);
        endpoint = endpoint == null ? "http://ws.kringetify.com/" : endpoint;

        long currentTimestamp = System.currentTimeMillis();
        this.logDAO.insert(new Log(description, ip, endpoint, currentTimestamp));
    }

}
