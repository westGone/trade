package com.trade.project.framework;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class SAXHandler implements ContentHandler {
	protected static final Log logger = LogFactory.getLog(XMLDocParser.class);
	
	private StringBuffer text = new StringBuffer();
	private String tagName = "";
	private String searchID = "";
	private boolean searchMode = false;
	
	public SAXHandler() {}
	
	public SAXHandler(String tagName, String searchID) {
		this.tagName = tagName;
		this.searchID = searchID;
	}
	
	public void setDocumentLocator(Locator locator) {}
	
	public void startDocument() throws SAXException {
		logger.debug(this.getClass().getName() + "[startDocument]");
	}
	
	public void endDocument() throws SAXException {
		logger.debug(this.getClass().getName() + "[endDocument]");
	}
	
	public void startPrefixMapping(String prifix, String uri) throws SAXException {}
	public void endPrefixMapping(String prefix) throws SAXException {}
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}
	public void processingInstruction(String target, String data) throws SAXException {}
	public void skippedEntity(String name) throws SAXException {}
	
	/**
	* Tag 시작점
	* @param uri
	* @param localName
	* @param qName
	* @param atts
	* @throws org.xml.sax.SAXException
	*/
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		if(qName.equals(tagName)) {
			if(searchID!=null&&!searchID.equals("")) {
				if(atts.getValue("id").equals(searchID)) {
					searchMode = true;
				} else searchMode = false;
			} else searchMode = false;
		} else searchMode = false;
	}
	
	/**
	* 태그 종료점
	* @param uri
	* @param localName
	* @param qName
	* @throws org.xml.sax.SAXException
	*/
	public void endElement(String uri, String localName, String qName) throws SAXException {}
	
	/**
	* Tag 시작점부터 종료점까지의 문자열
	* @param ch
	* @param start
	* @param length
	* @throws org.xml.sax.SAXException
	*/
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(searchMode) {
			String content = new String(ch, start, length);
			text.append(content);
		}
	}
	
	public String getText() {
		return text.toString();
	}
	
	public void setSearch(String tagName, String searchID) {
		this.tagName = tagName;
		this.searchID = searchID;
	}
}