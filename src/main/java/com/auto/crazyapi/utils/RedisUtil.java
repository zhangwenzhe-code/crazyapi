package com.auto.crazyapi.utils;

import redis.clients.jedis.Jedis;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年1月25日 下午3:11:14 

*/
public class RedisUtil {
	
	public static String getValue(String ip,String key) {
		Jedis jedis=new Jedis(ip);
		return jedis.get(key);
	}
	public static void main(String[] args) {
		Jedis jedis=new Jedis("localhost");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        jedis.append("18729399607", "4567");
        System.out.println(jedis.get("18729399607"));
        System.out.println(jedis.get("72CRM_USER_ADMIN_TOKEN3"));
	}

}
