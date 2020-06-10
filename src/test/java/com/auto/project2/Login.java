package com.auto.project2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.cookie.Cookie;
import org.testng.annotations.Test;

import com.auto.crazyapi.utils.CrazyHttpClient;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年2月15日 下午4:32:57 

*/
public class Login{
	

	public  static String login(String host,String username,String password) throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		Map<String, String> params=new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);
		String response = CrazyHttpClient.sendPost(host+"/javamall/api/shop/member!login.do", headers, params);
		return response;
//		System.out.println(response);
//		List<Cookie> cookies = CrazyHttpClient.getCookies();
//		for(Cookie c:cookies) {
//			System.out.println(c.getName()+":"+c.getValue());
//		}
	}

}
