package com.mtx.crmserver;

import java.util.Properties;

import com.auto.crazyapi.utils.PropertyUtil;

/**
*@author  zhangwenzhe
*@date  2020年5月31日---下午2:57:49
*/
public class BaseService {
	static String host;
	static String userdir=System.getProperty("user.dir");
	static String dburl;
	static String dbusername;
	static String dbpassword;
	static {
		Properties properties = PropertyUtil.getProperties(userdir+"/resources/http.properties");
		host = properties.getProperty("http.crm.url");
		
		Properties dbProperties = PropertyUtil.getProperties(userdir+"/resources/db.properties");
		dburl = dbProperties.getProperty("jdbc.crm.url");
		dbusername = dbProperties.getProperty("jdbc.crm.username");
		dbpassword = dbProperties.getProperty("jdbc.crm.password");
	}
}
