package com.mtx.les0524;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;

import org.apache.commons.io.FileUtils;
import org.apache.http.entity.StringEntity;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.auto.crazyapi.utils.CrazyHttpClient;
import com.auto.crazyapi.utils.JSONPathUtil;
import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.PropertyUtil;

import net.minidev.json.JSONArray;

/**
*@author  zhangwenzhe
*@date  2020年5月31日---上午9:48:26
*/
public class CRMTest {
	private String token;
	private String host;
	private Connection conn;
	private String property;
	@BeforeClass
	public void init() {
		host="http://localhost:8088";
		//连接crm数据库
		property = System.getProperty("user.dir");
		System.out.println(property);
		Properties dbProperties = PropertyUtil.getProperties(property+"/resources/db.properties");
		String url = dbProperties.getProperty("jdbc.crm.url");
		String username = dbProperties.getProperty("jdbc.crm.username");
		String password = dbProperties.getProperty("jdbc.crm.password");
		conn = JdbcDataUtil.getConn(url, username,password);
		//conn=JdbcDataUtil.getConn("jdbc:mysql://localhost:3306/crm?characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false", "root", "123");
	}
	@Test
	public void test001_CRMlogin() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		Map<String, String> params=new HashMap<String, String>();
		params.put("username", "admin");
		params.put("password", "123456");
		String response = CrazyHttpClient.sendPost(host+"/login", headers, params);
		System.out.println(response);
		Assert.assertTrue(response.contains("\"code\":0"));
		JSONObject resObject = JSONObject.parseObject(response);
		token = resObject.getString("Admin-Token");
	}
	//@Test(description="采用读取properties文件读取方式-正确用例")
	public void test005_CRMlogin() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		HashMap<String,String> params = PropertyUtil.getAllKeyValue(property+"/resources/data/crm/login.properties");
		for(Map.Entry<String, String> entry:params.entrySet()){  
	        System.out.println(entry.getKey()+"="+entry.getValue());  
	    } 
		String response = CrazyHttpClient.sendPost(host+"/login", headers, params);
		System.out.println(response);
		Assert.assertTrue(response.contains("\"code\":0"));
		JSONObject resObject = JSONObject.parseObject(response);
		token = resObject.getString("Admin-Token");
	}
	//@Test(description="采用读取properties文件读取方式-用户名字段缺失")
	public void test006_CRMlogin() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		HashMap<String,String> params = PropertyUtil.getAllKeyValue(property+"/resources/data/crm/login.properties");
		params.remove("username");
		for(Map.Entry<String, String> entry:params.entrySet()){  
	        System.out.println(entry.getKey()+"="+entry.getValue());  
	    } 
		String response = CrazyHttpClient.sendPost(host+"/login", headers, params);
		System.out.println(response);
		Assert.assertTrue(response.contains("\"code\":500"));
		JSONObject resObject = JSONObject.parseObject(response);
		token = resObject.getString("Admin-Token");
	}
	//@Test(description="采用读取properties文件读取方式-密码字段缺失")
	public void test007_CRMlogin() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		HashMap<String,String> params = PropertyUtil.getAllKeyValue(property+"/resources/data/crm/login.properties");
		params.remove("password");
		for(Map.Entry<String, String> entry:params.entrySet()){  
	        System.out.println(entry.getKey()+"="+entry.getValue());  
	    } 
		String response = CrazyHttpClient.sendPost(host+"/login", headers, params);
		System.out.println(response);
		Assert.assertTrue(response.contains("\"code\":500"));
		JSONObject resObject = JSONObject.parseObject(response);
		token = resObject.getString("Admin-Token");
	}
	//@Test(description="采用读取properties文件读取方式-用户名不正确")
	public void test008_CRMlogin() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		HashMap<String,String> params = PropertyUtil.getAllKeyValue(property+"/resources/data/crm/login.properties");
		params.replace("username", "admin1");
		for(Map.Entry<String, String> entry:params.entrySet()){  
	        System.out.println(entry.getKey()+"="+entry.getValue());  
	    } 
		String response = CrazyHttpClient.sendPost(host+"/login", headers, params);
		System.out.println(response);
		Assert.assertTrue(response.contains("\"code\":500"));
		JSONObject resObject = JSONObject.parseObject(response);
		token = resObject.getString("Admin-Token");
	}
	//@Test(description="采用读取properties文件读取方式-密码不正确")
	public void test009_CRMlogin() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		HashMap<String,String> params = PropertyUtil.getAllKeyValue(property+"/resources/data/crm/login.properties");
		params.replace("password", "admin1");
		for(Map.Entry<String, String> entry:params.entrySet()){  
	        System.out.println(entry.getKey()+"="+entry.getValue());  
	    } 
		String response = CrazyHttpClient.sendPost(host+"/login", headers, params);
		System.out.println(response);
		Assert.assertTrue(response.contains("\"code\":500"));
		JSONObject resObject = JSONObject.parseObject(response);
		token = resObject.getString("Admin-Token");
	}
	//@Test
	public void test002_CRMSelectCustomer() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);
		String params="{\n" + 
				"	\"page\": 1,\n" + 
				"	\"limit\": 5,\n" + 
				"	\"search\": \"\",\n" + 
				"	\"type\": 2\n" + 
				"}";
		String response = CrazyHttpClient.sendPostJsonOrXml(host+"/CrmCustomer/queryPageList", headers, params);
