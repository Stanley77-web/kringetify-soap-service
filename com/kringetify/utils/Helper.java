package com.kringetify.utils;

import com.kringetify.dao.LogDAO;
import com.kringetify.models.Log;
import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.internal.ws.api.message.Header;
import com.sun.xml.internal.ws.api.message.HeaderList;
import com.sun.xml.internal.ws.developer.JAXWSProperties;
import io.github.cdimascio.dotenv.Dotenv;

import javax.xml.ws.handler.MessageContext;

public class Helper {
    private Dotenv dotenv;
    private LogDAO logDAO;

    public Helper() {
        this.dotenv = Dotenv.load();
        this.logDAO = new LogDAO();
    }

    public String validate(MessageContext context, String desc) {
        HeaderList headerList = (HeaderList) context.get(JAXWSProperties.INBOUND_HEADER_LIST_PROPERTY);
        Header header = headerList.get("http://schemas.xmlsoap.org/soap/envelope/", "apikey");

        if (header == null) {
            return "Failed to make a request. Please set the header";
        }

        String apikey = header.getStringContent();
        if (apikey != null && apikey.equals(dotenv.get("APIKEY_KRINGETIFY_PREMIUM"))) {
            desc = "Kringetify premium " + desc;
        } else if (apikey != null && apikey.equals(dotenv.get("APIKEY_REST_SERVICE"))) {
            desc = "Rest service " + desc;
        } else {
            desc = "Failed to make a request. Please set correct apikey";
        }
        return desc;
    }

    public void writeToLog(MessageContext messageContext, String desc, String endpoint) {
        HttpExchange exchange = (HttpExchange) messageContext.get(JAXWSProperties.HTTP_EXCHANGE);
        String ip = exchange.getRemoteAddress().getHostName();
        long currentTimestamp = System.currentTimeMillis();

        this.logDAO.insert(new Log(desc, ip, endpoint, currentTimestamp));
    }
}
