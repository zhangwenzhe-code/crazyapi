package com.auto.crazyapi;
/** 

* @author 作者 沙陌 

* @version 创建时间：2020年2月11日 下午8:20:32 

*/

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

//import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auto.crazyapi.utils.CrazyHttpClient;
import com.auto.crazyapi.utils.JSONPathUtil;
import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.XMLUtil;

import net.minidev.json.JSONArray;

public class HttpTests20200211 {
	
	@Test
	public void test1() throws Exception {
		Map<String, String> params=new HashMap<String, String>();
		params.put("id", "1");
		String response = CrazyHttpClient.sendGet("http://localhost:8080/pinter/com/getSku", new HashMap<String, String>(), params);
		System.out.println(response);
		Assert.assertEquals(response.contains("\"code\":\"0\""), true);
	}
	@Test
	public void test2() throws Exception {
		Map<String, String> params=new HashMap<String, String>();
		params.put("userName", "admin");
		params.put("password", "123456");
		String response = CrazyHttpClient.sendPost("http://localhost:8080/pinter/com/login", new HashMap<String, String>(), params);
		System.out.println(response);
		Assert.assertEquals(response.contains("\"message\":\"success\""), true);
	}
	@Test
	public void test3() throws Exception {
		String response = CrazyHttpClient.sendUpload("http://localhost:8080/pinter/file/api/upload2", new HashMap<String, String>(), "C:\\Users\\lixio\\Desktop\\user.txt");
		System.out.println(response);
		Assert.assertEquals(response.contains("\"message\":\"上传成功\""), true);
	}
	@Test
	public void test4() throws Exception {
		String json="{\"userName\":\"test\",\"password\":\"1234\",\"gender\":1,\"phoneNum\":\"110\",\"email\":\"beihe@163.com\",\"address\":\"Beijing\"}";
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		String response = CrazyHttpClient.sendPostJsonOrXml("http://localhost:8080/pinter/com/register", headers, json);
		System.out.println(response);
		//Assert.assertEquals(response.contains("\"message\":\"上传成功\""), true);
	}
	@Test
	public void test5() throws Exception {
		String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
				"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" + 
				"  <soap:Body>\r\n" + 
				"    <getMobileCodeInfo xmlns=\"http://WebXml.com.cn/\">\r\n" + 
				"      <mobileCode>18729399607</mobileCode>\r\n" + 
				"      <userID></userID>\r\n" + 
				"    </getMobileCodeInfo>\r\n" + 
				"  </soap:Body>\r\n" + 
				"</soap:Envelope>";
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "text/xml; charset=utf-8");
		String response = CrazyHttpClient.sendPostJsonOrXml("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx", headers, xml);
		System.out.println(response);
		//Assert.assertEquals(response.contains("\"message\":\"上传成功\""), true);
	}
	
	@Test
	public void test6() throws Exception {
		//String json="{\"userName\":\"test\",\"password\":\"1234\",\"gender\":1,\"phoneNum\":\"110\",\"email\":\"beihe@163.com\",\"address\":\"Beijing\"}";
		File jsonFile=new File("resources/data/register.json");
		String json = FileUtils.readFileToString(jsonFile, "UTF-8");
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		String response = CrazyHttpClient.sendPostJsonOrXml("http://localhost:8080/pinter/com/register", headers, json);
		System.out.println(response);
		//Assert.assertEquals(response.contains("\"message\":\"上传成功\""), true);
		JSONObject jsonRes=JSONObject.parseObject(response);
		String msessage = jsonRes.getString("message");
		System.out.println(msessage);
	}
	
	@Test
	public void test7() throws Exception {
		File xmlFile=new File("resources/data/mobile.xml");
		String xml = FileUtils.readFileToString(xmlFile, "UTF-8");
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "text/xml; charset=utf-8");
		//headers.put("Cookie", "JSESSIONID=339CA36226A5D34F00E15A06D9C079BA");
		String response = CrazyHttpClient.sendPostJsonOrXml("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx", headers, xml);
		System.out.println(response);
		//Assert.assertEquals(response.contains("\"message\":\"上传成功\""), true);
		String xmlString = XMLUtil.getXmlString(response, "//soap:Body");
		Assert.assertEquals(xmlString, "18729399607：陕西 西安 陕西移动全球通卡");
	}
	
	@Test
	public void search() throws Exception {
		
		
		Map<String, String> params=new HashMap<String, String>();
		params.put("catid", "");
		params.put("brandid", "");
		params.put("keyword", "天喔");
		String response = CrazyHttpClient.sendGet("http://localhost:8080/javamall/api/shop/goods!search.do", new HashMap<String, String>(), params);
		//System.out.println(response);
//		JSONObject jsonRes=JSONObject.parseObject(response);
//		JSONArray jsonArray = jsonRes.getJSONArray("data");
//		System.out.println(jsonArray.get(0));
//		JSONObject jsonArray1 = (JSONObject) jsonArray.get(0);
//		//System.out.println(jsonArray1.getString("name"));
//		for(Object js:jsonArray) {
//			System.out.println(((JSONObject)js).getString("name"));
//		}
		JSONArray names=JSONPathUtil.extract(response, "$..name");
//		JSONArray names1=JSONPathUtil.extract(response, "$.data");
//		System.out.println(names1.get(0));
		//System.out.println(names.get(0));
		
		Connection conn = JdbcDataUtil.getConn(
				"jdbc:mysql://localhost:3306/javamall?useUnicode=true&characterEncoding=utf8",
				"root", "123456");
		Object[][] data = JdbcDataUtil.getData(conn, "select name from es_goods where name like \"%天喔%\"");
//		for(Object[] obj:data) {
//			for(Object o:obj) {
//				System.out.println(o.toString());
//			}
//		}
		JdbcDataUtil.closeConn(conn);
		//System.out.println(data[1][0]);
		Assert.assertEquals(names.size(), data.length);
		for(int i=0;i<data.length;i++) {
			Assert.assertEquals(names.get(i), data[i][0]);
		}
//		Assert.assertEquals(names.get(0), data[0][0]);
//		Assert.assertEquals(names.get(1), data[1][0]);
//		Assert.assertEquals(names.get(2), data[2][0]);
//		Assert.assertEquals(names.get(3), data[3][0]);
	}
	@Test
	public void test8() throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Cookie", "JSESSIONID=94785C75ECAB54E73437AE8242332A19");
		Map<String, String> params=new HashMap<String, String>();
		String string = CrazyHttpClient.sendGet("http://localhost:8080/javamall/shop/admin/member!memberlistJson.do?page=1&rows=10&sort=member_id&order=desc", headers, params);
		System.out.println(string);
	}


}
