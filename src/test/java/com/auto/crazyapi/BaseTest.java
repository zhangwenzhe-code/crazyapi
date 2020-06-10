package com.auto.crazyapi;

import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import org.apache.http.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.auto.crazyapi.utils.JdbcDataUtil;
import com.auto.crazyapi.utils.PropertyUtil;

/**
 * 
* @ClassName: Day2_1_BaseTest
* @Description: 初始化全局信息
* @author Kitty
* @date 2019年12月24日 下午3:40:42
*
 */
public class BaseTest {
  CloseableHttpClient client;
  Header cookieHeader;
  String host;
  Connection conn;
  
  @BeforeMethod
  public void setup(){
    client =HttpClients.createDefault();
    Properties httpProps=PropertyUtil.getProperties("/http.properties");
    String cookieValue=(String) httpProps.get("http.cookie");
    cookieHeader=new BasicHeader("Cookie", "JSESSIONID=" + cookieValue);
    host=httpProps.getProperty("http.url");
    
    Properties dbProps=PropertyUtil.getProperties("/db.properties");
    String dburl=dbProps.getProperty("jdbc.url");
    String dbuname=dbProps.getProperty("jdbc.username");
    String dbpassword=dbProps.getProperty("jdbc.password");
    conn=JdbcDataUtil.getConn(dburl, dbuname, dbpassword);
  }
  
  @AfterMethod
  public void tearDown() throws IOException{
    client.close();
    JdbcDataUtil.closeConn(conn);
  }
  @Test
  public void test() {
	  
  }
  
  
}
