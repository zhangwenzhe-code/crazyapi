package com.auto.api0401.crmserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import com.auto.crazyapi.utils.CrazyHttpClient;
import com.auto.crazyapi.utils.PropertyUtil;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年4月8日 下午9:37:35 

*/
public class LoginService extends BaseService {
//	static String host;
//	static {
//		String userPath = System.getProperty("user.dir");
//		Properties httpProps=PropertyUtil.getProperties(userPath+"/resources/http.properties");
//		host = httpProps.get("http.crm.url").toString();
//	}
	
	
	public static String login(String username,String password) throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		Map<String, String> params=new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);
		String reponseString = CrazyHttpClient.sendPost(host+"/login", headers, params);
		//System.out.println(reponseString);
		
		return reponseString;
	}

}
