package com.lly.common;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static java.lang.System.out;
import static java.lang.System.setOut;

public class HttpRequest {


    // todo 连接池

    private CloseableHttpClient httpClient;
    private CloseableHttpClient getHttpClient(){
        if(httpClient != null){
            return httpClient;
        }
        return HttpClients.createDefault();
    }

    public void getRequest(String url) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
//            EntityUtils.consume(entity);
//            String response = EntityUtils.toString(entity);
//            out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(httpResponse != null) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public InputStream postRequestWithJson(String url,String body) throws IOException {

        CloseableHttpClient httpClient = getHttpClient();
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();
        response = redirect(httpClient, entity, response, statusCode);
        HttpEntity httpEntity = response.getEntity();
//        System.out.println(EntityUtils.toString(httpEntity));;
        byte[] bytes = EntityUtils.toByteArray(httpEntity);
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        return stream;
    }

    public String postToString(String url ,String body) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();
        response = redirect(httpClient,entity,response,statusCode);
        HttpEntity httpEntity = response.getEntity();
        return EntityUtils.toString(httpEntity);
    }

    private CloseableHttpResponse redirect(CloseableHttpClient httpClient, StringEntity entity, CloseableHttpResponse response, int statusCode) throws IOException {
        HttpPost post;
        if(statusCode == HttpStatus.SC_MOVED_TEMPORARILY || statusCode == HttpStatus.SC_MOVED_PERMANENTLY){
            Header location = response.getFirstHeader("location");
            String newUrl = location.getValue();
            post = new HttpPost(newUrl);
            post.setEntity(entity);
            response = httpClient.execute(post);
        }
        return response;
    }

}
