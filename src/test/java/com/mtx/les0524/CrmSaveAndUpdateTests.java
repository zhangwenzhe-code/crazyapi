package com.mtx.les0524;

import java.sql.Connection;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.PropertyUtil;
import com.auto.crazyapi.utils.Props;
import com.mtx.crmserver.AddContactsService;
import com.mtx.crmserver.LoginService;
import com.mtx.crmserver.SaveAndUpdateService;

/**
*@author  zhangwenzhe
*@date  2020年6月1日---上午9:17:46
*/
public class CrmSaveAndUpdateTests extends CrmBaseTests{
	String token;
	@BeforeClass
	public void setUp() throws Exception {
		token = LoginService.getToken();
	}
	//@Test(description="正确新建产品")
	public void test001_saveAndUpdate() throws Exception {
		String res = SaveAndUpdateService.saveAndUpdate(token);
		System.out.println(res);
		//Assert.assertEquals(res, "{\"code\":0}");
		String sql="select `num` FROM 72crm_crm_product WHERE name='Java全栈自动化' ORDER BY product_id DESC LIMIT 1";
		Object[][] data = JdbcDataUtil.getData(conn, sql);
//		JSONObject paramJsonObject = SaveAndUpdateService.getData();
//		JSONObject entity = paramJsonObject.getJSONObject("entity");
		String expectNum = Props.get("num");
		String dbNum = data[0][0].toString();
		System.out.println("expectNum:"+expectNum+";dbNum:"+dbNum);
		Assert.assertEquals(dbNum, expectNum);
	}
	//@Test(description="name字段缺失")
	public void test002_saveAndUpdate() throws Exception {
		String res = SaveAndUpdateService.saveAndUpdate(token,"name");
		System.out.println(res);
		Assert.assertTrue(res.contains("\"code\":500"));;
	}
	//@Test(description="name空")
	public void test003_saveAndUpdate() throws Exception {
		String res = SaveAndUpdateService.saveAndUpdate(token,"name","");
		//System.out.println("3:"+res);
		String sql="select `name` FROM 72crm_crm_product WHERE num='"+Props.get("num")+"' ORDER BY product_id DESC LIMIT 1";
		Object[][] data = JdbcDataUtil.getData(conn, sql);
		String expectName = "";
		String dbName = data[0][0].toString();
		System.out.println(dbName);
		System.out.println("expectName:"+expectName+";dbName:"+dbName);
		Assert.assertEquals(dbName, expectName);
	}
	//@Test(description="category_id字段缺失")
	public void test004_saveAndUpdate() throws Exception {
		String res = SaveAndUpdateService.saveAndUpdate(token,"category_id");
		System.out.println("4:"+res);
		Assert.assertTrue(res.contains("\"code\":0"));;
	}
	//@Test(description="category_id字段空")
	public void test005_saveAndUpdate() throws Exception {
		String res = SaveAndUpdateService.saveAndUpdate(token,"category_id","");
		//System.out.println("5:"+res);
		String sql="select `category_id` FROM 72crm_crm_product WHERE num='"+Props.get("num")+"' ORDER BY product_id DESC LIMIT 1";
		Object[][] data = JdbcDataUtil.getData(conn, sql);
		Assert.assertEquals(data[0][0], null);
//		System.out.println(data[0][0]);
	}
	//@Test(description="num字段是数据库中已有值")
	public void test006_saveAndUpdate() throws Exception {
		String res = SaveAndUpdateService.saveAndUpdateNum(token,"num");
		//System.out.println("6:"+res);
		Assert.assertTrue(res.contains("产品编号已存在，请校对后再添加！"));;
	}
	//@Test(description="num字段空")
	public void test007_saveAndUpdate() throws Exception {
		String res = SaveAndUpdateService.saveAndUpdate(token,"num","");
		System.out.println("7:"+res);
		/*String sql="select num FROM 72crm_crm_product  ORDER BY product_id DESC LIMIT 1";
		Object[][] data = JdbcDataUtil.getData(conn, sql);
		System.out.println(data[0][0]);*/
		Assert.assertTrue(res.contains("产品编号已存在，请校对后再添加！"));
	}
	//@Test(description="num字段缺失")
	public void test008_saveAndUpdate() throws Exception {
		String res = SaveAndUpdateService.saveAndUpdate(token,"num");
		System.out.println("8:"+res);
		String sql="select num FROM 72crm_crm_product  ORDER BY product_id DESC LIMIT 1";
		Object[][] data = JdbcDataUtil.getData(conn, sql);
		System.out.println(data[0][0]);
		Assert.assertEquals(null, data[0][0]);
	}
	//@Test(description="price字段缺失")
	public void test009_saveAndUpdate() throws Exception {
		String res = SaveAndUpdateService.saveAndUpdate(token,"price");
		System.out.println("9:"+res);
		//Assert.assertTrue(res.contains("\"code\":500"));
		String sql="select price FROM 72crm_crm_product  ORDER BY product_id DESC LIMIT 1";
		Object[][] data = JdbcDataUtil.getData(conn, sql);
		System.out.println(data[0][0]);
		Assert.assertEquals(null, data[0][0]);
	}
	@Test(description="price字段空")
	public void test0010_saveAndUpdate() throws Exception {
		String res = SaveAndUpdateService.saveAndUpdate(token,"price","");
		System.out.println("9:"+res);
		//Assert.assertTrue(res.contains("\"code\":500"));
		String sql="select price FROM 72crm_crm_product  ORDER BY product_id DESC LIMIT 1";
		Object[][] data = JdbcDataUtil.getData(conn, sql);
		System.out.println(data[0][0]);
		Assert.assertEquals(null, data[0][0]);
	}
}
