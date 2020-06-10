package com.auto.api0401.crmserver;

import java.util.Properties;

import com.auto.crazyapi.utils.PropertyUtil;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年4月8日 下午9:46:39 

*/
public class BaseService {
	static String host;
	static {
		String userpath = System.getProperty("user.dir");
		Properties httpProps=PropertyUtil.getProperties(userpath+"/resources/http.properties");
		host = httpProps.get("http.crm.url").toString();
	}
}
