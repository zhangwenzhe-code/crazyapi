package com.mtx.les0524;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.auto.crazyapi.utils.Props;

/**
*@author  zhangwenzhe
*@date  2020年5月24日---下午4:41:07
*/
public class HttpClientBasicStudy {	
	/**
	 * 1. 创建client对象
	 * 2. 创建请求对象Get或者Post
	 * 3. 如果有cookie或者header参数，那么需要创建BasicHeader对象
	 * 4. 参数设置
	 *    1. 表单形式  new BasicNameValuePair("adminUser.username", "shamo0001");
	 *       List<NameValuePair> parm=xxx
	 *       parm.add(xx);
	 *       转换成entity对象UrlEncodedFormEntity
	 *    2. json形式或者xml形式的
	 *       转换成StringEntity
	 *    3. 文件上传
	 *       MultipartEntityBuilder.create().addPart("file", fileBody).build();
	 * 5. 针对请求添加entity参数，xx.setEntity(entity);
	 * 6. 如果有header参数，就添加，xx.setHeader(xxx);
	 * 7. 发送请求接收响应，response=client.excute(get/post);
	 * 8. 转换响应entity为字符串，EntityUtils.toString(response.getEntity);
	 * 9. 做断言
	 */
	CloseableHttpClient httpClient;
	@BeforeClass
	public void init() {
		httpClient=HttpClients.createDefault();
	}
	
	//@Test
	public void test001_get() throws Exception, IOException {
		//CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpGet httpGet=new HttpGet("http://localhost:8080/javamall/api/shop/goods!search.do?keyword=天喔");
		HttpResponse response=httpClient.execute(httpGet);
		HttpEntity entity=response.getEntity();
		String string = EntityUtils.toString(entity);
		System.out.println(string);
		Assert.assertEquals(string.contains("\"result\":1"), true);
	}
	//@Test
	public void test002_post() throws Exception, IOException {
		//CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpPost httpPost=new HttpPost("http://localhost:8080/javamall/api/shop/goods!search.do");
		BasicNameValuePair keyword=new BasicNameValuePair("keyword", "天喔");
		BasicNameValuePair catid=new BasicNameValuePair("catid", "");
		BasicNameValuePair brandid=new BasicNameValuePair("brandid", "");
		List<BasicNameValuePair> parameters=new ArrayList<>();
		parameters.add(keyword);
		parameters.add(catid);
		parameters.add(brandid);
		
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(parameters);
		httpPost.setEntity(urlEncodedFormEntity);
		
		HttpResponse response=httpClient.execute(httpPost);
		HttpEntity entity=response.getEntity();
		String string = EntityUtils.toString(entity);
		System.out.println(string);
		Assert.assertEquals(string.contains("\"result\":1"), true);
	}
	//@Test
	public void test003_postLogin() throws Exception, IOException {
		//CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpPost httpPost=new HttpPost("http://localhost:8080/javamall/api/shop/member!login.do");
		BasicNameValuePair username=new BasicNameValuePair("username", "bob");
		BasicNameValuePair password=new BasicNameValuePair("password", "123456");
		List<BasicNameValuePair> param=new ArrayList<BasicNameValuePair>();
		param.add(username);
		param.add(password);
		
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(param);
		httpPost.setEntity(urlEncodedFormEntity);
		
		HttpResponse response=httpClient.execute(httpPost);
		HttpEntity entity=response.getEntity();
		String string = EntityUtils.toString(entity);
		System.out.println(string);
		Assert.assertEquals(string.contains("\"result\":1"), true);
	}
	//@Test
	public void test004_postAddCart() throws Exception, IOException {
		//CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpPost httpPost=new HttpPost("http://localhost:8080/javamall/api/shop/cart!addGoods.do");
		BasicNameValuePair num=new BasicNameValuePair("num", "1");
		BasicNameValuePair goodsid=new BasicNameValuePair("goodsid", "118");
		List<BasicNameValuePair> param=new ArrayList<BasicNameValuePair>();
		param.add(num);
		param.add(goodsid);
		
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(param);
		httpPost.setEntity(urlEncodedFormEntity);
		
		HttpResponse response=httpClient.execute(httpPost);
		HttpEntity entity=response.getEntity();
		String string = EntityUtils.toString(entity);
		System.out.println(string);
		Assert.assertEquals(string.contains("\"result\":1"), true);
	}
	//@Test(description="下单")
	public void test005_postOrder() throws Exception, IOException {
		HttpPost httpPost=new HttpPost("http://localhost:8080/javamall/api/shop/order!create.do");
		BasicNameValuePair addressId=new BasicNameValuePair("addressId", "1");
		BasicNameValuePair paymentId=new BasicNameValuePair("paymentId", "1");
		BasicNameValuePair typeId=new BasicNameValuePair("typeId", "1");
		List<BasicNameValuePair> param=new ArrayList<BasicNameValuePair>();
		param.add(addressId);
		param.add(paymentId);
		param.add(typeId);
		
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(param);
		httpPost.setEntity(urlEncodedFormEntity);
		
		HttpResponse response=httpClient.execute(httpPost);
		HttpEntity entity=response.getEntity();
		String string = EntityUtils.toString(entity);
		System.out.println(string);
		Assert.assertEquals(string.contains("\"result\":1"), true);
	}
	//@Test
	public void test004_JsonAssign() throws Exception, IOException {
		//CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpPost httpPost=new HttpPost("http://localhost:8080/pinter/com/register");
		String param="{\n" + 
				"	\"userName\": \"test\",\n" + 
				"	\"password\": \"1234\",\n" + 
				"	\"gender\": 1,\n" + 
				"	\"phoneNum\": \"110\",\n" + 
				"	\"email\": \"beihe@163.com\",\n" + 
				"	\"address\": \"Beijing\"\n" + 
				"}";
		
		StringEntity stringEntity = new StringEntity(param);
		BasicHeader header=new BasicHeader("Content-Type", "application/json");
		httpPost.setEntity(stringEntity);
		httpPost.setHeader(header);
		
		HttpResponse response=httpClient.execute(httpPost);
		HttpEntity entity=response.getEntity();
		String result = EntityUtils.toString(entity);
		System.out.println(result);
		Assert.assertEquals(result.contains("\"message\":\"注册成功\""), true);
	}
	//@Test
	public void test005_XMLAssign() throws Exception, IOException {
			HttpPost httpPost=new HttpPost("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx");
			String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
					"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" + 
					"  <soap:Body>\r\n" + 
					"    <getMobileCodeInfo xmlns=\"http://WebXml.com.cn/\">\r\n" + 
					"      <mobileCode>18729399607</mobileCode>\r\n" + 
					"      <userID></userID>\r\n" + 
					"    </getMobileCodeInfo>\r\n" + 
					"  </soap:Body>\r\n" + 
					"</soap:Envelope>";
			
			StringEntity stringEntity = new StringEntity(xml,"UTF-8");
			BasicHeader header=new BasicHeader("Content-Type", "text/xml");
			httpPost.setEntity(stringEntity);
			httpPost.setHeader(header);
			
			HttpResponse response=httpClient.execute(httpPost);
			HttpEntity entity=response.getEntity();
			String result = EntityUtils.toString(entity);
			System.out.println(result);
			System.out.println(response.getStatusLine());//HTTP/1.1 200 
			Assert.assertEquals(result.contains("陕西"), true);
		}
	@Test
	public void test006_JsonCrmLogin() throws Exception, IOException {
		HttpPost httpPost=new HttpPost("http://localhost:8088/login");
		BasicNameValuePair username=new BasicNameValuePair("username", "admin");
		BasicNameValuePair password=new BasicNameValuePair("password", "123456");
		List<BasicNameValuePair> param=new ArrayList<BasicNameValuePair>();
		param.add(username);
		param.add(password);
		
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(param);
		httpPost.setEntity(urlEncodedFormEntity);
		
		HttpResponse response=httpClient.execute(httpPost);
		HttpEntity entity=response.getEntity();
		String string = EntityUtils.toString(entity);
		JSONObject parseObject = JSONObject.parseObject(string);
		String token = parseObject.getString("Admin-Token");
		Props.put("Admin-Token", token);
		System.out.println(string);
		Assert.assertEquals(string.contains("\"code\":0"), true);
		
	}
	
