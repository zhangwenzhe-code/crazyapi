package com.mtx.crmserver;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import com.alibaba.fastjson.JSONObject;
import com.auto.crazyapi.utils.CrazyHttpClient;
import com.auto.crazyapi.utils.JSONPathUtil;
import com.auto.crazyapi.utils.JdbcDataUtil;

import net.minidev.json.JSONArray;

/**
*@author  zhangwenzhe
*@date  2020年5月31日---下午3:07:13
*/
public class AddCustomerService extends BaseService{
	private static String url="/CrmCustomer/addOrUpdate";
	private static Connection conn;

	public static JSONObject getData() throws IOException {
		File dataFile=new File(userdir+"/resources/data/crm/addCustomer.json");
		String params = FileUtils.readFileToString(dataFile);
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		return paramJsonObject;
	}
	public static String addCustomer(String token) throws Exception {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);
		
		String response = CrazyHttpClient.sendPostJson(host+url, headers, getData());
		return response;
	}
	public static String addCustomer(String token,String key,String value) throws Exception {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", LoginService.getToken());
		//解析
		JSONObject paramJsonObject = getData();
		JSONObject entity = paramJsonObject.getJSONObject("entity");
		//替换
		entity.replace(key, value);
		String response = CrazyHttpClient.sendPostJson(host+url, headers, paramJsonObject);
		return response;
	}
	public static String addCustomer(String token,String key) throws Exception {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", LoginService.getToken());
		
		JSONObject paramJsonObject = getData();
		JSONObject entity = paramJsonObject.getJSONObject("entity");
		//替换
		entity.remove(key);
		String response = CrazyHttpClient.sendPostJson(host+url, headers, paramJsonObject);
		return response;
	}
	public static Object[][] getDbData(String sql){
		conn = JdbcDataUtil.getConn(dburl, dbusername,dbpassword);
		Object[][] data = JdbcDataUtil.getData(conn, sql);
		return data;
	}
	public static JSONArray getResValue(String res,String key) {
		JSONArray extract = JSONPathUtil.extract(res, "$.."+key);
		return extract;
	}
}
