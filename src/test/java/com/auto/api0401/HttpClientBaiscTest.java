package com.auto.api0401;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年4月1日 下午8:12:09 

*/
public class HttpClientBaiscTest {
	
	
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
	CloseableHttpClient client;
	BasicHeader cookie;
	String host;
	
	@BeforeClass
	public void init() {
		client=HttpClients.createDefault();
		host="http://localhost:8080";
		cookie=new BasicHeader("cookie", "JSESSIONID=892AA9717999DA40B2DD548FB3301FD5");
	}
	
	//@Test
	public void getManagerList() throws Exception, IOException {
		//CloseableHttpClient client=HttpClients.createDefault();
		HttpGet httpGet=new HttpGet(host+"/javamall/core/admin/userAdmin!listJson.do");
		//BasicHeader cookie=new BasicHeader("cookie", "JSESSIONID=892AA9717999DA40B2DD548FB3301FD5");
		httpGet.setHeader(cookie);
		HttpResponse response = client.execute(httpGet);
		System.out.println(response.getStatusLine());//HTTP/1.1 200 
		String body = EntityUtils.toString(response.getEntity());
		System.out.println(body);
		Assert.assertEquals(body.contains("\"total\":23"), true);
	}
	//@Test
	public void addManager() throws Exception, IOException {
		//CloseableHttpClient client=HttpClients.createDefault();
		//BasicHeader cookie=new BasicHeader("cookie", "JSESSIONID=892AA9717999DA40B2DD548FB3301FD5");
		HttpPost httpPost=new HttpPost(host+"/javamall/core/admin/userAdmin!addSave.do?ajax=yes");
		//加参数
		NameValuePair username=new BasicNameValuePair("adminUser.username", "shamo0001");
		NameValuePair password=new BasicNameValuePair("adminUser.password", "123456");
		NameValuePair realname=new BasicNameValuePair("adminUser.realname", "沙漠");
		NameValuePair founder=new BasicNameValuePair("adminUser.founder", "0");
		NameValuePair roleids=new BasicNameValuePair("roleids", "2");
		NameValuePair state=new BasicNameValuePair("adminUser.state", "1");
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(username);
		params.add(password);
		params.add(realname);
		params.add(founder);
		params.add(roleids);
		params.add(state);
		HttpEntity entity=new UrlEncodedFormEntity(params);//把键值对集合转换成entity对象
		httpPost.setEntity(entity);//表示给post请求添加参数
		httpPost.setHeader(cookie);
		HttpResponse response = client.execute(httpPost);
		System.out.println(response.getStatusLine());//HTTP/1.1 200 
		String body = EntityUtils.toString(response.getEntity());
		System.out.println(body);
		String expect="{\"result\":1,\"message\":\"新增管理员成功\"}";
		JSONObject bodyJson=JSONObject.parseObject(body);
		String messageText = bodyJson.getString("message");//利用fastjson第三包解析json格式的响应数据，从中提取单个字段
		Assert.assertEquals(body, expect);
		Assert.assertEquals(messageText, "新增管理员成功");
	}
	
	//@Test
	public void upload() throws Exception, IOException {
		HttpPost httpPost=new HttpPost(host+"/pinter/file/api/upload2");
		FileBody fileBody=new FileBody(new File("C:\\Users\\lixio\\Desktop\\a.txt"));
		HttpEntity httpEntity = MultipartEntityBuilder.create().addPart("file", fileBody).build();
		httpPost.setEntity(httpEntity);
		HttpResponse response = client.execute(httpPost);
		System.out.println(response.getStatusLine());//HTTP/1.1 200 
		String body = EntityUtils.toString(response.getEntity());
		System.out.println(body);
		JSONObject bodyJson=JSONObject.parseObject(body);
		String messageText = bodyJson.getString("message");//利用fastjson第三包解析json格式的响应数据，从中提取单个字段
		Assert.assertEquals(messageText, "上传成功");	
	}
	
//	@Test
	public void postJson() throws Exception {
		HttpPost httpPost=new HttpPost(host+"/pinter/com/register");
		//参数，json格式的参数我们把转成StringEntity对象
		String json="{\"userName\":\"test\",\"password\":\"1234\",\"gender\":1,\"phoneNum\":\"110\",\"email\":\"beihe@163.com\",\"address\":\"Beijing\"}";
		HttpEntity entity=new StringEntity(json, "UTF-8");//把json格式参数字符串转成entity对象
		BasicHeader contentType=new BasicHeader("Content-Type", "application/json");
		httpPost.setEntity(entity);
		httpPost.setHeader(contentType);
		HttpResponse response = client.execute(httpPost);
		System.out.println(response.getStatusLine());//HTTP/1.1 200 
		String body = EntityUtils.toString(response.getEntity());
		System.out.println(body);
		JSONObject bodyJson=JSONObject.parseObject(body);
		String messageText = bodyJson.getString("message");//利用fastjson第三包解析json格式的响应数据，从中提取单个字段
		Assert.assertEquals(messageText, "注册成功");	
	}
	//@Test
	public void postXML() throws Exception, IOException {
		//http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx
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
		
		BasicHeader contentType=new BasicHeader("Content-Type", "text/xml");
		HttpEntity entity=new StringEntity(xml, "UTF-8");
		httpPost.setEntity(entity);
		httpPost.setHeader(contentType);
		HttpResponse response = client.execute(httpPost);
		System.out.println(response.getStatusLine());//HTTP/1.1 200 
		String body = EntityUtils.toString(response.getEntity());
		System.out.println(body);
	}
	
	@Test
	public void postJosnKv() throws Exception, Exception {
		//CloseableHttpClient client=HttpClients.createDefault();
		//BasicHeader cookie=new BasicHeader("cookie", "JSESSIONID=892AA9717999DA40B2DD548FB3301FD5");
		HttpPost httpPost=new HttpPost(host+"/pinter/com/buy");
		//加参数
		NameValuePair json=new BasicNameValuePair("param", "{\"skuId\":123,\"num\":10}");
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(json);
		HttpEntity entity=new UrlEncodedFormEntity(params);//把键值对集合转换成entity对象
		httpPost.setEntity(entity);//表示给post请求添加参数
		httpPost.setHeader(cookie);
		HttpResponse response = client.execute(httpPost);
		System.out.println(response.getStatusLine());//HTTP/1.1 200 
		String body = EntityUtils.toString(response.getEntity());
		System.out.println(body);
	}

}
