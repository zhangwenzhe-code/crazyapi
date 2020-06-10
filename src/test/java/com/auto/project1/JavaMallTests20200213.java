package com.auto.project1;
/** 

* @author 作者 沙陌 

* @version 创建时间：2020年2月13日 下午8:24:37 

*/

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.cookie.Cookie;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.auto.crazyapi.utils.CrazyHttpClient;
import com.auto.crazyapi.utils.EncryptionUtil;
import com.auto.crazyapi.utils.ExcelDataUtil;
import com.auto.crazyapi.utils.JSONPathUtil;
import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.RandomUtil;

import net.minidev.json.JSONArray;

public class JavaMallTests20200213 extends BaseTest{
	//javamall后台管理员各种接口
	
	@Test(description = "获取管理员列表")
	public void test001_getManagers() throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID="+cookie);
		
		Map<String, String> params=new HashMap<String, String>();
		params.put("ajax", "yes");
		
		String response = CrazyHttpClient.sendGet(host+"/javamall/core/admin/userAdmin!listJson.do", headers, params);
		System.out.println(response);
		
		int total=JSONPathUtil.extract(response, "$.total");
//		System.out.println(total);
		JSONArray usernames = JSONPathUtil.extract(response, "$..username");
//		for(Object o:usernames) {
//			System.out.println(o.toString());
//		}
		
		Object[][] db_usernames = JdbcDataUtil.getData(conn, "SELECT username FROM es_adminuser;");
		
