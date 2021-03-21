package com.trade.project.framework;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.XMLReader;

import com.trade.project.controller.CommonController;

@Component("XMLDocParser")
public class XMLDocParser {
	private static final Log logger = LogFactory.getLog(XMLDocParser.class);
	
	/**
	* XML Document Parsing
	* @param path
	* @param fileName
	* @return
	* @throws java.lang.Exception
	*/
	public Document getDOMDocument(String path, String fileName) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(path + fileName);
		
		return document;
	}
	
	/**
	 * XML Document Parsing
	 * @param packageName
	 * @param fileName
	 * @param searchID
	 * @return
	 * @throws Exception
	 */
	public String getSAXDocument(String packageName, String fileName, String searchID) throws Exception {
		 String path = XMLDocParser.class.getResource("/").getPath();
		 path = path.substring(0,path.length() - 1);
		 path = path.substring(0, path.lastIndexOf("/") + 1);
		 path = path + "classes/resource/jdbc/" + packageName + "/";

		 String xmlStr = getSAXDocument(path, fileName + ".xml", "sql", searchID);
		 xmlStr = xmlStr.replaceAll(":cryptKey", "'" + CommonController.getSaultKey() + "'");
		 //xmlStr = xmlStr.replaceAll("TO_NCHAR", "UNISTR");
		 
		 if(xmlStr != null){
			 xmlStr += (new StringBuffer()).append("  /* ADDED inform by framework - ").append(packageName).append(".").append(fileName).append(".").append(searchID).append(" */ ");
		 }
		 
		 return xmlStr;
	}

	/**
	 * XML Document Parsing
	 * @param path
	 * @param fileName
	 * @param tagName
	 * @param searchID
	 * @return
	 * @throws Exception
	 */
	public String getSAXDocument(String path, String fileName, String tagName, String searchID) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		
		SAXParser parser = factory.newSAXParser();
		XMLReader reader = parser.getXMLReader();
		SAXHandler handler = new SAXHandler(tagName, searchID);
		
		reader.setContentHandler(handler);
		reader.parse(path + fileName);
		
		return handler.getText() != null ? handler.getText(): "";
	}
	
	/**
	* Parsing 한 XML Document 객체의 Node List 중에 searchID 를 ID 값으로 하는 항목 조회
	* @param nodeList
	* @param sqlID
	* @return
	*/
	public String getSQLText(NodeList nodeList, String sqlID){
		logger.debug(this.getClass().getName() + "[getSQLText]");
		
		String text = "";
		
		for(int i = 0, n = nodeList.getLength(); i < n; i++) {
			Element el = (Element) nodeList.item(i);
			
			if(el.getAttribute("id").equals(sqlID)) {
				Text tx = (Text) el.getFirstChild();
				text = tx.getData();
			}
		}
		
		return text;
	}
	
	/**
	* Parsing 한 XML Document 객체의 tagName중에 searchID 를 ID 값으로 하는 항목 조회
	* @param document
	* @param tagName
	* @param searchID
	* @return
	*/
	public String getNodeText(Document document, String tagName, String searchID) {
		logger.debug(this.getClass().getName() + "[getNodeText]");
		
		StringBuffer strBuf = new StringBuffer();
		
		if(tagName!=null&&!tagName.equals("")&&searchID!=null&&!searchID.equals("")) {
			Element eRoot = document.getDocumentElement();
			NodeList nodeList = eRoot.getElementsByTagName(tagName);
			
			Element el = null;
			for(int i = 0, n = nodeList.getLength(); i < n; i++) {
				el = (Element) nodeList.item(i);
				
				if(el.getAttribute("id").equals(searchID)) {
					String tempStr = "";
					Node node = null;
					
					for(int k = 0, n2 = el.getChildNodes().getLength(); k < n2; k++) {
						node = el.getChildNodes().item(k);
						tempStr = searchNode(nodeList, "id", node.getTextContent());
						
						if(tempStr.equals("")) {
							strBuf.append(node.getTextContent());
						} else {
							strBuf.append(tempStr);
						}
					}
					break;
				}
			}
		}
		
		return strBuf.toString();
	}
	
	public String searchNode(NodeList nodeList, String key, String search) {
		String text = "";

		Element el = null;
		for(int i = 0, n = nodeList.getLength(); i < n; i++) {
			el = (Element) nodeList.item(i);
			
			if(el.getAttribute(key).equals(search)) {
				Text tx = (Text) el.getFirstChild();
				text = tx.getData();
			}
		}
		
		return text;
	}
	
	/**
	* Parsing 한 XML Document 객체의 tagName중에 배열에 담긴 searchID 를 ID 값으로 하는
	* 항목을 모두 조회해서 배열 순서대로 병합하여 반환
	* @param document
	* @param tagName
	* @param searchID
	* @return
	*/
	public String getNodeAddedText(Document document, String tagName, String[] searchID) {
		logger.debug(this.getClass().getName() + "[getNodeText]");
		
		StringBuffer strBuffer = new StringBuffer();
		if(searchID.length>0&&tagName!=null&&!tagName.equals("")) {
			Element eRoot = document.getDocumentElement();
			NodeList nodeList = eRoot.getElementsByTagName(tagName);

			for(int i = 0, n = nodeList.getLength(); i < n; i++) {
				Element el = (Element) nodeList.item(i);
				
				for(int k = 0, n2 = searchID.length; k < n2; k++) {
					if(el.getAttribute("id").equals(searchID[k])) {
						Text tx = (Text) el.getFirstChild();
						strBuffer.append(tx.getData());
					}
				}
			}
		}
		
		return strBuffer.toString();
	}
}