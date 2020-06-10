package com.mtx.les0524;
/**
*@author  zhangwenzhe
*@date  2020年5月31日---下午3:17:28
*/

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.auto.crazyapi.utils.JSONPathUtil;
import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.Props;
import com.mtx.crmserver.AddCustomerService;
import com.mtx.crmserver.LoginService;

import net.minidev.json.JSONArray;

public class CrmAddCustomerTests {
	private String token;
	@BeforeClass
	public void init() throws Exception {
		token = LoginService.getToken();
	}
	@Test(description="正确添加")
	public void test001_addCustomer() throws Exception {
		String addresponse = AddCustomerService.addCustomer(token);
		
		JSONArray customerName = JSONPathUtil.extract(addresponse, "$..customerName");
		
		JSONObject parseObject = JSONObject.parseObject(addresponse);
		JSONObject dataObject = parseObject.getJSONObject("data");
//		String customerName = dataObject.getString("customerName");
		String customerId = dataObject.getString("customerId");
		
		Object[][] data = AddCustomerService.getDbData("SELECT customer_id,customer_name from 72crm_crm_customer ORDER BY customer_id DESC LIMIT 1");
		String id=data[0][0].toString();
		String name=data[0][1].toString();
		
		Assert.assertEquals(id, customerId);
		Assert.assertEquals(name, customerName.get(0).toString());
		Props.put("customer_id", id);
	}
	@Test(description="客户名称空")
	public void test002_addCustomer() throws Exception {
//		String token = LoginService.getToken();
		String addresponse = AddCustomerService.addCustomer(token,"customer_name","");
		JSONObject parseObject = JSONObject.parseObject(addresponse);
		JSONObject dataObject = parseObject.getJSONObject("data");
		String customerName = dataObject.getString("customerName");
		String customerId = dataObject.getString("customerId");
		
		Object[][] data = AddCustomerService.getDbData("SELECT customer_id,customer_name from 72crm_crm_customer ORDER BY customer_id DESC LIMIT 1");
		String id=data[0][0].toString();
		String name=data[0][1].toString();
		
		Assert.assertEquals(id, customerId);
		Assert.assertEquals(name, customerName);
	}
	@Test(description="客户名称字段缺失")
	public void test003_addCustomer() throws Exception {
//		String token = LoginService.getToken();
		String addresponse = AddCustomerService.addCustomer(token,"customer_name");
		System.out.println("3:"+addresponse);
		Assert.assertTrue(addresponse.contains("服务器响应异常"));
	}
}
