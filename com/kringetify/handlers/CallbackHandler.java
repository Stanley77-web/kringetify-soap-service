package com.kringetify.handlers;

import com.kringetify.models.Status;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.http.message.StatusLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CallbackHandler {
    private HttpPost httpPost;

    public CallbackHandler(String URL) {
        this.httpPost = new HttpPost(URL);
    }

    public void setEntityBody(int creatorId, int subscriberId, Status status) {
        List<NameValuePair> listNVP = new ArrayList<>();
        listNVP.add(new BasicNameValuePair("creator-id", Integer.toString(creatorId)));
        listNVP.add(new BasicNameValuePair("subscriber-id", Integer.toString(subscriberId)));
        listNVP.add(new BasicNameValuePair("status", status.toString()));
        this.httpPost.setEntity(new UrlEncodedFormEntity(listNVP));
    }

    public String post() {
        String res;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(this.httpPost);

            System.out.println(response.getCode() + " | " + response.getReasonPhrase() + " -version " + response.getVersion());

            HttpEntity entity = response.getEntity();
            res = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return "Failed. Internal server error";
        }
        return res;
    }
}
