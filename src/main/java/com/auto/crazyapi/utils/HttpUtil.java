package com.auto.crazyapi.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;




public class HttpUtil {
	public static void main(String[] args) throws Exception {
		System.out.println(HttpUtil.getURL("/config.properties", "url_prefix", "/http/getCaptchaFromSession"));
	}

	public static String getURL(String urlPrex, String path) {
		return urlPrex + path;
	}

	public static String getURL(String propFile, String prefix, String path) {
		Properties prop = PropertyUtil.getProperties(propFile);
		String urlPrex = prop.getProperty(prefix);
		return urlPrex + path;
	}
	//{"id":"1","age":"18","job":"tester"}
	public static String getQueryParam(Map<String, String> queryMap) throws UnsupportedEncodingException {

		StringBuilder accum = new StringBuilder();
		//&id=1&age=18&job=tester
		for (Map.Entry<String, String> entry : queryMap.entrySet()) {
			accum.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
		}
		String queryParam = accum.toString().replaceFirst("&", "?");//?id=1&age=18&job=tester
		return queryParam;

	}

	public static UrlEncodedFormEntity getFormEntity(Map<String,String> map){

		List<NameValuePair> paramList = new ArrayList<>();
		for(Map.Entry<String, String> entry : map.entrySet()){
			paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
	
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,
				Charset.forName("utf-8"));
		return entity; 
	}
	public static StringEntity getDataFromFile(String dataPath) throws Exception {
		File dataFile=new File(dataPath);
		String data=FileUtils.readFileToString(dataFile);
		StringEntity dataEntity=new StringEntity(data, "UTF-8");
		return dataEntity;
	} 
}