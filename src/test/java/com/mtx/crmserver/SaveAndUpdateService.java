package com.mtx.crmserver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;
import com.auto.crazyapi.utils.CrazyHttpClient;
import com.auto.crazyapi.utils.Props;
import com.auto.crazyapi.utils.RandomUtil;

/**
*@author  zhangwenzhe
*@date  2020年6月1日---上午9:12:44
*新建商品
*/
public class SaveAndUpdateService extends BaseService{
	private static String url="/CrmProduct/saveAndUpdate";
	static String paramsFilePath=userdir+"/resources/data/crm/saveAndUpdate.json";//参数配置文件路径
	public static String saveAndUpdate(String token) throws Exception {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);
		String res = CrazyHttpClient.sendPostJson(host+url, headers, getData());
		return res;
	}
	public static String saveAndUpdate(String token, String key) throws Exception {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);
		String res = CrazyHttpClient.sendPostJson(host+url, headers, getData(key));
		return res;
	}
	public static String saveAndUpdateNum(String token, String key) throws Exception {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);
		String res = CrazyHttpClient.sendPostJson(host+url, headers, getDataNum());
		return res;
	}
	public static JSONObject getDataNum() throws Exception {
		File dataFile=new File(paramsFilePath);
		String params = FileUtils.readFileToString(dataFile);
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		return paramJsonObject;
	}
	
	public static JSONObject getData() throws Exception {
		File dataFile=new File(paramsFilePath);
		String params = FileUtils.readFileToString(dataFile);
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		JSONObject entity = paramJsonObject.getJSONObject("entity");
		String num = entity.getString("num");
		num = RandomUtil.getRndNumByLen(5);
		entity.replace("num", num);
		System.out.println("paramJsonObject:"+paramJsonObject.toJSONString());
		Props.put("num", num);
		return paramJsonObject;
	}
	private static JSONObject getData(String key) throws Exception {
		File dataFile=new File(paramsFilePath);
		String params = FileUtils.readFileToString(dataFile);
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		JSONObject entity = paramJsonObject.getJSONObject("entity");
		
		//paramJsonObject.replace("entity", entity);
		String num = entity.getString("num");
		num = RandomUtil.getRndNumByLen(5);
		entity.replace("num", num);
		System.out.println("paramJsonObject:"+paramJsonObject.toJSONString());
		Props.put("num", num);
		entity.remove(key);
		return paramJsonObject;
	}
	public static String saveAndUpdate(String token, String key, String value) throws Exception {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);
		String res = CrazyHttpClient.sendPostJson(host+url, headers, getData(key,value));
		return res;
		
	}
	private static JSONObject getData(String key, String value) throws Exception {
		File dataFile=new File(paramsFilePath);
		String params = FileUtils.readFileToString(dataFile);
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		JSONObject entity = paramJsonObject.getJSONObject("entity");
		//paramJsonObject.replace("entity", entity);
		String num = entity.getString("num");
		num = RandomUtil.getRndNumByLen(5);
		entity.replace("num", num);
		System.out.println("paramJsonObject:"+paramJsonObject.toJSONString());
		Props.put("num", num);
		entity.replace(key, value);
		return paramJsonObject;
	}
	
}
