package com.kringetify.handlers;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.MessageContext.Scope;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;

public class AuthenticationHandler implements SOAPHandler<SOAPMessageContext> {
    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        try {
            SOAPHeader soapHeader = context.getMessage().getSOAPHeader();

            if (soapHeader == null) {
                System.out.println("Header not set");
            }

            NodeList apikeyNode = soapHeader.getElementsByTagNameNS("*", "apikey");
            String apikey = apikeyNode.item(0).getChildNodes().item(0).getNodeValue();

            if (apikey == null) {
                System.out.println("Apikey not set");
            }

            context.put("apikey", apikey);
            context.setScope("apikey", Scope.APPLICATION);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}
