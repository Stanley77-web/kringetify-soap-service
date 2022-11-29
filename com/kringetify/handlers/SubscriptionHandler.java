package com.kringetify.handlers;

import com.kringetify.dao.*;
import com.kringetify.models.Log;
import com.kringetify.models.Status;
import com.kringetify.models.Subscription;
import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.internal.ws.developer.JAXWSProperties;
import io.github.cdimascio.dotenv.Dotenv;

import javax.xml.ws.handler.MessageContext;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionHandler {
    private SubscriptionDAO subscriptionDAO;
    private LogDAO logDAO;
    private Dotenv dotenv;

    public SubscriptionHandler() {
        this.subscriptionDAO = new SubscriptionDAO();
        this.logDAO = new LogDAO();
        this.dotenv = Dotenv.load();
    }
    public String createSubscription(int creator_id, int subscriber_id, MessageContext context) {
        Subscription subscription = new Subscription(creator_id, subscriber_id);
        String apikey = (String) context.get("apikey");
        String desc = subscription.toString();

        if (apikey != null && apikey.equals(dotenv.get("APIKEY_KRINGETIFY_PREMIUM"))) {
            desc = "Kringetify premium " + desc;
        } else if (apikey != null && apikey.equals(dotenv.get("APIKEY_REST_SERVICE"))) {
            desc = "rest service " + desc;
        } else {
            return "failed to make a request. Please set correct apikey";
        }

        this.writeToLog(context, desc);
        return this.subscriptionDAO.create(subscription);
    }

    public String makeApproval(int creator_id, int subscriber_id, boolean approval, MessageContext context) {
        Subscription subscription = new Subscription(creator_id, subscriber_id, approval ? Status.ACCEPTED : Status.REJECTED);
        String apikey = (String) context.get("apikey");
        String desc = (approval ? "accept " : "reject ") + subscriber_id + " subscription request to " + creator_id;

        if (apikey != null && apikey.equals(dotenv.get("APIKEY_KRINGETIFY_PREMIUM"))) {
            desc = "Kringetify premium " + desc;
        } else if (apikey != null && apikey.equals(dotenv.get("APIKEY_REST_SERVICE"))) {
            desc = "Rest service " + desc;
        } else {
            return "Failed to make a request. Please set correct apikey";
        }

        this.writeToLog(context, desc);
        return this.subscriptionDAO.updateStatus(subscription);
    }

    public List<Subscription> pendingSubscription(MessageContext context) {
        List pendingRequest = new ArrayList<>();
        String apikey = (String) context.get("apikey");
        String desc = "fetch all pending Request";

        if (apikey != null && apikey.equals(dotenv.get("APIKEY_KRINGETIFY_PREMIUM"))) {
            desc = "Kringetify premium " + desc;
        } else if (apikey != null && apikey.equals(dotenv.get("APIKEY_REST_SERVICE"))) {
            desc = "Rest service " + desc;
        } else {
            System.out.println("Failed to make a request. Please set correct apikey");
            return pendingRequest;
        }

        this.writeToLog(context, desc);
        pendingRequest = this.subscriptionDAO.findAllWithStatus(Status.PENDING);
        return pendingRequest;
    }

    private void writeToLog(MessageContext messageContext, String description) {
        HttpExchange exchange = (HttpExchange) messageContext.get(JAXWSProperties.HTTP_EXCHANGE);
        String ip = exchange.getRemoteAddress().getHostName();

        String endpoint = (String) messageContext.get(MessageContext.PATH_INFO);
        endpoint = endpoint == null ? "/ws/subscription" : endpoint;

        long currentTimestamp = System.currentTimeMillis();
        this.logDAO.insert(new Log(description, ip, endpoint, currentTimestamp));
    }
}
