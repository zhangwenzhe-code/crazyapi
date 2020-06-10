package com.auto.crazyapi;
/** 

* @author 作者 沙陌 

* @version 创建时间：2020年2月8日 下午4:31:13 

*/

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HttpGet20200208 {
	
	CloseableHttpClient client;//创建一个客户端对象
	HttpGet httpGet;
	HttpPost httpPost;
	HttpResponse response;
	
	@BeforeClass
	public void initClient() {
		client=HttpClients.createDefault();
	}
	
	@Test
	public void httpGetTest() throws Exception, Exception {
		httpGet=new HttpGet("http://localhost:8080/pinter/com/getSku?id=1");
		response = client.execute(httpGet);
		System.out.println(response.getStatusLine());//这个响应的http版本和响应状态码
		System.out.println(response.getStatusLine().getStatusCode());//响应状态码
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		String resBody = EntityUtils.toString(response.getEntity());
		System.out.println(resBody);
		Assert.assertEquals(resBody.contains("\"code\":\"0\""), true);
	}
	@Test
	public void httpPost() throws Exception {
		httpPost=new HttpPost("http://localhost:8080/pinter/com/login");
		NameValuePair username=new BasicNameValuePair("userName","admin");//参数
		NameValuePair password=new BasicNameValuePair("password", "123456");//参数
		List<NameValuePair> params=new ArrayList<NameValuePair>();//参数集合对象
		params.add(username);
		params.add(password);
		StringEntity entity=new UrlEncodedFormEntity(params);//将参数集合对象转换成HttpEntity对象
		httpPost.setEntity(entity);//设置post的entity
		response= client.execute(httpPost);//发起请求
		System.out.println(response.getStatusLine());//这个响应的http版本和响应状态码
		System.out.println(response.getStatusLine().getStatusCode());//响应状态码
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		String resBody = EntityUtils.toString(response.getEntity());
		System.out.println("post======"+resBody);
		Assert.assertEquals(resBody.contains("\"message\":\"success\""), true);
	}
	@Test
	public void upload() throws Exception, Exception {
		httpPost=new HttpPost("http://localhost:8080/pinter/file/api/upload2");
		FileBody fileBody=new FileBody(new File("C:\\Users\\lixio\\Desktop\\user.txt"));
		HttpEntity httpEntity = MultipartEntityBuilder.create().addPart("file", fileBody).build();
		httpPost.setEntity(httpEntity);
		//httpPost.addHeader("Content-Type", "multipart/form-data");
		response= client.execute(httpPost);//发起请求
		System.out.println(response.getStatusLine());//这个响应的http版本和响应状态码
		System.out.println(response.getStatusLine().getStatusCode());//响应状态码
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		String resBody = EntityUtils.toString(response.getEntity());
		System.out.println("post======"+resBody);
		Assert.assertEquals(resBody.contains("\"message\":\"上传成功\""), true);
	}
	@Test
	public void download() throws Exception, Exception {
		httpGet=new HttpGet("http://localhost:8080/pinter/file/api/download?id=24");
		response = client.execute(httpGet);
		System.out.println(response.getStatusLine());//这个响应的http版本和响应状态码
		System.out.println(response.getStatusLine().getStatusCode());//响应状态码
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		String resBody = EntityUtils.toString(response.getEntity());
		System.out.println(resBody);
		//Assert.assertEquals(resBody.contains("\"code\":\"0\""), true);
	}
	@Test
	public void register() throws Exception, Exception {
		httpPost=new HttpPost("http://localhost:8080/pinter/com/register");
		String json="{\"userName\":\"test\",\"password\":\"1234\",\"gender\":1,\"phoneNum\":\"110\",\"email\":\"beihe@163.com\",\"address\":\"Beijing\"}";
		StringEntity entity=new StringEntity(json, "UTF-8");
		httpPost.setEntity(entity);
		httpPost.addHeader("Content-Type","application/json");
		response = client.execute(httpPost);
		System.out.println(response.getStatusLine());//这个响应的http版本和响应状态码
		System.out.println(response.getStatusLine().getStatusCode());//响应状态码
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		String resBody = EntityUtils.toString(response.getEntity());
		System.out.println(resBody);
		Assert.assertEquals(resBody.contains("注册成功"), true);
	}
	
	@Test
	public void sentXML() throws Exception, Exception {
		httpPost=new HttpPost("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx");
		String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
				"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" + 
				"  <soap:Body>\r\n" + 
				"    <getMobileCodeInfo xmlns=\"http://WebXml.com.cn/\">\r\n" + 
				"      <mobileCode>18729399607</mobileCode>\r\n" + 
				"      <userID></userID>\r\n" + 
				"    </getMobileCodeInfo>\r\n" + 
				"  </soap:Body>\r\n" + 
				"</soap:Envelope>";
		httpPost.addHeader("Content-Type","text/xml; charset=utf-8");
		Header[] headers = httpPost.getAllHeaders();
//		for(Header h:headers) {
//			System.out.println(h.toString());
//			System.out.println("header:"+h.getName()+" value:"+h.getValue());
//		}
		StringEntity xmlEntity=new StringEntity(xml, "UTF-8");
		httpPost.setEntity(xmlEntity);
		response = client.execute(httpPost);
		System.out.println(response.getStatusLine());//这个响应的http版本和响应状态码
		System.out.println(response.getStatusLine().getStatusCode());//响应状态码
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		String resBody = EntityUtils.toString(response.getEntity());
		System.out.println(resBody);
		Assert.assertEquals(resBody.contains("陕西 西安 陕西移动全球通卡"), true);
	}
	
	@Test
	public void httpsTest() throws Exception, Exception {
		httpGet=new HttpGet("https://www.baidu.com");
		response = client.execute(httpGet);
		System.out.println(response.getStatusLine());//这个响应的http版本和响应状态码
		System.out.println(response.getStatusLine().getStatusCode());//响应状态码
		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		String resBody = EntityUtils.toString(response.getEntity());
	}
}
