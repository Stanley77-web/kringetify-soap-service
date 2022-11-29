package com.kringetify.ws;

import com.kringetify.models.Subscription;

import javax.jws.*;
import java.util.List;

@WebService
@HandlerChain(file = "handler.xml")
public interface SubscriptionWS {
    @WebMethod
    public String createSubscription(@WebParam(name = "creator_id") int creator_id,
                                     @WebParam(name = "subscriber_id") int subscriber_id);
    @WebMethod
    public String makeApproval(@WebParam(name = "creator_id") int creator_id,
                               @WebParam(name = "subscriber_id") int subscriber_id,
                               @WebParam(name = "approval") boolean approval);
    @WebMethod
    public @WebResult(name = "SubsResponse") List<Subscription> pendingSubscription();
}
