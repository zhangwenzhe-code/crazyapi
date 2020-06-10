package com.mtx.crmserver;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.auto.crazyapi.utils.CrazyHttpClient;
import com.auto.crazyapi.utils.JSONPathUtil;
import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.PropertyUtil;

/**
*@author  zhangwenzhe
*@date  2020年5月31日---下午2:32:12
*/
public class LoginService extends BaseService{
	private static String url="/login";
	private static String paramPath=userdir+"/resources/data/crm/login.properties";
	
	public static Map<String, String> getData(){
		HashMap<String,String> allKeyValue = PropertyUtil.getAllKeyValue(paramPath);
		return allKeyValue;
	}
	public static Map<String, String> getData(String key,String value){
		HashMap<String,String> allKeyValue = PropertyUtil.getAllKeyValue(paramPath);
		allKeyValue.replace(key, value);
		return allKeyValue;
	}
	public static Map<String, String> getData(String key){
		HashMap<String,String> allKeyValue = PropertyUtil.getAllKeyValue(paramPath);
		allKeyValue.remove(key);
		return allKeyValue;
	}
	public static String login() throws Exception {
		Map<String, String> headers = new HashMap<>();
		String response = CrazyHttpClient.sendPost(host+url, headers , getData());
		return response;
	}
	public static String login(String key,String value) throws Exception {
		Map<String, String> headers = new HashMap<>();
		String response = CrazyHttpClient.sendPost(host+url, headers , getData(key,value));
		return response;
	}
	public static String login(String key) throws Exception {
		Map<String, String> headers = new HashMap<>();
		String response = CrazyHttpClient.sendPost(host+url, headers , getData(key));
		return response;
	}
	public static String getToken() throws Exception {
		String response = login();
		String token = JSONPathUtil.extract(response, "$.Admin-Token");
		return token;
	}
	
}
