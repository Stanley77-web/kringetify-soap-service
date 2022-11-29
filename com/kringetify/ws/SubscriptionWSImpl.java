package com.kringetify.ws;

import com.kringetify.handlers.SubscriptionHandler;
import com.kringetify.models.Subscription;

import javax.annotation.Resource;
import javax.jws.*;
import javax.xml.ws.WebServiceContext;
import java.util.List;

@WebService(endpointInterface = "com.kringetify.ws.SubscriptionWS")
public class SubscriptionWSImpl implements SubscriptionWS {
    SubscriptionHandler subscriptionHandler;
    @Resource
    WebServiceContext webServiceContext;

    public SubscriptionWSImpl() {
        this.subscriptionHandler = new SubscriptionHandler();
    }

    @Override
    public String createSubscription(int creator_id, int subscriber_id) {
        return this.subscriptionHandler.createSubscription(
                creator_id, subscriber_id, this.webServiceContext.getMessageContext());
    }

    @Override
    public String makeApproval(int creator_id, int subscriber_id, boolean approval) {
        return this.subscriptionHandler.makeApproval(
                creator_id, subscriber_id, approval, this.webServiceContext.getMessageContext());
    }

    @Override
    public List<Subscription> pendingSubscription() {
        return this.subscriptionHandler.pendingSubscription(this.webServiceContext.getMessageContext());
    }
}
