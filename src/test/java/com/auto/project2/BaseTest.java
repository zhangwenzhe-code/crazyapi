package com.auto.project2;

import java.sql.Connection;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.PropertyUtil;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年2月15日 下午4:31:48 

*/
public class BaseTest {
	static String host;
	static Connection conn;
	static String cookie;
	@BeforeClass
	public void init() {
		String property = System.getProperty("user.dir");
		System.out.println(property);
		Properties httpProps=PropertyUtil.getProperties(property+"/resources/http.properties");
		host=httpProps.getProperty("http.javamall.url");
		//cookie=httpProps.getProperty("http.cookie");
		Properties dbProps=PropertyUtil.getProperties(property+"/resources/db.properties");
		String dburl=dbProps.getProperty("jdbc.url");
		String dbusername=dbProps.getProperty("jdbc.username");
		String dbpassword=dbProps.getProperty("jdbc.password");
		conn=JdbcDataUtil.getConn(dburl, dbusername, dbpassword);
		//JdbcDataUtil.executeUpdate(conn, "DELETE FROM es_adminuser where username like '%shamo%' OR username='';");//清除测试数据
	}
	@AfterClass
	public void close() throws Exception {
		conn.close();
	}
	
}
