package com.Fee.common.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlUtils {
	
	private static final Logger log = Logger.getLogger(XmlUtils.class);
	
    /**
     * 序列化XML
     * @param object
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    public static String Marshal(Object object) throws JAXBException, UnsupportedEncodingException
    {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");        //编码格式
       // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);   //格式化XML

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marshaller.marshal(object, outputStream);

        return outputStream.toString("GBK");
    }

    /**
     * 反序列化XML
     * @param cls
     * @param objStr
     * @param <T>
     * @return
     * @throws JAXBException
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T UnMarshal(Class cls, String objStr) throws JAXBException, UnsupportedEncodingException
    {
        JAXBContext context = JAXBContext.newInstance(cls);
        Unmarshaller unMarshaller = context.createUnmarshaller();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(objStr.getBytes("GBK"));
        return (T)unMarshaller.unmarshal(inputStream);
    }
    
    /**
     * 获取XML节点值
     * @param xmlString
     * @param xPath xpath表达式
     * @example //MemberShipResponse/BODY/Member/CRMApplyCode/text()
     * @return Str
     */
	public static String getXmlNoteValue(String xmlString,String xPath) {
		String value = null;
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			StringReader stringReader = new StringReader(xmlString);
			InputSource inputSource = new InputSource(stringReader);
			Document doc = builder.parse(inputSource);
			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			XPathExpression expr = xpath.compile(xPath);
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < nodes.getLength(); i++) {
				sb.append(nodes.item(i).getNodeValue());
			}
			value = sb.toString();
		} catch (Exception e) {
			log.error("getXmlNoteValue-error" + e.getMessage(), e);
		}
		return value;
	}
	
	
	/**
	 * 获取XML nodes 信息
	 * @param xmlString
	 * @param xPath
	 * @return
	 */
	public static NodeList getXmlNoteList(String xmlString,String xPath){
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			StringReader stringReader = new StringReader(xmlString);
			InputSource inputSource = new InputSource(stringReader);
			Document doc = builder.parse(inputSource);
			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			XPathExpression expr = xpath.compile(xPath);
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			return nodes;
		} catch (Exception e) {
			log.error("getXmlNoteValue-error" + e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 获取XML 节点值
	 * @param noteName 节点名称
	 * @param xmlContent xml内容
	 * @return
	 */
	public static String getXMLNoteValue(String noteName,String xmlContent){
		int s = xmlContent.indexOf("<"+noteName);
		int e = xmlContent.indexOf("</"+noteName);
		if(s==-1||e==-1){
			return "";
		}
		String string=xmlContent.substring(s,e);
		String result="";
		if(string.contains("List")){
			result=string.replace("<"+noteName+">", "").replace("<"+noteName+"List>", "");
		}else{
			result=xmlContent.substring(s,e).replace("<"+noteName+">", "");
		}
		return result;
				
	}
	
/*	public static void main(String[] args) {
		//String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<MemberOrderApplyCrmRelationReponse>\r\n  <BODY>\r\n    <OrderInfo>\r\n      <BusinessCode>20160119180056_0_31</BusinessCode>\r\n      <Mobile>13622261351</Mobile>\r\n      <ProductInfos>\r\n        <ProductInfo>\r\n          <ProductCode>8585</ProductCode>\r\n          <ProductName>3元流量包（集团标准资费）</ProductName>\r\n          <OrderNum />\r\n          <FlowValue>10</FlowValue>\r\n          <DealState>4</DealState>\r\n          <CrmApplyCode>80009750661792</CrmApplyCode>\r\n        </ProductInfo>\r\n      </ProductInfos>\r\n    </OrderInfo>\r\n    <OrderInfo>\r\n      <BusinessCode>20160119174727_0_30</BusinessCode>\r\n      <Mobile>13622261351</Mobile>\r\n      <ProductInfos>\r\n        <ProductInfo>\r\n          <ProductCode>8585</ProductCode>\r\n          <ProductName>3元流量包（集团标准资费）</ProductName>\r\n          <OrderNum />\r\n          <FlowValue>10</FlowValue>\r\n          <DealState>4</DealState>\r\n          <CrmApplyCode>80009750660130</CrmApplyCode>\r\n        </ProductInfo>\r\n      </ProductInfos>\r\n    </OrderInfo>\r\n    <OrderInfo>\r\n      <BusinessCode>20160119172746_0_29</BusinessCode>\r\n      <Mobile>13622261351</Mobile>\r\n      <ProductInfos>\r\n        <ProductInfo>\r\n          <ProductCode>8585</ProductCode>\r\n          <ProductName>3元流量包（集团标准资费）</ProductName>\r\n          <OrderNum />\r\n          <FlowValue>10</FlowValue>\r\n          <DealState>4</DealState>\r\n          <CrmApplyCode>80009749848420</CrmApplyCode>\r\n        </ProductInfo>\r\n      </ProductInfos>\r\n    </OrderInfo>\r\n  </BODY>\r\n</MemberOrderApplyCrmRelationReponse>";
		String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<MemberOrderApplyCrmRelationReponse>\r\n  <BODY>\r\n    <OrderInfo>\r\n      <BusinessCode>20160119172746_0_29</BusinessCode>\r\n      <Mobile>13622261351</Mobile>\r\n      <ProductInfos>\r\n        <ProductInfo>\r\n          <ProductCode>8585</ProductCode>\r\n          <ProductName>3元流量包（集团标准资费）</ProductName>\r\n          <OrderNum />\r\n          <FlowValue>10</FlowValue>\r\n          <DealState>4</DealState>\r\n          <CrmApplyCode>80009749848420</CrmApplyCode>\r\n        </ProductInfo>\r\n      </ProductInfos>\r\n    </OrderInfo>\r\n  </BODY>\r\n</MemberOrderApplyCrmRelationReponse>";
		System.out.println(xmlStr);
		NodeList nodes = XmlUtils.getXmlNoteList(xmlStr, "//MemberOrderApplyCrmRelationReponse/BODY/OrderInfo");
		SendOrderResult result=null;
		for (int i = 0; i < nodes.getLength(); i++) {
			Node nNode = nodes.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String orderId = eElement.getElementsByTagName("BusinessCode").item(0).getTextContent();
				String state = eElement.getElementsByTagName("DealState").item(0).getTextContent();
				String orderNum = eElement.getElementsByTagName("CrmApplyCode").item(0).getTextContent();
				String order_id = FlowUtils.getOrderId(orderId);

			}
		}
	}*/
}
