package com.mtx.les0524;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mtx.crmserver.LoginService;

/**
*@author  zhangwenzhe
*@date  2020年5月31日---下午2:46:59
*/
public class CrmLoginTests {
	@Test(description="正确登录")
	public void test001_login() throws Exception {
		String login = LoginService.login();
		Assert.assertTrue(login.contains("\"code\":0"));
	}
	@Test(description="用户名不正确")
	public void test002_login() throws Exception {
		String login = LoginService.login("username","admin1");
		Assert.assertTrue(login.contains("用户名或密码错误！"));
	}
	@Test(description="密码不正确")
	public void test003_login() throws Exception {
		String login = LoginService.login("password","admin1");
		//System.out.println("3:"+login);
		Assert.assertTrue(login.contains("用户名或密码错误！"));
	}
	@Test(description="用户名字段缺失")
	public void test004_login() throws Exception {
		String login = LoginService.login("username");
		//System.out.println("4:"+login);
		Assert.assertTrue(login.contains("请输入用户名和密码！"));
	}
	@Test(description="密码字段缺失")
	public void test005_login() throws Exception {
		String login = LoginService.login("password");
		//System.out.println("5:"+login);
		Assert.assertTrue(login.contains("请输入用户名和密码！"));
	}
	@Test(description="用户名空")
	public void test006_login() throws Exception {
		String login = LoginService.login("username","");
		//System.out.println("6:"+login);
		Assert.assertTrue(login.contains("请输入用户名和密码！"));
	}
	@Test(description="密码空")
	public void test007_login() throws Exception {
		String login = LoginService.login("password","");
		//System.out.println("7:"+login);
		Assert.assertTrue(login.contains("请输入用户名和密码！"));
	}
}
