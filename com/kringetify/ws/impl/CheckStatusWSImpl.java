package com.kringetify.ws.impl;

import com.kringetify.handlers.CheckStatusHandler;
import com.kringetify.models.Status;
import com.kringetify.models.Subscription;
import com.kringetify.ws.CheckStatusWS;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.List;

@WebService(endpointInterface = "com.kringetify.ws.CheckStatusWS")
public class CheckStatusWSImpl implements CheckStatusWS {
    private CheckStatusHandler checkStatusHandler;
    @Resource
    private WebServiceContext webServiceContext;

    public CheckStatusWSImpl() {
        this.checkStatusHandler = new CheckStatusHandler();
    }

    @Override
    public Status findStatusById(int creatorId, int subscriberId) {
        return this.checkStatusHandler.subscriptionStatus(
                this.webServiceContext.getMessageContext(), new Subscription(creatorId, subscriberId));
    }
    @Override
    public List<Subscription> findSubscriptionById(int subscriberId) {
        return this.checkStatusHandler.userSubscription(
                this.webServiceContext.getMessageContext(), subscriberId);
    }
}
