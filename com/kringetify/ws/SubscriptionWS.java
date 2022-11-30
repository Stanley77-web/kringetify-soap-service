package com.kringetify.ws;

import com.kringetify.models.Status;
import com.kringetify.models.Subscription;

import javax.jws.*;
import java.util.List;

@WebService(name = "subs", targetNamespace = "http://www.kringetify.com/ws/")
public interface SubscriptionWS {
    @WebMethod(operationName = "create")
    public String createSubscription(@WebParam(name = "creatorId") int creatorId,
                                     @WebParam(name = "subscriberId") int subscriberId);
    @WebMethod(operationName = "approval")
    public String makeApproval(@WebParam(name = "creatorId") int creatorId,
                               @WebParam(name = "subscriberId") int subscriberId,
                               @WebParam(name = "approval") boolean approval);
    @WebMethod(operationName = "pendingstatus") @WebResult(name = "SubsResponse")
    public List<Subscription> findPendingSubscriptions();
    @WebMethod(operationName = "allstatus") @WebResult(name = "subsResponse")
    public List<Subscription> findAllSubscriptions();
}
