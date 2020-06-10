package com.auto.crazyapi.utils;

import com.alibaba.fastjson.JSONObject;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年5月24日 上午11:45:38 

*/
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String parm="{\r\n" + 
				" \"entity\": {\r\n" + 
				"  \"name\": \"沙漠\",\r\n" + 
				"  \"customer_id\": 5,\r\n" + 
				"  \"mobile\": \"18098909090\",\r\n" + 
				"  \"telephone\": \"01023456789\",\r\n" + 
				"  \"email\": \"shddhdd@163.com\",\r\n" + 
				"  \"post\": \"总经理\",\r\n" + 
				"  \"address\": \"北京霍营\",\r\n" + 
				"  \"next_time\": \"2020-04-23 16:03:47\",\r\n" + 
				"  \"remark\": \"这是备注\"\r\n" + 
				" }\r\n" + 
				"}";
		JSONObject jsonObject=JSONObject.parseObject(parm);
		JSONObject jsonObject2 = jsonObject.getJSONObject("entity");
		jsonObject2.replace("customer_id", "46");
		
		System.out.println(jsonObject2.toJSONString());
		String newparm= "{\"entity\":"+jsonObject2.toJSONString()+"}";
		System.out.println(newparm);
	}

}
