package com.kringetify.ws;

import com.kringetify.models.Subscription;

import javax.jws.*;
import java.util.List;

@WebService
public interface SubscriptionWS {
    @WebMethod
    public String createRequest(@WebParam(name = "creator_id") int creator_id,
                                @WebParam(name = "subscriber_id") int subscriber_id);
    @WebMethod
    public String makeApproval(@WebParam(name = "creator_id") int creator_id,
                               @WebParam(name = "subscriber_id") int subscriber_id,
                               @WebParam(name = "approval") boolean approval);
    @WebMethod
    public @WebResult(name = "SubsResponse") List<Subscription> pendingRequest();
}
