package com.auto.api0401;
/** 

* @author 作者 沙陌 

* @version 创建时间：2020年4月8日 下午8:56:57 

*/

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.auto.api0401.crmserver.AddContacts;
import com.auto.api0401.crmserver.AddCustomerService;
import com.auto.api0401.crmserver.AddProduct;
import com.auto.api0401.crmserver.LoginService;
import com.auto.crazyapi.utils.CrazyHttpClient;
import com.auto.crazyapi.utils.ExcelDataUtil;
import com.auto.crazyapi.utils.JSONPathUtil;
import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.PropertyUtil;
import com.auto.crazyapi.utils.Props;

public class AddProductTest extends CRMTestBase {
	
	@Test(description = "正常添加产品")
	public void test001_addProduct() throws Exception {
		// TODO Auto-generated method stub
		String resString = AddProduct.addProduct(Props.get("token"));
		Assert.assertEquals(resString, "{\"code\":0}");
		String name = JSONPathUtil.extract(AddContacts.getData(), "$.entity.name").toString();
		//SELECT `name` FROM 72crm_crm_contacts ORDER BY contacts_id DESC LIMIT 1
		Object[][] data = JdbcDataUtil.getData(connection, "SELECT  `name` FROM 72crm_crm_product ORDER BY product_id DESC LIMIT 1");
		String expectName=data[0][0].toString();
		Assert.assertEquals(name, expectName);

	}
	
	@Test(description = "产品名称为空，添加正确")
	public void test002_addProduct() throws Exception {
		// TODO Auto-generated method stub
		String name="";
		String resString = AddProduct.addProduct(Props.get("token"),"name",name);
		Assert.assertEquals(resString, "{\"code\":0}");
		//String name = JSONPathUtil.extract(AddContacts.getData(), "$.entity.name").toString();
		//SELECT `name` FROM 72crm_crm_contacts ORDER BY contacts_id DESC LIMIT 1
		Object[][] data = JdbcDataUtil.getData(connection, "SELECT  `name` FROM 72crm_crm_product ORDER BY product_id DESC LIMIT 1");
		String expectName=data[0][0].toString();
		Assert.assertEquals(name, expectName);

	}
	@Test(description = "产品类型不能为空")
	public void test003_addProduct() throws Exception {
		// TODO Auto-generated method stub
		String category_id="";
		String resString = AddProduct.addProduct(Props.get("token"),"category_id",category_id);
		Assert.assertEquals(resString, "{\"code\":1}");
		//String name = JSONPathUtil.extract(AddContacts.getData(), "$.entity.name").toString();
		//SELECT `name` FROM 72crm_crm_contacts ORDER BY contacts_id DESC LIMIT 1
//		Object[][] data = JdbcDataUtil.getData(connection, "SELECT  `name` FROM 72crm_crm_product ORDER BY product_id DESC LIMIT 1");
//		String expectName=data[0][0].toString();
//		Assert.assertEquals(name, expectName);

	}
	@Test(description = "产品编码不能为空")
	public void test004_addProduct() throws Exception {
		// TODO Auto-generated method stub
		String num="";
		String resString = AddProduct.addProduct(Props.get("token"),"num",num);
		Assert.assertEquals(resString, "{\"code\":1}");
		//String name = JSONPathUtil.extract(AddContacts.getData(), "$.entity.name").toString();
		//SELECT `name` FROM 72crm_crm_contacts ORDER BY contacts_id DESC LIMIT 1
//		Object[][] data = JdbcDataUtil.getData(connection, "SELECT  `name` FROM 72crm_crm_product ORDER BY product_id DESC LIMIT 1");
//		String expectName=data[0][0].toString();
//		Assert.assertEquals(name, expectName);

	}
	@Test(description = "价格不能为空")
	public void test005_addProduct() throws Exception {
		// TODO Auto-generated method stub
		String price="";
		String resString = AddProduct.addProduct(Props.get("token"),"price",price);
		Assert.assertEquals(resString, "{\"code\":1}");
		//String name = JSONPathUtil.extract(AddContacts.getData(), "$.entity.name").toString();
		//SELECT `name` FROM 72crm_crm_contacts ORDER BY contacts_id DESC LIMIT 1
//		Object[][] data = JdbcDataUtil.getData(connection, "SELECT  `name` FROM 72crm_crm_product ORDER BY product_id DESC LIMIT 1");
//		String expectName=data[0][0].toString();
//		Assert.assertEquals(name, expectName);

	}
	@Test(description = "价格数字格式不正确")
	public void test006_addProduct() throws Exception {
		// TODO Auto-generated method stub
		String price="dfdggd";
		String resString = AddProduct.addProduct(Props.get("token"),"price",price);
		Assert.assertEquals(resString, "{\"code\":1}");
		//String name = JSONPathUtil.extract(AddContacts.getData(), "$.entity.name").toString();
		//SELECT `name` FROM 72crm_crm_contacts ORDER BY contacts_id DESC LIMIT 1
//		Object[][] data = JdbcDataUtil.getData(connection, "SELECT  `name` FROM 72crm_crm_product ORDER BY product_id DESC LIMIT 1");
//		String expectName=data[0][0].toString();
//		Assert.assertEquals(name, expectName);

	}
	@Test(description = "只有必填字段添加成功")
	public void test007_addProduct() throws Exception {
		// TODO Auto-generated method stub
		List<String> removeFieldList=new ArrayList<String>();
		removeFieldList.add("name");
		removeFieldList.add("description");
		String resString = AddProduct.addProduct(Props.get("token"),removeFieldList);
		Assert.assertEquals(resString, "{\"code\":0}");
		//String name = JSONPathUtil.extract(AddContacts.getData(), "$.entity.name").toString();
		//SELECT `name` FROM 72crm_crm_contacts ORDER BY contacts_id DESC LIMIT 1
//		Object[][] data = JdbcDataUtil.getData(connection, "SELECT  `name` FROM 72crm_crm_product ORDER BY product_id DESC LIMIT 1");
//		String expectName=data[0][0].toString();
//		Assert.assertEquals(name, expectName);

	}

}
