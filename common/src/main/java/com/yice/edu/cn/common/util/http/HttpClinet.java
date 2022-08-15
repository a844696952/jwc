package com.yice.edu.cn.common.util.http;


import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class HttpClinet {

    /**
     * get请求
     * @return
     **/
    public static String get (String url) {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * post请求，参数为json字符串
     * @param url 请求地址
     * @param jsonString json字符串
     * @return 响应
     */
    public static String postJson(String url,String jsonString)
    {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")));
            response = httpClient.execute(post);
            if(response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
                if(response != null)
                {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
