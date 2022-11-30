package com.kringetify;

import com.kringetify.ws.impl.*;

import javax.xml.ws.*;

public class Main {
    public static void main(String[] args) {
        try {
            Endpoint.publish("http://127.0.0.1:8080/ws/subscription", new SubscriptionWSImpl());
            Endpoint.publish("http://127.0.0.1:8080/ws/checkstatus", new CheckStatusWSImpl());
            System.out.println("Complied");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