		Assert.assertEquals(total, db_usernames.length);
		for(int i=0;i<total;i++) {
			Assert.assertEquals(usernames.get(i).toString(), db_usernames[i][0]);
		}

	}
	@Test(description = "新增管理员")
	public void test002_addManager() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID="+cookie);
		String username=RandomUtil.getRndStrByLen(6);
		Object[][] user = JdbcDataUtil.getData(conn, "select * from es_adminuser where state=1 and username='"+username+"'");
		while(user.length>0) {
			username=RandomUtil.getRndStrByLen(6);
			user = JdbcDataUtil.getData(conn, "select * from es_adminuser where state=1 and username='"+username+"'");
		}
		Map<String, String> params=new HashMap<String, String>();
		params.put("ajax", "yes");
		params.put("adminUser.username", username);
		params.put("adminUser.password", "123456");
		params.put("adminUser.realname", "沙陌");
		params.put("adminUser.founder", "1");
		params.put("roleids", "2");
		params.put("adminUser.state", "0");
		params.put("adminUser.userno", "123");
		params.put("adminUser.userdept", "测试部");
		params.put("adminUser.remark", "这是备注");
		String response = CrazyHttpClient.sendPost(host+"/javamall/core/admin/userAdmin!addSave.do", headers, params);
		System.out.println(response);
		String message=JSONPathUtil.extract(response, "$.message");
		Assert.assertEquals(message, "新增管理员成功");

		Object[][] db_usernames = JdbcDataUtil.getData(conn, "SELECT username,password FROM es_adminuser  ORDER BY userid DESC LIMIT 1");
		System.out.println(db_usernames[0][0]);
		System.out.println(db_usernames[0][1]);
		Assert.assertEquals("shamotest123", db_usernames[0][0]);
		String md5pwd=EncryptionUtil.md5("123456");
		Assert.assertEquals(md5pwd, db_usernames[0][1]);
	}
	@Test(description = "新增管理员")
	public void test003_addManager() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID="+cookie);
		String username=RandomUtil.getRndStrByLen(6);
		Object[][] user = JdbcDataUtil.getData(conn, "select * from es_adminuser where state=1 and username='"+username+"'");
		while(user.length>0) {
			username=RandomUtil.getRndStrByLen(6);
			user = JdbcDataUtil.getData(conn, "select * from es_adminuser where state=1 and username='"+username+"'");
		}
		Map<String, String> params=new HashMap<String, String>();
		params.put("ajax", "yes");
		params.put("adminUser.username", username);
		params.put("adminUser.password", "123456");
		params.put("adminUser.realname", "沙陌");
		params.put("adminUser.founder", "0");
		params.put("roleids", "1");
		params.put("adminUser.state", "1");
		params.put("adminUser.userno", "123");
		params.put("adminUser.userdept", "测试部");
		params.put("adminUser.remark", "这是备注");
		String response = CrazyHttpClient.sendPost(host+"/javamall/core/admin/userAdmin!addSave.do", headers, params);
		System.out.println(response);
		String message=JSONPathUtil.extract(response, "$.message");
		Assert.assertEquals(message, "新增管理员成功");

		Object[][] db_usernames = JdbcDataUtil.getData(conn, "SELECT username,password FROM es_adminuser  ORDER BY userid DESC LIMIT 1");
		System.out.println(db_usernames[0][0]);
		System.out.println(db_usernames[0][1]);
		Assert.assertEquals("shamotest123", db_usernames[0][0]);
		String md5pwd=EncryptionUtil.md5("123456");
		Assert.assertEquals(md5pwd, db_usernames[0][1]);
	}
	@Test(description = "新增管理员")
	public void test004_addManager() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID="+cookie);
		String username=RandomUtil.getRndStrByLen(6);
		Object[][] user = JdbcDataUtil.getData(conn, "select * from es_adminuser where state=1 and username='"+username+"'");
		while(user.length>0) {
			username=RandomUtil.getRndStrByLen(6);
			user = JdbcDataUtil.getData(conn, "select * from es_adminuser where state=1 and username='"+username+"'");
		}
		Map<String, String> params=new HashMap<String, String>();
		params.put("ajax", "yes");
		params.put("adminUser.username", username);
		params.put("adminUser.password", "123456");
		params.put("adminUser.realname", "沙陌");
		params.put("adminUser.founder", "0");
		params.put("roleids", "3");
		params.put("adminUser.state", "1");
		params.put("adminUser.userno", "123");
		params.put("adminUser.userdept", "测试部");
		params.put("adminUser.remark", "这是备注");
		String response = CrazyHttpClient.sendPost(host+"/javamall/core/admin/userAdmin!addSave.do", headers, params);
		System.out.println(response);
		String message=JSONPathUtil.extract(response, "$.message");
		Assert.assertEquals(message, "新增管理员成功");

		Object[][] db_usernames = JdbcDataUtil.getData(conn, "SELECT username,password FROM es_adminuser  ORDER BY userid DESC LIMIT 1");
		System.out.println(db_usernames[0][0]);
		System.out.println(db_usernames[0][1]);
		Assert.assertEquals("shamotest123", db_usernames[0][0]);
		String md5pwd=EncryptionUtil.md5("123456");
		Assert.assertEquals(md5pwd, db_usernames[0][1]);
	}
	@Test(description = "新增管理员")
	public void test005_addManager() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID="+cookie);
		String username=RandomUtil.getRndStrByLen(6);
		Object[][] user = JdbcDataUtil.getData(conn, "select * from es_adminuser where state=1 and username='"+username+"'");
		while(user.length>0) {
			username=RandomUtil.getRndStrByLen(6);
			user = JdbcDataUtil.getData(conn, "select * from es_adminuser where state=1 and username='"+username+"'");
		}
		Map<String, String> params=new HashMap<String, String>();
		params.put("ajax", "yes");
		params.put("adminUser.username", username);
		params.put("adminUser.password", "123456");
		params.put("adminUser.realname", "沙陌");
		params.put("adminUser.founder", "0");
		params.put("roleids", "4");
		params.put("adminUser.state", "1");
		params.put("adminUser.userno", "123");
		params.put("adminUser.userdept", "测试部");
		params.put("adminUser.remark", "这是备注");
		String response = CrazyHttpClient.sendPost(host+"/javamall/core/admin/userAdmin!addSave.do", headers, params);
		System.out.println(response);
		String message=JSONPathUtil.extract(response, "$.message");
		Assert.assertEquals(message, "新增管理员成功");

		Object[][] db_usernames = JdbcDataUtil.getData(conn, "SELECT username,password FROM es_adminuser  ORDER BY userid DESC LIMIT 1");
		System.out.println(db_usernames[0][0]);
		System.out.println(db_usernames[0][1]);
		Assert.assertEquals("shamotest123", db_usernames[0][0]);
		String md5pwd=EncryptionUtil.md5("123456");
		Assert.assertEquals(md5pwd, db_usernames[0][1]);
	}
	@Test(description = "新增管理员")
	public void test006_addManager() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID="+cookie);
		String username=RandomUtil.getRndStrByLen(6);
		Object[][] user = JdbcDataUtil.getData(conn, "select * from es_adminuser where state=1 and username='"+username+"'");
		while(user.length>0) {
			username=RandomUtil.getRndStrByLen(6);
			user = JdbcDataUtil.getData(conn, "select * from es_adminuser where state=1 and username='"+username+"'");
		}
		Map<String, String> params=new HashMap<String, String>();
		params.put("ajax", "yes");
		params.put("adminUser.username", username);
		params.put("adminUser.password", "123456");
		params.put("adminUser.realname", "沙陌");
		params.put("adminUser.founder", "0");
		params.put("roleids", "5");
		params.put("adminUser.state", "1");
		params.put("adminUser.userno", "123");
		params.put("adminUser.userdept", "测试部");
		params.put("adminUser.remark", "这是备注");
		String response = CrazyHttpClient.sendPost(host+"/javamall/core/admin/userAdmin!addSave.do", headers, params);
		System.out.println(response);
		String message=JSONPathUtil.extract(response, "$.message");
		Assert.assertEquals(message, "新增管理员成功");

		Object[][] db_usernames = JdbcDataUtil.getData(conn, "SELECT username,password FROM es_adminuser  ORDER BY userid DESC LIMIT 1");
		System.out.println(db_usernames[0][0]);
		System.out.println(db_usernames[0][1]);
		Assert.assertEquals("shamotest123", db_usernames[0][0]);
		String md5pwd=EncryptionUtil.md5("123456");
		Assert.assertEquals(md5pwd, db_usernames[0][1]);
	}
	
	@Test(description = "新增管理员",dataProvider = "getAddManagerData")
	public void test007_addManager() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID="+cookie);
		String username=RandomUtil.getRndStrByLen(6);
		Object[][] user = JdbcDataUtil.getData(conn, "select * from es_adminuser where state=1 and username='"+username+"'");
		while(user.length>0) {
			username=RandomUtil.getRndStrByLen(6);
			user = JdbcDataUtil.getData(conn, "select * from es_adminuser where state=1 and username='"+username+"'");
		}
		Map<String, String> params=new HashMap<String, String>();
		params.put("ajax", "yes");
		params.put("adminUser.username", username);
		params.put("adminUser.password", "123456");
		params.put("adminUser.realname", "沙陌");
		params.put("adminUser.founder", "0");
		params.put("roleids", "5");
		params.put("adminUser.state", "1");
		params.put("adminUser.userno", "123");
		String response = CrazyHttpClient.sendPost(host+"/javamall/core/admin/userAdmin!addSave.do", headers, params);
		System.out.println(response);
		String message=JSONPathUtil.extract(response, "$.message");
		Assert.assertEquals(message, "新增管理员成功");

		Object[][] db_usernames = JdbcDataUtil.getData(conn, "SELECT username,password FROM es_adminuser  ORDER BY userid DESC LIMIT 1");
		System.out.println(db_usernames[0][0]);
		System.out.println(db_usernames[0][1]);
		Assert.assertEquals("shamotest123", db_usernames[0][0]);
		String md5pwd=EncryptionUtil.md5("123456");
		Assert.assertEquals(md5pwd, db_usernames[0][1]);
	}
	@DataProvider
	public Object[][] getAddManagerData(){
		Object[][] userinfo= {{"shamotest1","123456","沙陌","0","1","1","1234","测试部","备注"},
							  {},
							  {}
							  };
		return userinfo;
	}


}
