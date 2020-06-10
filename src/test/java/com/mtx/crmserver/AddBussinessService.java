package com.mtx.crmserver;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;
import com.auto.crazyapi.utils.CrazyHttpClient;
import com.auto.crazyapi.utils.JSONPathUtil;
import com.auto.crazyapi.utils.JdbcDataUtil;

import net.minidev.json.JSONArray;

/**
*@author  zhangwenzhe
*@date  2020年6月2日---下午10:15:59
* 新建商机
*/
public class AddBussinessService extends BaseService{
	private static String url="/CrmBusiness/addOrUpdate";
	static String paramsFilePath=userdir+"/resources/data/crm/addAndUpdateBussiness.json";//参数配置文件路径
	
	public static String addAndUpdateBussiness(String token) throws Exception {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);
		String res = CrazyHttpClient.sendPostJson(host+url, headers, getData());
		return res;
	}
	
	public static JSONObject getData() throws IOException {
		File dataFile=new File(paramsFilePath);
		String params = FileUtils.readFileToString(dataFile);
		System.out.println(params);
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		return paramJsonObject;
	}

	public static String addAndUpdateBussiness(String token, String key) throws Exception{
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);
		String res = CrazyHttpClient.sendPostJson(host+url, headers, getData(key));
		return res;
	}

	private static JSONObject getData(String key) throws Exception {
		File dataFile=new File(paramsFilePath);
		String params = FileUtils.readFileToString(dataFile);
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		JSONObject entity = paramJsonObject.getJSONObject("entity");
		entity.remove(key);
		System.out.println(paramJsonObject.toJSONString());
		return paramJsonObject;
	}

	public static String addAndUpdateBussiness(String token, String key, String value) throws Exception {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);
		String res = CrazyHttpClient.sendPostJson(host+url, headers, getData(key,value));
		return res;
	}

	public static JSONObject getData(String key, String value) throws IOException {
		File dataFile=new File(paramsFilePath);
		String params = FileUtils.readFileToString(dataFile);
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		JSONObject entity = paramJsonObject.getJSONObject("entity");
		entity.replace(key,value);
		System.out.println(paramJsonObject.toJSONString());
		return paramJsonObject;
	}
}
