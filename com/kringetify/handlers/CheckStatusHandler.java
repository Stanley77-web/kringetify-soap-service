package com.kringetify.handlers;

import com.kringetify.dao.LogDAO;
import com.kringetify.dao.SubscriptionDAO;
import com.kringetify.models.Status;
import com.kringetify.models.Subscription;
import com.kringetify.utils.Helper;
import io.github.cdimascio.dotenv.Dotenv;

import javax.xml.ws.handler.MessageContext;
import java.util.List;

public class CheckStatusHandler {
    private SubscriptionDAO subscriptionDAO;
    private Helper helper;

    public CheckStatusHandler() {
        this.subscriptionDAO = new SubscriptionDAO();
        this.helper = new Helper();
    }

    public Status subscriptionStatus(MessageContext context, Subscription subs) {
        String desc = "check status subscription";
        desc = this.helper.validate(context, desc);

        if ((desc.substring(0,6)).equals("Failed")) {
            System.out.println(desc);
            return null;
        }
        this.helper.writeToLog(context, desc, "/ws/checkstatus");
        return this.subscriptionDAO.findStatusById(subs);
    }

    public List<Subscription> userSubscription(MessageContext context, int subcriberId) {
        String desc = "check user subscription";
        desc = this.helper.validate(context, desc);

        if ((desc.substring(0,6)).equals("Failed")) {
            System.out.println(desc);
            return null;
        }
        this.helper.writeToLog(context, desc, "/ws/checkstatus");
        return this.subscriptionDAO.findById(subcriberId);
    }
}
