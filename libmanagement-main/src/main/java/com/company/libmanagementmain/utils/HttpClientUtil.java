package com.company.libmanagementmain.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.company.libmanagementmain.LibmanagementMainApplication;
import com.company.libmanagementmain.entity.LibResultInfo;
import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    private static final Logger log = LoggerFactory.getLogger(LibmanagementMainApplication.class);

    public static LibResultInfo getRequestReturnLibResultInfo(String path, List<NameValuePair> parametersBody, Map<String, String> headerMap) throws Exception {
        String responseString = getRequest(path,parametersBody, headerMap);
        return JSON.parseObject(responseString, new TypeReference<LibResultInfo>(){});
    }

    // 发送GET请求
    public static String getRequest(String path, List<NameValuePair> parametersBody, Map<String, String> headerMap) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(path);
        uriBuilder.setParameters(parametersBody);

        HttpGet get = new HttpGet(uriBuilder.build());
        HttpClient client = HttpClientBuilder.create().build();

        if (headerMap != null && headerMap.size() > 0){
            for (Map.Entry<String, String> entry : headerMap.entrySet()){
                get.addHeader(entry.getKey(), entry.getValue());
            }
        }
        try {
            HttpResponse response = client.execute(get);
            int code = response.getStatusLine().getStatusCode();
            if (code >= 400){
                System.out.println("http code : " + String.valueOf(code));
                throw new RuntimeException((new StringBuilder()).append("Could not access protected resource. Server returned http code: ").append(code).toString());
            }
            return EntityUtils.toString(response.getEntity());
        }
        catch (ClientProtocolException e) {
            throw e;
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            get.releaseConnection();
        }
    }

    // 发送POST请求（普通表单形式）
    public static String postForm(String path, List<NameValuePair> parametersBody) throws Exception {
        HttpEntity entity = new UrlEncodedFormEntity(parametersBody, Charsets.UTF_8);
        return postRequest(path, "application/x-www-form-urlencoded", entity);
    }

    public static LibResultInfo postFormReturnObject(String path, List<NameValuePair> parametersBody) throws Exception {
        HttpEntity entity = new UrlEncodedFormEntity(parametersBody, Charsets.UTF_8);
        String responseString = postRequest(path, "application/x-www-form-urlencoded", entity);
        return JSON.parseObject(responseString, new TypeReference<LibResultInfo>(){});
    }

    public static LibResultInfo postFormReturnObject(String path, List<NameValuePair> parametersBody, Map<String, String> headerMap) throws Exception {
        HttpEntity entity = new UrlEncodedFormEntity(parametersBody, Charsets.UTF_8);
        String responseString = postRequest(path, "application/x-www-form-urlencoded", entity, headerMap);
        return JSON.parseObject(responseString, new TypeReference<LibResultInfo>(){});
    }

    // 发送POST请求（JSON形式）
    public static String postJSON(String path, String json) throws Exception {
        StringEntity entity = new StringEntity(json, Charsets.UTF_8);
        return postRequest(path, "application/json", entity);
    }

    public static String postRequest(String path, String mediaType, HttpEntity entity) throws Exception {
        return postRequest(path,mediaType, entity, null);
    }

    // 发送POST请求
    public static String postRequest(String path, String mediaType, HttpEntity entity, Map<String, String> headerMap) throws Exception {
        log.debug("[postRequest] resourceUrl: {}", path);
        HttpPost post = new HttpPost(path);
        post.addHeader("Content-Type", mediaType);
        post.addHeader("Accept", "application/json");
        if (headerMap != null && headerMap.size() > 0){
            for (Map.Entry<String, String> entry : headerMap.entrySet()){
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }
        post.setEntity(entity);
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code >= 400) {
                System.out.println("http code : " + String.valueOf(code));
                throw new Exception();
            }
            return EntityUtils.toString(response.getEntity());
        }
        catch (ClientProtocolException e) {
            throw e;
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            post.releaseConnection();
        }
    }

    public static String deleteRequest(String path, String mediaType, HttpEntity entity) throws Exception {
        return deleteRequest(path,"application/x-www-form-urlencoded", entity, null);
    }

    public static String deleteRequest(String path, String mediaType, HttpEntity entity, Map<String, String> headerMap) throws Exception {
        log.debug("[deleteRequest] resourceUrl: {}", path);
        HttpDeleteWithBody delete = new HttpDeleteWithBody(path);
        delete.addHeader("Content-Type", mediaType);
        delete.addHeader("Accept", "application/json");
        if (headerMap != null && headerMap.size() > 0){
            for (Map.Entry<String, String> entry : headerMap.entrySet()){
                delete.addHeader(entry.getKey(), entry.getValue());
            }
        }

        delete.setEntity(entity);
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(delete);
            int code = response.getStatusLine().getStatusCode();
            if (code >= 400){
                System.out.println("http code : " + String.valueOf(code));
                throw new Exception();
            }

            return EntityUtils.toString(response.getEntity());
        }
        catch (ClientProtocolException e) {
            throw e;
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            delete.releaseConnection();
        }
    }
}

