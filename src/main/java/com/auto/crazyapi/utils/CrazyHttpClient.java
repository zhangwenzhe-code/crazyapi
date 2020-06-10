package com.auto.crazyapi.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年2月11日 下午5:32:49 

*/
public class CrazyHttpClient {
	
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static SSLConnectionSocketFactory sslsf = null;
    private static PoolingHttpClientConnectionManager cm = null;
    private static SSLContextBuilder builder = null;
    private static HttpClient client;
    private static HttpGet httpGet;
    private static HttpPost httpPost;
    private static HttpResponse response;
    private static CookieStore cookieStore;
	

    static {
        try {
        	cookieStore=new BasicCookieStore();
            builder = new SSLContextBuilder();
            // 全部信任 不做身份鉴定
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(),
                    new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HTTP, new PlainConnectionSocketFactory()).register(HTTPS, sslsf).build();
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(200);// max connection
            client = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(cm)
    				.setConnectionManagerShared(true).setDefaultCookieStore(cookieStore).build();
//            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String sendGet(String url,Map<String,String> headers,Map<String,String> params) throws Exception {
    	String queryParam = HttpUtil.getQueryParam(params);//?id=1&age=18&job=tester
    	httpGet=new HttpGet(url+queryParam);//http://localhost:8080/pinter/com/getSku?id=1&age=18&job=tester
    	if(!headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
            	httpGet.addHeader(entry.getKey(), entry.getValue());
            }
    	}
    	response = client.execute(httpGet);
    	return EntityUtils.toString(response.getEntity());
    }
    public static String sendPost(String url,Map<String,String> headers,Map<String,String> params) throws Exception {
    	UrlEncodedFormEntity formEntity = HttpUtil.getFormEntity(params);
    	httpPost=new HttpPost(url);
    	if(!headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
            	httpPost.addHeader(entry.getKey(), entry.getValue());
            }
    	}
    	httpPost.setEntity(formEntity);
    	response = client.execute(httpPost);
    	return EntityUtils.toString(response.getEntity());
    }
    
    public static String sendPostJsonOrXml(String url,Map<String,String> headers,String param) throws Exception {
    	httpPost=new HttpPost(url);
    	if(!headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
            	httpPost.addHeader(entry.getKey(), entry.getValue());
            }
    	}
    	StringEntity entity=new StringEntity(param, "UTF-8");
    	httpPost.setEntity(entity);
    	response = client.execute(httpPost);
    	return EntityUtils.toString(response.getEntity());
    }
    public static String sendPostJson(String url,Map<String,String> headers,JSONObject param) throws Exception {
    	httpPost=new HttpPost(url);
    	if(!headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
            	httpPost.addHeader(entry.getKey(), entry.getValue());
            }
    	}
    	StringEntity entity=new StringEntity(param.toJSONString(), "UTF-8");
    	httpPost.setEntity(entity);
    	response = client.execute(httpPost);
    	return EntityUtils.toString(response.getEntity());
    }
    
    public static String sendUpload(String url,Map<String,String> headers,String filePath) throws Exception {
    	FileBody bin = new FileBody(new File(filePath));
    	httpPost=new HttpPost(url);
    	if(!headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
            	httpPost.addHeader(entry.getKey(), entry.getValue());
            }
    	}
    	MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		HttpEntity reqEntity=builder.addPart("file", bin).build();
		httpPost.setEntity(reqEntity);
    	response = client.execute(httpPost);
    	return EntityUtils.toString(response.getEntity());
    }
    public static List<Cookie>  getCookies() {
    	List<Cookie> cookies = cookieStore.getCookies();
//    	System.out.println(cookies.size());
//    	for(Cookie c:cookies) {
//    		System.out.println(c.getName()+":"+c.getValue());
//    	}
    	return cookies;
    }
    public static Header[] getResponseHeaders() {
    	Header[] allHeaders = response.getAllHeaders();
//    	
//    	for(Header h:allHeaders) {
//    		System.out.println(h.getName()+":"+h.getValue());
//    	}
    	return allHeaders;
    }
    public static int getResponseStatusCode() {
    	int statusCode = response.getStatusLine().getStatusCode();
    	return statusCode;
    }
}
