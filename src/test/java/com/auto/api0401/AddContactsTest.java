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
import com.auto.api0401.crmserver.AddContacts;
import com.auto.api0401.crmserver.AddCustomerService;
import com.auto.api0401.crmserver.LoginService;
import com.auto.crazyapi.utils.CrazyHttpClient;
import com.auto.crazyapi.utils.ExcelDataUtil;
import com.auto.crazyapi.utils.JSONPathUtil;
import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.PropertyUtil;
import com.auto.crazyapi.utils.Props;

public class AddContactsTest extends CRMTestBase {
	
//	String host;
//	//String token;
//	Connection connection;
	
//	@BeforeClass
//	public void init() {
//		connection=JdbcDataUtil.getConn(url, username, password);
//		//JdbcDataUtil.executeUpdate(connection, "DELETE FROM 72crm_crm_customer where customer_name='' OR customer_name like '%a%' OR customer_name like '%客户%'");//测试前清除之前的测试数据
//		JdbcDataUtil.executeUpdate(connection, "DELETE FROM 72crm_crm_contacts where name='' OR name like '%a%' OR name like '%客户%'");//测试前清除之前的测试数据
//		System.out.println("开始执行测试");
//	}
//	@AfterClass
//	public void quit() {
//		JdbcDataUtil.closeConn(connection);
//	}
	@Test
	public void test001_addContacts() throws Exception {
		// TODO Auto-generated method stub
		String resString = AddContacts.addContact(Props.get("token"), "customer_id", Props.get("customerId"));
		Assert.assertEquals(resString, "{\"code\":0}");
		String name = JSONPathUtil.extract(AddContacts.getData(), "$.entity.name").toString();
		//SELECT `name` FROM 72crm_crm_contacts ORDER BY contacts_id DESC LIMIT 1
		Object[][] data = JdbcDataUtil.getData(connection, "SELECT `name` FROM 72crm_crm_contacts ORDER BY contacts_id DESC LIMIT 1");
		String expectName=data[0][0].toString();
		Assert.assertEquals(name, expectName);

	}

}
