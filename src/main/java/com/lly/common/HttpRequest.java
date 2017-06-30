package com.lly.common;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static java.lang.System.out;

public class HttpRequest {


    // todo Á¬½Ó³Ø

    private CloseableHttpClient httpClient;
    private CloseableHttpClient getHttpClient(){
        if(httpClient != null){
            return httpClient;
        }
        return HttpClients.createDefault();
    }

    public void getRequest(String url){
        CloseableHttpClient httpClient = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
//            EntityUtils.consume(entity);
            String response = EntityUtils.toString(entity);
            out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert httpResponse != null;
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void postRequest(String url,String params) throws UnsupportedEncodingException {
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("param", params));
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs);
        httpPost.setHeader("Cookie","crowd=dana;yiusr=j%3A%7B%22yiusr%22%3A%22dana%22%7D;yisid=j%3A%7B%22yisid%22%3A%22xkqw6iygghrlc3uz7hzrfm5dc4fropfsm2e2qiav%22%7D");
        httpPost.setEntity(urlEncodedFormEntity);
        CloseableHttpResponse httpResponse = null;
        try {
             httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            String response = EntityUtils.toString(entity);
            LogUtil.log(response);
            out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                LogUtil.log(e.getStackTrace().toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally {
                assert httpResponse != null;
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
