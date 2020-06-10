package com.auto.crazyapi.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class PropertyUtil {
  
	private static Map<String, Properties> propFileMap = new ConcurrentHashMap<String, Properties>();

	public static void main(String[] args) {
		String property = System.getProperty("user.dir");
		System.out.println(property);
		Properties httpProps=PropertyUtil.getProperties(property+"/resources/http.properties");
		//getAllKeyValue("/data/order.properties");
//		Properties prop2 = PropertyUtil.getProperties("/db.properties");
//		System.out.println("jdbc.url: " + prop2.getProperty("jdbc.url"));
//		Properties prop3 = PropertyUtil.getProperties("/http.properties");
//		System.out.println("jdbc.url: " + prop3.getProperty("http.cookie"));
	}

	public static Properties getProperties(String fileName) {
		System.out.println(fileName);
		Properties prop=readProperties(fileName);
		//Properties prop = propFileMap.get(fileName);
		if (prop == null) {
			prop = new Properties();
		}
		InputStream is = null;
		try {
			is = new FileInputStream(fileName);
			prop.load(is);
			propFileMap.put(fileName, prop);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return prop;
	}
	
	/**
	 * 用在读取android_caps和ios_caps配置文件时
	 * @return
	 */
	public static HashMap<String, String> getAllKeyValue(String file) {
		HashMap<String, String> keyValus = new HashMap<String, String>();
		Iterator it = getProperties(file).entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			keyValus.put(key.toString(), value.toString());
			System.out.println(("get caps " + key.toString() + ":" + value.toString()));
		}
		return keyValus;
	}
	private static Properties readProperties(String file){
		Properties properties = new Properties();
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			properties.load(bf);
			inputStream.close(); // 关闭流
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
		return properties;
	}
}
