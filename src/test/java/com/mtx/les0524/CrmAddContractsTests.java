package com.mtx.les0524;

import java.sql.Connection;
import java.util.Properties;

import org.aspectj.weaver.VersionedDataInputStream;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.PropertyUtil;
import com.auto.crazyapi.utils.Props;
import com.mtx.crmserver.AddContactsService;
import com.mtx.crmserver.LoginService;

/**
*@author  zhangwenzhe
*@date  2020年5月31日---下午5:26:05
*/
public class CrmAddContractsTests {
	String token;
	private Connection conn;
	@BeforeClass
	public void init() throws Exception {
		String userdir=System.getProperty("user.dir");
		Properties dbProperties = PropertyUtil.getProperties(userdir+"/resources/db.properties");
		String dburl = dbProperties.getProperty("jdbc.crm.url");
		String dbusername = dbProperties.getProperty("jdbc.crm.username");
		String dbpassword = dbProperties.getProperty("jdbc.crm.password");
		conn = JdbcDataUtil.getConn(dburl,dbusername,dbpassword);
		//清除之前的测试数据
    	JdbcDataUtil.executeUpdate(conn, "DELETE FROM 72crm_crm_contacts WHERE name LIKE '%沙漠%' OR name='';");
    	token = LoginService.getToken();
	}
	@Test(description="正确添加")
	public void test001_addContracts() throws Exception {
		String res = AddContactsService.addContacts(token);
		System.out.println(res);
		//Assert.assertEquals(res, "{\"code\":0}");
		Object[][] data = JdbcDataUtil.getData(conn, "SELECT name FROM 72crm_crm_contacts ORDER BY contacts_id DESC LIMIT 1;");
		JSONObject paramJsonObject = AddContactsService.getData();
		JSONObject entity = paramJsonObject.getJSONObject("entity");
		String expectName = entity.getString("name");
		String dbName = data[0][0].toString();
		Assert.assertEquals(dbName, expectName);
	}
	@Test(description="关联客户id")
	public void test002_addcontacts() throws Exception {
		String customerId = Props.get("customer_id");
		System.out.println("customerId:"+customerId);
		String res = AddContactsService.addContacts(token, "customer_id", customerId);
		System.out.println(res);
		JSONObject paramJsonObject = AddContactsService.getData();
		JSONObject entity = paramJsonObject.getJSONObject("entity");
		String expectName = entity.getString("name");
		
		Object[][] data = JdbcDataUtil.getData(conn, "SELECT name,customer_id FROM 72crm_crm_contacts ORDER BY contacts_id DESC LIMIT 1;");
		String dbname = data[0][0].toString();
		String dbid = data[0][1].toString();
		System.out.println("dbid:"+dbid);
		Assert.assertEquals(dbname, expectName);
		Assert.assertEquals(customerId, dbid);
	}
	@AfterClass
	public void afterClass() {
		JdbcDataUtil.closeConn(conn);
	}
}
