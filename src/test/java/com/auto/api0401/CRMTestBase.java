package com.auto.api0401;
/** 

* @author 作者 沙陌 

* @version 创建时间：2020年4月13日 下午8:34:58 

*/

import java.sql.Connection;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.PropertyUtil;

public class CRMTestBase {
	
	public String host;
	public Connection connection;
	public String url;
	public String username;
	public String password;

	@BeforeClass
	public void init() {
		String userPath = System.getProperty("user.dir");
		Properties httpProps=PropertyUtil.getProperties(userPath+"/resources/http.properties");
		host = httpProps.get("http.crm.url").toString();
		Properties db=PropertyUtil.getProperties(userPath+"/resources/db.properties");
		url=db.getProperty("jdbc.crm.url");
		username=db.getProperty("jdbc.crm.username");
		password=db.getProperty("jdbc.crm.password");
		connection=JdbcDataUtil.getConn(url, username, password);
//		connection=JdbcDataUtil.getConn(url, username, password);
		JdbcDataUtil.executeUpdate(connection, "DELETE FROM 72crm_crm_customer where customer_name='' OR customer_name like '%a%' OR customer_name like '%客户%'");//测试前清除之前的测试数据
		JdbcDataUtil.executeUpdate(connection, "DELETE FROM 72crm_crm_contacts where name='' OR name like '%a%' OR name like '%客户%'");//测试前清除之前的测试数据
//		System.out.println("开始执行测试");
	}
	@AfterClass
	public void close() {
		JdbcDataUtil.closeConn(connection);
	}
}
