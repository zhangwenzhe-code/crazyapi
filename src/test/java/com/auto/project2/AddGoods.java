package com.auto.project2;

import java.util.HashMap;
import java.util.Map;

import com.auto.crazyapi.utils.CrazyHttpClient;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年2月15日 下午4:56:58 

*/
public class AddGoods {
	
	public static String addGoods(String host,String goodsid,String num) throws Exception {
		Map<String, String> headers=new HashMap<String, String>();
		Map<String, String> params=new HashMap<String, String>();
		params.put("goodsid", "118");
		params.put("num", "1");
		String response = CrazyHttpClient.sendPost(host+"/javamall/api/shop/cart!addGoods.do", headers, params);
		System.out.println(response);
		return response;
	}

}
