package com.auto.crazyapi;
/** 

* @author 作者 沙陌 

* @version 创建时间：2020年2月8日 上午11:32:18 

*/

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

public class UploadTest {
	CloseableHttpClient client=HttpClients.createDefault();
	HttpPost post;
	@Test
	public void upload() throws Exception, IOException {
		FileBody bin = new FileBody(new File("C:\\Users\\lixio\\Desktop\\user.txt"));
		post=new HttpPost("http://localhost:8080/pinter/file/api/upload2");
		//post.addHeader("Content-Type", "multipart/form-data");
		MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		HttpEntity reqEntity=builder.addPart("file", bin).build();
		post.setEntity(reqEntity);
		CloseableHttpResponse httpResponse = client.execute(post);
		System.out.println(httpResponse.getStatusLine());
		System.out.println(EntityUtils.toString(httpResponse.getEntity()));
	}
	@Test
	public void download() throws Exception, IOException {
		HttpGet httpGet=new HttpGet("http://localhost:8080/pinter/file/api/download?id=1");
		
		HttpResponse response=client.execute(httpGet);
		System.out.println(response.getStatusLine());
		System.out.println(EntityUtils.toString(response.getEntity()));
	}
	@Test
	public void registerWithJson() throws Exception, Exception {
		// TODO Auto-generated method stub
		post=new HttpPost("http://localhost:8080/pinter/com/register");
		String json="{\"userName\":\"test\",\"password\":\"1234\",\"gender\":1,\"phoneNum\":\"110\",\"email\":\"beihe@163.com\",\"address\":\"Beijing\"}";
		StringEntity entity=new StringEntity(json, "UTF-8");
		post.setEntity(entity);
		post.addHeader("Content-Type", "application/json");
		HttpResponse response=client.execute(post);
		System.out.println(response.getStatusLine());
		System.out.println(EntityUtils.toString(response.getEntity()));
		

	}
	@Test
	public void login() throws Exception {
		post=new HttpPost("http://localhost:8080/pinter/com/login");
		NameValuePair username=new BasicNameValuePair("userName", "admin");
		NameValuePair password=new BasicNameValuePair("password","123456");
		List<NameValuePair> parms=new ArrayList<NameValuePair>();
		parms.add(username);
		parms.add(password);
		HttpEntity entity=new UrlEncodedFormEntity(parms);
		post.setEntity(entity);
		HttpResponse response=client.execute(post);
		System.out.println(response.getStatusLine());
		System.out.println(EntityUtils.toString(response.getEntity()));
	}
	@Test
	public void xmlTest() throws Exception, Exception {
		String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
				"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" + 
				"  <soap:Body>\r\n" + 
				"    <getMobileCodeInfo xmlns=\"http://WebXml.com.cn/\">\r\n" + 
				"      <mobileCode>18729399607</mobileCode>\r\n" + 
				"      <userID></userID>\r\n" + 
				"    </getMobileCodeInfo>\r\n" + 
				"  </soap:Body>\r\n" + 
				"</soap:Envelope>";
		StringEntity entity=new StringEntity(xml, "UTF-8");
		
		post=new HttpPost("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx");
		post.addHeader("Content-type", "text/xml; charset=utf-8");
		post.setEntity(entity);
		
		HttpResponse response=client.execute(post);
		System.out.println(response.getStatusLine());
		System.out.println(EntityUtils.toString(response.getEntity()));
	}
}
