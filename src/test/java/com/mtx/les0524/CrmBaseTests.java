package com.mtx.les0524;

import java.sql.Connection;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.PropertyUtil;
import com.mtx.crmserver.LoginService;

/**
*@author  zhangwenzhe
*@date  2020年6月1日---上午9:18:42
*/
public class CrmBaseTests {
	
	public Connection conn;
	@BeforeClass
	public void init() throws Exception {
		String userdir=System.getProperty("user.dir");
		Properties dbProperties = PropertyUtil.getProperties(userdir+"/resources/db.properties");
		String dburl = dbProperties.getProperty("jdbc.crm.url");
		String dbusername = dbProperties.getProperty("jdbc.crm.username");
		String dbpassword = dbProperties.getProperty("jdbc.crm.password");
		conn = JdbcDataUtil.getConn(dburl,dbusername,dbpassword);
	}
	@AfterClass
	public void afterClass() {
		JdbcDataUtil.closeConn(conn);
	}
}
