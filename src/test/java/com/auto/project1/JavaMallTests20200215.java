package com.auto.project1;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.auto.crazyapi.utils.CrazyHttpClient;
import com.auto.crazyapi.utils.EncryptionUtil;
import com.auto.crazyapi.utils.ExcelDataUtil;
import com.auto.crazyapi.utils.JSONPathUtil;
import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.RandomUtil;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年2月15日 下午2:40:43 

*/
public class JavaMallTests20200215 extends BaseTest{
	
	
	@DataProvider
	public Object[][] managersData(){
		String property = System.getProperty("user.dir");
		System.out.println(property);
		Object[][] data = ExcelDataUtil.read(property+"/resources/excel/manager.xls", "user", 1, 0, 0, 0);
		return data;
	}
	
	@DataProvider
	public Object[][] managersModifyData(){
		String property = System.getProperty("user.dir");
		Object[][] data = ExcelDataUtil.read(property+"/resources/excel/modifymanager.xls", "user", 1, 0, 0, 0);
		return data;
	}
	
	
	@Test(description = "新增管理员数据驱动模式",dataProvider = "managersData")
	public void test001_addManager(String username,String password,String founder,String roleids,
			String state,String realname,String userno,String userdept,String remark,String assertvalue,String casename) throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID="+cookie);
		Map<String, String> params=new HashMap<String, String>();
		params.put("ajax", "yes");
		params.put("adminUser.username", username);
		params.put("adminUser.password", password);
		params.put("adminUser.realname", realname);
		params.put("adminUser.founder", founder);
		params.put("roleids", roleids);
		params.put("adminUser.state", state);
		params.put("adminUser.userno", userno);
		params.put("adminUser.userdept", userdept);
		params.put("adminUser.remark", remark);
		String response = CrazyHttpClient.sendPost(host+"/javamall/core/admin/userAdmin!addSave.do", headers, params);
		System.out.println(response);
		String message=JSONPathUtil.extract(response, "$.message");
		Assert.assertEquals(message, assertvalue,casename+"失败");
		int statusCode=CrazyHttpClient.getResponseStatusCode();
		Assert.assertEquals(statusCode, 200);

//		Object[][] db_usernames = JdbcDataUtil.getData(conn, "SELECT username,password FROM es_adminuser  ORDER BY userid DESC LIMIT 1");
//		System.out.println(db_usernames[0][0]);
//		System.out.println(db_usernames[0][1]);
//		Assert.assertEquals("shamotest123", db_usernames[0][0]);
//		String md5pwd=EncryptionUtil.md5("123456");
//		Assert.assertEquals(md5pwd, db_usernames[0][1]);
	}
	
	
	@Test(description = "修改管理员数据驱动模式",dataProvider = "managersModifyData")
	public void test002_modifyManager(String username,String updatePwd,String newPassword,String founder,String roleids,
			String state,String realname,String userno,String userdept,String remark,String depotid,String assertvalue,String casename) throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID="+cookie);
		
		String userid="";
		Object[][] user = JdbcDataUtil.getData(conn, "select userid from es_adminuser where "+"username='"+username+"'");
		if(user.length>0) {
			userid=String.valueOf(user[0][0]);
		}
		Map<String, String> params=new HashMap<String, String>();
		params.put("ajax", "yes");
		params.put("adminUser.userid", userid);
		if(updatePwd.equals("yes")) {
			params.put("updatePwd", updatePwd);
			params.put("newPassword", newPassword);
		}
		params.put("adminUser.username", username);
		params.put("adminUser.realname", realname);
		params.put("adminUser.founder", founder);
		params.put("roleids", roleids);
		params.put("adminUser.state", state);
		params.put("adminUser.userno", userno);
		params.put("adminUser.userdept", userdept);
		params.put("adminUser.remark", remark);
		params.put("depotid", depotid);
		String response = CrazyHttpClient.sendPost(host+"/javamall/core/admin/userAdmin!editSave.do", headers, params);
		System.out.println(response);
		String message=JSONPathUtil.extract(response, "$.message");
		Assert.assertEquals(message, assertvalue,casename+"失败");
		if(updatePwd.equals("yes")) {
			Object[][] db_usernames = JdbcDataUtil.getData(conn, "SELECT password FROM es_adminuser  where username='"+username+"'");
			System.out.println(db_usernames[0][0]);
			String md5pwd=EncryptionUtil.md5(newPassword);
			Assert.assertEquals(md5pwd, db_usernames[0][0]);
		}
		int statusCode=CrazyHttpClient.getResponseStatusCode();
//		Assert.assertEquals(statusCode, 200);

	}
	@Test
	public void test003_deleteManager() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID="+cookie);
		
		String userid="";
		Object[][] user = JdbcDataUtil.getData(conn, "select userid from es_adminuser where username like '%shamo%' order by userid desc limit 1");
		userid=String.valueOf(user[0][0]);

		Map<String, String> params=new HashMap<String, String>();
		params.put("id", userid);
		String response = CrazyHttpClient.sendGet(host+"/javamall/shop/admin/member!delete.do?ajax=yes", headers, params);
		System.out.println(response);
		String message=JSONPathUtil.extract(response, "$.message");
		Assert.assertEquals(message, "删除成功");
	}
}
