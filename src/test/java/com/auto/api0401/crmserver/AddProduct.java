package com.auto.api0401.crmserver;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;
import com.auto.crazyapi.utils.CrazyHttpClient;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年4月13日 下午8:09:57 

*/
public class AddProduct extends BaseService{
	static String jsonfile="resources/data/crm/addProduct.json";
	static String uriString="/CrmProduct/saveAndUpdate";
	
	
	public static String addProduct(String token) throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);

		String json1=getData();
		System.out.println(json1);
		String reponseString = CrazyHttpClient.sendPostJsonOrXml(host+uriString, headers, json1);
		System.out.println(reponseString);
		return reponseString;
	}
	
	public static String addProduct(String token,String json) throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);

//		String json1=getData();
//		System.out.println(json1);
		String reponseString = CrazyHttpClient.sendPostJsonOrXml(host+uriString, headers, json);
		System.out.println(reponseString);
		return reponseString;
	}
	
	public static String addProduct(String token,List<String> removefieldList) throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);

		String json=getData(removefieldList);
//		System.out.println(json1);
		String reponseString = CrazyHttpClient.sendPostJsonOrXml(host+uriString, headers, json);
		System.out.println(reponseString);
		return reponseString;
	}
	
	public static String addProduct(String token, String key, Object value) throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);

		String json1=getData(key,value);
		System.out.println(json1);
		String reponseString = CrazyHttpClient.sendPostJsonOrXml(host+uriString, headers, json1);
		System.out.println(reponseString);
		return reponseString;
	}
	
	public static String getData(String key,Object value) throws Exception {
		File jsonDataFile=new File(jsonfile);
		String jsonString = FileUtils.readFileToString(jsonDataFile,"UTF-8");
		JSONObject jsonData=JSONObject.parseObject(jsonString);
		JSONObject entity = jsonData.getJSONObject("entity");
		//entity.remove("Product_name");
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
//		//entity.remove("Product_name");
//		entity.put(key, value);
//		jsonData.put("entity", entity);
//		String json1=jsonData.toJSONString();
		return jsonString;
	}
	public static String getData(List<String> fieldList) throws Exception {
		File jsonDataFile=new File(jsonfile);
		String jsonString = FileUtils.readFileToString(jsonDataFile,"UTF-8");
		JSONObject jsonData=JSONObject.parseObject(jsonString);
		JSONObject entity = jsonData.getJSONObject("entity");
		//entity.remove("Product_name");
		for (String field : fieldList) {
			entity.remove(field);
		}
		jsonData.put("entity", entity);
		String json1=jsonData.toJSONString();
		return json1;
	}
}
