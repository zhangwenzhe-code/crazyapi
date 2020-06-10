package com.auto.api0401;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.MailcapCommandMap;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.auto.crazyapi.utils.CrazyHttpClient;
import com.auto.crazyapi.utils.EncryptionUtil;
import com.auto.crazyapi.utils.JSONPathUtil;
import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.PropertyUtil;
import com.auto.crazyapi.utils.RandomUtil;
import com.auto.project2.AddGoods;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年4月8日 下午8:10:27 

*/
public class JavaMallTests {
	
	@Test
	public void test001_search() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		Map<String, String> params=new HashMap<String, String>();
		params.put("keyword", "天喔");
		
		String reponseString = CrazyHttpClient.sendGet("http://localhost:8080/javamall/api/shop/goods!search.do", headers, params);
		System.out.println(reponseString);
	}
	@Test
	public void test002_login() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		Map<String, String> params=new HashMap<String, String>();
		params.put("username", "shamotest1");
		params.put("password", "123456");
		String reponseString = CrazyHttpClient.sendPost("http://localhost:8080/javamall/api/shop/member!login.do", headers, params);
		System.out.println(reponseString);
	}
	@Test
	public void test004_addGoods() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		Map<String, String> params=new HashMap<String, String>();
		params.put("goodsid", "118");
		params.put("num", "1");
		String reponseString = CrazyHttpClient.sendPost("http://localhost:8080/javamall/api/shop/cart!addGoods.do", headers, params);
		System.out.println(reponseString);
	}
	
	@Test
	public void test005_order() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		Map<String, String> params=new HashMap<String, String>();
		params.put("addressId", "1");
		params.put("paymentId", "1");
		params.put("addressId", "1");
		params.put("typeId", "1");
		String reponseString = CrazyHttpClient.sendPost("http://localhost:8080/javamall/api/shop/order!create.do", headers, params);
		System.out.println(reponseString);
	}
	@Test
	public void test003_json() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		String jsonString="	{\"userName\":\"test\",\"password\":\"1234\",\"gender\":1,\"phoneNum\":\"110\",\"email\":\"beihe@163.com\",\"address\":\"Beijing\"}";
		String reponseString = CrazyHttpClient.sendPostJsonOrXml("http://localhost:8080/pinter/com/register", headers, jsonString);
		System.out.println(reponseString);
	}
	@Test
	public void addMember() throws Exception {
		String userPath = System.getProperty("user.dir");
		Properties db=PropertyUtil.getProperties(userPath+"/resources/db.properties");
		String url=db.getProperty("jdbc.url");
		String username=db.getProperty("jdbc.username");
		String password=db.getProperty("jdbc.password");
		Connection connection=JdbcDataUtil.getConn(url, username, password);
		
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID=E96A41692ACA0AFE81AF90117E4C43F8");
		String name = RandomUtil.getRndStrByLen(6);
		Object[][] data2 = JdbcDataUtil.getData(connection, "SELECT * FROM es_member WHERE uname='"+name+"'");
		while(null!=data2) {
			name = RandomUtil.getRndStrByLen(6);
		}
		
		Map<String, String> params=new HashMap<String, String>();
		params.put("member.uname", name);
		params.put("member.password", "abcdef");
		params.put("member.sex", "1");
		params.put("birthday", "2020-04-06");
		params.put("member.email", "shamo123@163.com");
		//member.sex: 1
		String res = CrazyHttpClient.sendPost("http://localhost:8080/javamall/shop/admin/member!saveMember.do?ajax=yes", headers, params);
		System.out.println(res);
		Assert.assertTrue(res.contains("保存会员成功"));
		//{"result":1,"message":"保存会员成功","id":"81"}
		String id = JSONPathUtil.extract(res, "$.id").toString();
		//select uname,email,`password` from es_member where member_id=81

		Object[][] data = JdbcDataUtil.getData(connection, "select uname,email,`password` from es_member where member_id="+id);
		JdbcDataUtil.closeConn(connection);
		String unameString=data[0][0].toString();
		String email=data[0][1].toString();
		String dbpassword=data[0][2].toString();
		Assert.assertEquals(unameString, name);
		Assert.assertEquals(email, "shamo123@163.com");
		String md5 = EncryptionUtil.md5("abcdef");
		Assert.assertEquals(dbpassword, md5);
		
	}

}
