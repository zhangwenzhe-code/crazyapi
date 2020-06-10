package com.auto.project2;

import java.util.HashMap;
import java.util.Map;

import com.auto.crazyapi.utils.CrazyHttpClient;
import com.auto.crazyapi.utils.PropertyUtil;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年2月15日 下午5:08:21 

*/
public class Order {
	public static String order(String host,String addressId,String paymentId,String typeId) throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		//headers.put("Cookie", "JSESSIONID="+cookie);
		Map<String, String> params=new HashMap<String, String>();
		params.put("addressId", addressId);
		params.put("paymentId", paymentId);
		params.put("typeId", typeId);
		//Map<String, String> params=PropertyUtil.getAllKeyValue("/data/order.properties");
		
		String response = CrazyHttpClient.sendPost(host+"/javamall/api/shop/order!create.do", headers, params);
		return response;
	}
	public static String order(String host,Map<String,String> params) throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		//headers.put("Cookie", "JSESSIONID="+cookie);
//		Map<String, String> params=new HashMap<String, String>();
//		params.put("addressId", addressId);
//		params.put("paymentId", paymentId);
//		params.put("typeId", typeId);
		//Map<String, String> params=PropertyUtil.getAllKeyValue("/data/order.properties");
		
		String response = CrazyHttpClient.sendPost(host+"/javamall/api/shop/order!create.do", headers, params);
		return response;
	}

}
