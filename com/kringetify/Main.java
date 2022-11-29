package com.kringetify;

import com.kringetify.ws.SubscriptionWSImpl;

import javax.xml.ws.*;

public class Main {
    public static void main(String[] args) {
        try {
            Endpoint.publish("http://localhost:4790/ws/subscription", new SubscriptionWSImpl());
            System.out.println("Complied");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
