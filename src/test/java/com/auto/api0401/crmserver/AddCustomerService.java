package com.auto.api0401.crmserver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;
import com.auto.crazyapi.utils.CrazyHttpClient;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年4月8日 下午9:46:07 

*/
public class AddCustomerService extends BaseService{
	static String jsonfile="resources/data/crm/addCustomer.json";
	
	
	public static String addCustomer(String token) throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);

		String json1=getData();
		System.out.println(json1);
		String reponseString = CrazyHttpClient.sendPostJsonOrXml(host+"/CrmCustomer/addOrUpdate", headers, json1);
		System.out.println(reponseString);
		return reponseString;
	}
	
	public static String addCustomer(String token,String json) throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);

//		String json1=getData();
//		System.out.println(json1);
		String reponseString = CrazyHttpClient.sendPostJsonOrXml(host+"/CrmCustomer/addOrUpdate", headers, json);
		System.out.println(reponseString);
		return reponseString;
	}
	
	public static String addCustomer(String token, String key, Object value) throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);

		String json1=getData(key,value);
		System.out.println(json1);
		String reponseString = CrazyHttpClient.sendPostJsonOrXml(host+"/CrmCustomer/addOrUpdate", headers, json1);
		System.out.println(reponseString);
		return reponseString;
	}
	
	public static String getData(String key,Object value) throws Exception {
		File jsonDataFile=new File(jsonfile);
		String jsonString = FileUtils.readFileToString(jsonDataFile,"UTF-8");
		JSONObject jsonData=JSONObject.parseObject(jsonString);
		JSONObject entity = jsonData.getJSONObject("entity");
		//entity.remove("customer_name");
		entity.put(key, value);
		jsonData.put("entity", entity);
		String json1=jsonData.toJSONString();
		return json1;
	}
	public static String getData() throws Exception {
		File jsonDataFile=new File(jsonfile);
		String jsonString = FileUtils.readFileToString(jsonDataFile,"UTF-8");
//		JSONObject jsonData=JSONObject.parseObject(jsonString);
//		JSONObject entity = jsonData.getJSONObject("entity");
//		//entity.remove("customer_name");
//		entity.put(key, value);
//		jsonData.put("entity", entity);
//		String json1=jsonData.toJSONString();
		return jsonString;
	}
}