//		Assert.assertTrue(response.contains("\"code\":0"));
		System.out.println(response);
		JSONArray customerName = JSONPathUtil.extract(response, "$..customerName");
		JSONArray customerId = JSONPathUtil.extract(response, "$..customerId");
//		for(int i=0;i<customerName.size();i++) {
//			System.out.println("customerId"+customerId.get(i));
//		}
		Object[][] data = JdbcDataUtil.getData(conn, "SELECT customer_id,customer_name from 72crm_crm_customer ORDER BY customer_id DESC LIMIT 5");
		/*for(int i=0;i<data.length;i++) {
			for(int j=0;j<data[i].length;j++) {
				System.out.println(i+":"+j+":"+data[i][j].toString());
			}
		}*/
		Assert.assertEquals(customerName.size(), data.length);
		for(int i=0;i<data.length;i++) {
			for(int j=0;j<data[i].length;j++) {
				if (j==0) {
					//System.out.println(customerId.get(i)+":"+data[i][j].toString());
					Assert.assertEquals(customerId.get(i).toString(), data[i][j].toString());
				}else {
					//System.out.println(customerName.get(i)+":"+data[i][j].toString());
					Assert.assertEquals(customerName.get(i), data[i][j].toString());
				}
				
			}
		}
	}
	@Test
	public void test003_CRMAddCustomer() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);
		String params="{\n" + 
				"	\"entity\": {\n" + 
				"		\"customer_name\": \"客户123\",\n" + 
				"		\"mobile\": \"18991112345\",\n" + 
				"		\"telephone\": \"01028375678\",\n" + 
				"		\"website\": \"http://testfan.cn\",\n" + 
				"		\"next_time\": \"2020-04-02 00:00:00\",\n" + 
				"		\"remark\": \"这是备注\",\n" + 
				"		\"address\": \"北京市,北京城区,昌平区\",\n" + 
				"		\"detailAddress\": \"霍营地铁\",\n" + 
				"		\"location\": \"\",\n" + 
				"		\"lng\": \"\",\n" + 
				"		\"lat\": \"\"\n" + 
				"	}\n" + 
				"}";
		String response = CrazyHttpClient.sendPostJsonOrXml(host+"/CrmCustomer/addOrUpdate", headers, params);
		System.out.println(response);
		JSONObject parseObject = JSONObject.parseObject(response);
		JSONObject dataObject = parseObject.getJSONObject("data");
		String customerName = dataObject.getString("customerName");
		String customerId = dataObject.getString("customerId");
			
		Object[][] data = JdbcDataUtil.getData(conn, "SELECT customer_id,customer_name from 72crm_crm_customer ORDER BY customer_id DESC LIMIT 1");
		String id=data[0][0].toString();
		String name=data[0][1].toString();
//		
		
		Assert.assertEquals(id, customerId);
		Assert.assertEquals(name, customerName);
	}
	//@Test
	public void test004_CRMAddCustomer() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);
		File dataFile=new File(property+"/resources/data/crm/addCustomer.json");
		String params = FileUtils.readFileToString(dataFile);
		JSONObject paramJsonObject = JSONObject.parseObject(params);
		JSONObject entity = paramJsonObject.getJSONObject("entity");
		//替换
		entity.replace("customer_name", "客户1221");
		//再重新放回去
		//params="{\"entity\":"+entity.toString()+"}";
		String response = CrazyHttpClient.sendPostJson(host+"/CrmCustomer/addOrUpdate", headers, paramJsonObject);
		System.out.println(response);
		JSONObject parseObject = JSONObject.parseObject(response);
		JSONObject dataObject = parseObject.getJSONObject("data");
		String customerName = dataObject.getString("customerName");
		String customerId = dataObject.getString("customerId");
		System.out.println("customerName:"+customerName);
		
		Object[][] data = JdbcDataUtil.getData(conn, "SELECT customer_id,customer_name from 72crm_crm_customer ORDER BY customer_id DESC LIMIT 1");
		String id=data[0][0].toString();
		String name=data[0][1].toString();			
		//System.out.println("name:"+name);
		
		Assert.assertEquals(id, customerId);
		Assert.assertEquals(name, customerName);
	}
	@AfterClass
	public void close() {
		JdbcDataUtil.closeConn(conn);
	}
}
