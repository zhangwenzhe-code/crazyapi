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

import net.minidev.json.JSONArray;

/**
*@author  zhangwenzhe
*@date  2020年5月31日---下午5:11:53
*/
public class AddContactsService extends BaseService{
	private static String url="/CrmContacts/addOrUpdate";
	static String paramsFilePath=userdir+"/resources/data/crm/addContacts.json";//参数配置文件路径
	
	public static String addContacts(String token) throws Exception {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);
		String res = CrazyHttpClient.sendPostJson(host+url, headers, getData());
		return res;
	}
	public static String addContacts(String token,String key) throws Exception {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);
		String res = CrazyHttpClient.sendPostJson(host+url, headers, getData(key));
		return res;
	}
	public static String addContacts(String token,String key,String value) throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("content-type", "application/json");
		headers.put("Admin-Token", token);
		String res = CrazyHttpClient.sendPostJson(host+url, headers, getData(key,value));
		return res;
	}

	public static JSONObject getData() throws IOException {
		File dataFile=new File(paramsFilePath);
		String params = FileUtils.readFileToString(dataFile);
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		return paramJsonObject;
	}
	public static JSONObject getData(String key) throws IOException {
		JSONObject data = getData();
		JSONObject entityObject = data.getJSONObject("entity");
		entityObject.remove(key);
		data.replace("entity", entityObject);
		return data;
	}
	public static JSONObject getData(String key,String value) throws IOException {
		JSONObject data = getData();
		JSONObject entityObject = data.getJSONObject("entity");
		entityObject.replace(key, value);
		data.replace("entity", entityObject);
		return data;
	}
	public static JSONArray getResValue(String res,String key) {
		return JSONPathUtil.extract(res, "$.."+key);
	}
	public static JSONObject getDataOnlyRequres() throws IOException {
		JSONObject data = getData();
		JSONObject entityJson = data.getJSONObject("entity");
		String string = entityJson.getString("name");
		JSONObject json=JSONObject.parseObject("{\"name\":\""+string+"\"}");
		data.replace("entity", json);
		return data;
	}
}
