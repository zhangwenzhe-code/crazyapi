package com.mtx.les0524;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.Props;
import com.mtx.crmserver.AddBussinessService;
import com.mtx.crmserver.LoginService;
import com.mtx.crmserver.SaveAndUpdateService;

/**
*@author  zhangwenzhe
*@date  2020年6月2日---下午10:43:19
*/
public class CrmAddBussinessTests extends CrmBaseTests{
	String token;
	@BeforeClass
	public void setUp() throws Exception {
		token = LoginService.getToken();
	}
	//@Test(description="正确新建商机")
	public void test001_addAndUpdateBussiness() throws Exception {
		String res = AddBussinessService.addAndUpdateBussiness(token);
		System.out.println(res);
		String sql="select `business_name` FROM 72crm_crm_business WHERE business_name='商机1' ORDER BY business_id DESC LIMIT 1";
		Object[][] data = JdbcDataUtil.getData(conn, sql);
		JSONObject paramJsonObject = AddBussinessService.getData();
		JSONObject entity = paramJsonObject.getJSONObject("entity");
		String expectName=entity.getString("business_name");
		
		String dbName = data[0][0].toString();
		System.out.println("expectName:"+expectName+";dbName:"+dbName);
		Assert.assertEquals(dbName, expectName);
	}
	//@Test(description="缺少名称字段")
	public void test002_addAndUpdateBussiness() throws Exception {
		String res = AddBussinessService.addAndUpdateBussiness(token,"business_name");
		System.out.println(res);
		Assert.assertTrue(res.contains("\"code\":500"));
	}
	//@Test(description="名称字段空")
	public void test003_addAndUpdateBussiness() throws Exception {
		String res = AddBussinessService.addAndUpdateBussiness(token,"business_name","");
		System.out.println(res);
		Assert.assertTrue(res.contains("\"code\":0"));
		String sql="select `business_name` FROM 72crm_crm_business ORDER BY business_id DESC LIMIT 1";
		Object[][] data = JdbcDataUtil.getData(conn, sql);
		JSONObject paramJsonObject = AddBussinessService.getData("business_name","");
		JSONObject entity = paramJsonObject.getJSONObject("entity");
		String expectName=entity.getString("business_name");
		System.out.println("expectName:"+expectName);
		String dbName = data[0][0].toString();
		System.out.println("expectName:"+expectName+";dbName:"+dbName);
		Assert.assertEquals(dbName, expectName);
	}
}