	@Test
	public void test007_JsonCrmAddCustomer() throws Exception, IOException {
		//CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpPost httpPost=new HttpPost("http://localhost:8088/CrmCustomer/addOrUpdate");
		String param="{\n" + 
				"	\"entity\": {\n" + 
				"		\"customer_name\": \"客户123\",\n" + 
				"		\"mobile\": \"18991112345\",\n" + 
				"		\"telephone\": \"01028375678\",\n" + 
				"		\"website\": \"http://testfan.cn\",\n" + 
				"		\"next_time\": \"2020-04-02 00:00:00\",\n" + 
				"		\"remark\": \"这是备注\",\n" + 
				"		\"address\": \"北京市,北京城区,昌平区\",\n" + 
				"		\"detailAddress\": \"霍营地铁\",\n" + 
				"		\"location\": \"\",\n" + 
				"		\"lng\": \"\",\n" + 
				"		\"lat\": \"\"\n" + 
				"	}\n" + 
				"}";
		String token = Props.get("Admin-Token");
		System.out.println(token);
		StringEntity stringEntity = new StringEntity(param,"UTF-8");
		BasicHeader header=new BasicHeader("Content-Type", "application/json");
		BasicHeader Admintoken=new BasicHeader("Admin-Token", token);
		httpPost.setEntity(stringEntity);
		httpPost.setHeader(header);
		httpPost.setHeader(Admintoken);
		
		HttpResponse response=httpClient.execute(httpPost);
		HttpEntity entity=response.getEntity();
		String result = EntityUtils.toString(entity);
		System.out.println(result);
		System.out.println(response.getStatusLine());//HTTP/1.1 200 
		Assert.assertEquals(result.contains("\"code\":0"), true);
	}
	
	
}
