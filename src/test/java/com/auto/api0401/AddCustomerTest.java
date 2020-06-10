package com.auto.api0401;
/** 

* @author 作者 沙陌 

* @version 创建时间：2020年4月8日 下午8:56:57 

*/

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.auto.api0401.crmserver.AddCustomerService;
import com.auto.api0401.crmserver.LoginService;
import com.auto.crazyapi.utils.CrazyHttpClient;
import com.auto.crazyapi.utils.ExcelDataUtil;
import com.auto.crazyapi.utils.JSONPathUtil;
import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.PropertyUtil;
import com.auto.crazyapi.utils.Props;

public class AddCustomerTest extends CRMTestBase {
	
//	String host;
	String token;
//	Connection connection;
	
//	@BeforeClass
//	public void init() {
//		connection=JdbcDataUtil.getConn(url, username, password);
//		JdbcDataUtil.executeUpdate(connection, "DELETE FROM 72crm_crm_customer where customer_name='' OR customer_name like '%a%' OR customer_name like '%客户%'");//测试前清除之前的测试数据
//		//JdbcDataUtil.executeUpdate(connection, "DELETE FROM 72crm_crm_contacts where name='' OR name like '%a%' OR name like '%客户%'");//测试前清除之前的测试数据
//		System.out.println("开始执行测试");
//	}
//	@AfterClass
//	public void quit() {
//		JdbcDataUtil.closeConn(connection);
//	}
	@Test
	public void test001_login() throws Exception {
//		Map<String, String> headers=new HashMap<String, String>();
//		Map<String, String> params=new HashMap<String, String>();
//		params.put("username", "admin");
//		params.put("password", "123456");
//		String reponseString = CrazyHttpClient.sendPost(host+"/login", headers, params);
//		System.out.println(reponseString);
//		Assert.assertEquals(reponseString.contains("\"code\":0"), true);
//		JSONObject bodyJson=JSONObject.parseObject(reponseString);
//		token = bodyJson.getString("Admin-Token");
		String reponseString = LoginService.login("admin", "123456");
		Assert.assertEquals(reponseString.contains("\"code\":0"), true);
		JSONObject bodyJson=JSONObject.parseObject(reponseString);
		token = bodyJson.getString("Admin-Token");
		Props.put("token",token );
	}
	@Test
	public void test002_login() throws Exception {
		String reponseString = LoginService.login("admin", "1234567");
		System.out.println(reponseString);
		Assert.assertEquals(reponseString.contains("用户名或密码错误"), true);
	}
	@Test
	public void test003_login() throws Exception {
		String reponseString = LoginService.login("admin1", "123456");
		System.out.println(reponseString);
		Assert.assertEquals(reponseString.contains("用户名或密码错误"), true);
	}
	@Test
	public void test002_addCustomer() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);
//		String jsonString="{\r\n" + 
//				"	\"entity\": {\r\n" + 
//				"		\"customer_name\": \"客户123\",\r\n" + 
//				"		\"mobile\": \"18991112345\",\r\n" + 
//				"		\"telephone\": \"01028375678\",\r\n" + 
//				"		\"website\": \"http://testfan.cn\",\r\n" + 
//				"		\"next_time\": \"2020-04-02 00:00:00\",\r\n" + 
//				"		\"remark\": \"这是备注\",\r\n" + 
//				"		\"address\": \"北京市,北京城区,昌平区\",\r\n" + 
//				"		\"detailAddress\": \"霍营地铁\",\r\n" + 
//				"		\"location\": \"\",\r\n" + 
//				"		\"lng\": \"\",\r\n" + 
//				"		\"lat\": \"\"\r\n" + 
//				"	}\r\n" + 
//				"}";
		File jsonDataFile=new File("resources/data/crm/addCustomer.json");
		String jsonString = FileUtils.readFileToString(jsonDataFile,"UTF-8");
		JSONObject jsonData=JSONObject.parseObject(jsonString);
		JSONObject entity = jsonData.getJSONObject("entity");
		entity.put("customer_name", "abcd");
		jsonData.put("entity", entity);
		String json1=jsonData.toJSONString();
		System.out.println(json1);
		String reponseString = CrazyHttpClient.sendPostJsonOrXml(host+"/CrmCustomer/addOrUpdate", headers, json1);
		System.out.println(reponseString);
	}
	
	@Test
	public void test003_addCustomer() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Admin-Token", token);
		File jsonDataFile=new File("resources/data/crm/addCustomer.json");
		String jsonString = FileUtils.readFileToString(jsonDataFile,"UTF-8");
		JSONObject jsonData=JSONObject.parseObject(jsonString);
		JSONObject entity = jsonData.getJSONObject("entity");
		entity.remove("customer_name");
		jsonData.put("entity", entity);
		String json1=jsonData.toJSONString();
		System.out.println(json1);
		String reponseString = CrazyHttpClient.sendPostJsonOrXml(host+"/CrmCustomer/addOrUpdate", headers, json1);
		System.out.println(reponseString);
	}
	
	@Test
	public void test004_addCustomer() throws Exception {
		String res = AddCustomerService.addCustomer(token, "customer_name", "");
		Assert.assertEquals(res.contains("customer_name字段不能为空"), true);
	}
	@Test
	public void test005_addCustomer() throws Exception {
		String res = AddCustomerService.addCustomer(token, "mobile", "1891233456");
		Assert.assertEquals(res.contains("手机号格式不正确"), true);
	}
	@Test
	public void test006_addCustomer() throws Exception {
		String res = AddCustomerService.addCustomer(token, "mobile", "1891233456j");
		Assert.assertEquals(res.contains("手机号格式不正确"), true);
	}
	
	@DataProvider
	public Object[][] getAddCustomerData(){
		String property = System.getProperty("user.dir");
		Object[][] data = ExcelDataUtil.read(property+"/resources/excel/crmaddcustomer.xls", "addcustomer", 1, 0, 0, 0);
		return data;
	}
	
	@Test(dataProvider = "getAddCustomerData")
	public void test007_addCustomer(String casename,String json,String assertValue) throws Exception {
		String res = AddCustomerService.addCustomer(token,json);
		assertEquals(res.contains(assertValue), true);
	}
	
	@Test
	public void test008_addCustomer() throws Exception {
		//{"code":0,"data":{"customerName":"sdfff","customerId":34}}
		String res = AddCustomerService.addCustomer(token);
		//AddCustomerService.getData();
		String reqCustomerName=JSONPathUtil.extract(AddCustomerService.getData(),"$.entity.customer_name");
		String customerName=JSONPathUtil.extract(res,"$.data.customerName").toString();
		String customerId=JSONPathUtil.extract(res,"$.data.customerId").toString();
		Props.put("customerId", customerId);
		

		Object[][] data = JdbcDataUtil.getData(connection, "SELECT customer_name FROM 72crm_crm_customer where customer_id="+customerId);
		String dbCustomerName=data[0][0].toString();
		
		//Assert.assertEquals(res.contains("手机号格式不正确"), true);
		Assert.assertEquals(customerName, reqCustomerName);
		Assert.assertEquals(dbCustomerName, reqCustomerName);
	}

}
