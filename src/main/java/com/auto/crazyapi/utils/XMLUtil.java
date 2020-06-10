package com.auto.crazyapi.utils;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.xpath.DefaultXPath;

/** 

* @author 作者 沙陌 

* @version 创建时间：2020年2月13日 下午2:33:41 

*/
public class XMLUtil {
	
	public static String getXmlString(String xml,String xpath) throws DocumentException {
		
		Document xmlDoc=DocumentHelper.parseText(xml);
		//DefaultXPath x=new DefaultXPath(xpath);
		Node singleNode = xmlDoc.selectSingleNode(xpath);
		System.out.println(singleNode.getStringValue());
		return singleNode.getStringValue();
//		List<Node> selectNodes = xmlDoc.selectNodes(xpath);
//		for(Node d:selectNodes) {
//			System.out.println(d.getStringValue());
//			System.out.println(1);
//		}
//		//System.out.println(singleNode);
		
		
	}
	public static void main(String[] args) throws Exception {
		getXmlString("<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><getMobileCodeInfoResponse xmlns=\"http://WebXml.com.cn/\"><getMobileCodeInfoResult>18729399607：陕西 西安 陕西移动全球通卡</getMobileCodeInfoResult></getMobileCodeInfoResponse></soap:Body></soap:Envelope>", "//soap:Body");
//		getXmlString("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
//				"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" + 
//				"  <soap:Body>\r\n" + 
//				"    <getMobileCodeInfo xmlns=\"http://WebXml.com.cn/\">\r\n" + 
//				"      <mobileCode>string</mobileCode>\r\n" + 
//				"      <userID>string</userID>\r\n" + 
//				"    </getMobileCodeInfo>\r\n" + 
//				"  </soap:Body>\r\n" + 
//				"</soap:Envelope>", "//soap:Body/*[1]");
	}

}
