package com.kringetify.ws;

import com.kringetify.models.Status;
import com.kringetify.models.Subscription;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.awt.*;
import java.util.List;

@WebService
public interface CheckStatusWS {
    @WebMethod(operationName = "subscriptionstatus")
    public Status findStatusById(@WebParam(name = "creatorId") int creatorId,
                                 @WebParam(name = "subscriberId") int subscriberId);
    @WebMethod(operationName = "subscriptions") @WebResult(name = "subsResponse")
    public List<Subscription> findSubscriptionById(@WebParam(name = "subscriberId")  int subscriberId);
}
