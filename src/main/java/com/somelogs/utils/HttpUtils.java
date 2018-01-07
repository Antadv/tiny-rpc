package com.somelogs.utils;

import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * http util
 *
 * @author LBG - 2017/9/29 0029
 */
public class HttpUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";

    public static String get(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, Charset.forName(DEFAULT_CHARSET));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String postJson(String url, String postBody) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/json");
        httpPost.setEntity(new StringEntity(postBody, Charset.forName(DEFAULT_CHARSET)));

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, DEFAULT_CHARSET);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String postWithMap(String url, Map<String, Object> paramMap) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        if (MapUtils.isNotEmpty(paramMap)) {
            StringBuilder sb = new StringBuilder(paramMap.size());
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                sb.append(entry.getKey())
                  .append("=")
                  .append(entry.getValue())
                  .append("&");
            }
            String params = sb.toString();
            params = params.substring(0, params.length() - 1);
            httpPost.setEntity(new StringEntity(params, DEFAULT_CHARSET));
        }

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            URI uri = httpPost.getURI();
            System.out.println(uri);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, DEFAULT_CHARSET);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
