package com.auto.project2;
/** 

* @author 作者 沙陌 

* @version 创建时间：2020年2月15日 下午4:44:10 

*/

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.cookie.Cookie;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.auto.crazyapi.utils.CrazyHttpClient;
import com.auto.crazyapi.utils.JSONPathUtil;
import com.auto.crazyapi.utils.PropertyUtil;

public class JavaMallQianTaiTests extends BaseTest {
	
	@Test
	public void test001_login() throws Exception {
		String res = Login.login(host, "shamotest1", "123456");
		String message = JSONPathUtil.extract(res, "$.message");
		Assert.assertEquals(message, "登陆成功");
//		CrazyHttpClient.getCookies();
//		List<Cookie> cookies = CrazyHttpClient.getCookies();
//		for(Cookie c:cookies) {
//			if(c.getName().equals("JSESSIONID")) {
//				cookie=c.getValue();
//			}
//		}
	}
	@Test
	public void test002_login() throws Exception {
		String res = Login.login(host, "shamotest2344", "123456");
		String message = JSONPathUtil.extract(res, "$.message");
		Assert.assertEquals(message, "账号密码错误");
	}
	@Test
	public void test003_login() throws Exception {
		String res = Login.login(host, "shamotest1", "1234567");
		String message = JSONPathUtil.extract(res, "$.message");
		Assert.assertEquals(message, "账号密码错误");
	}
	@Test
	public void test004_login() throws Exception {
		String res = Login.login(host, "", "1234567");
		String message = JSONPathUtil.extract(res, "$.message");
		Assert.assertEquals(message, "账号密码错误");
	}
	@Test
	public void test005_login() throws Exception {
		String res = Login.login(host, "shamotest1", "");
		String message = JSONPathUtil.extract(res, "$.message");
		Assert.assertEquals(message, "账号密码错误");
	}
	@Test
	public void test006_addgoods() throws Exception {
//		Map<String, String> headers=new HashMap<String, String>();
//		Map<String, String> params=new HashMap<String, String>();
//		params.put("goodsid", "118");
//		params.put("num", "1");
//		String response = CrazyHttpClient.sendPost(host+"/javamall/api/shop/cart!addGoods.do", headers, params);
//		System.out.println(response);
		AddGoods.addGoods(host, "118", "1");
//		List<Cookie> cookies = CrazyHttpClient.getCookies();
//		for(Cookie c:cookies) {
//			System.out.println(c.getName()+":"+c.getValue());
//		}
	}
	@Test
	public void test007_order() throws Exception {
//		Map<String, String> headers=new HashMap<String, String>();
//		//headers.put("Cookie", "JSESSIONID="+cookie);
//		Map<String, String> params=new HashMap<String, String>();
//		params.put("addressId", "2");
//		params.put("paymentId", "1");
//		params.put("typeId", "1");
//		String response = CrazyHttpClient.sendPost(host+"/javamall/api/shop/order!create.do", headers, params);
//		System.out.println(response);
		String res = Order.order(host, "2","1","1");
		System.out.println(res);
		int result = JSONPathUtil.extract(res, "$.result");
		Assert.assertEquals(result, 1);
		
	}
	@Test
	public void test008_order() throws Exception {
//		Map<String, String> headers=new HashMap<String, String>();
//		//headers.put("Cookie", "JSESSIONID="+cookie);
//		Map<String, String> params=new HashMap<String, String>();
//		params.put("addressId", "2");
//		params.put("paymentId", "1");
//		params.put("typeId", "1");
//		String response = CrazyHttpClient.sendPost(host+"/javamall/api/shop/order!create.do", headers, params);
//		System.out.println(response);
		String property = System.getProperty("user.dir");
		System.out.println(property);
		Map<String, String> params=PropertyUtil.getAllKeyValue(property+"/resources/data/order.properties");
		String res = Order.order(host, params);
		System.out.println(res);
		int result = JSONPathUtil.extract(res, "$.result");
		Assert.assertEquals(result, 1);
	}

}
