package com.trade.project.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonBeanProcessor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.trade.project.controller.CommonController;
import com.trade.project.dao.NewsDao;

@Service("newsService")
public class NewsService {
	protected static final Log logger = LogFactory.getLog(NewsService.class);

	public String articleContent;
	public String articleCategory;
	public String articleDate;	
	public String articleTitle;	
	public String articleUrl;	
	private Document doc;
	private Elements ele;
	
	@Resource(name="newsDao")
	NewsDao newsDao;
	
	@Resource(name="commonController")
	CommonController commonController;
	
	/**
	 * Get Crawling Info
	 * @return
	 * @throws java.lang.Exception
	 */
	public List<Map<String, Object>> getNewsCrawlingInfo() throws java.lang.Exception {
		List<Map<String, Object>> crawlingInfo = newsDao.getNewsCrawlingInfo();
		return crawlingInfo;
	}
	
	/*############################################Crawling START###########################################*/
	/**
	 * Crawling News
	 * @throws java.lang.Exception
	 */
	public void crawlingNews() throws java.lang.Exception {
		List<Map<String, Object>> crawlingInfo = newsDao.getNewsCrawlingInfo();
		
		if(crawlingInfo != null) {
			//전역변수 초기화
			articleContent 	= "";
			articleDate 	= "";
			articleTitle 	= "";
			articleUrl 		= "";
			
			setArticleDate();
			
			for(int i=0; i<crawlingInfo.size(); i++) {
				int start 	= Integer.parseInt(crawlingInfo.get(i).get("AREA_ADDRESS").toString());
				int end 	= Integer.parseInt(crawlingInfo.get(i).get("AREA_END").toString());
				for(int k = start ; k< end ; k++){
					setURL(crawlingInfo.get(i), k);
					System.out.println(articleUrl);
					setArticleCategory(crawlingInfo.get(i));
					
					if(articleCategory != ""){
						Map<String,Object> newsInfo = new HashMap<String, Object>();
						
						setArticleContent(crawlingInfo.get(i));
						
						newsInfo.put("title"	, articleTitle);
						newsInfo.put("content"	, articleContent);
						newsInfo.put("url"		, articleUrl);
						newsInfo.put("regDate"	, articleDate);
						newsInfo.put("company"	, crawlingInfo.get(i).get("NEWS_COMPANY").toString());
						
						newsDao.saveCrawlingNews(newsInfo);
					}
				}
			}
		} 
	}//END crawlingNews 
	
	//Crawling URL Setting
	public void setURL(Map<String, Object> map, int i) throws Exception{
		articleUrl = map.get("NEWS_URL").toString()+articleDate+String.format("%06d", i);
		doc = Jsoup.connect(articleUrl).get();
	}
	
	//Crawling 검색필터 Setting
	public void setArticleCategory(Map<String, Object> map){
		articleCategory = "";
		String temp 	= null;
		ele = doc.select("body");

		for(Element e: ele){
			if(e.getElementById(map.get("SEARCH_RANGE").toString()) != null) {
				temp = e.getElementById(map.get("SEARCH_RANGE").toString()).text();
				if(temp.contains("비트코인")){
					articleCategory = "비트코인";
				}
				else if(temp.contains("이더리움")){
					articleCategory = "이더리움";
				}
				else if(temp.contains("가상화폐")){
					articleCategory = "가상화폐";
				}
			}
		}
	}
	
	//Crawling Date Setting
	public void setArticleDate(){
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMdd");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		//TODO 스케줄러 돌릴때는 하루 빼는부분 삭제
		cal.add(Calendar.DATE, -1);
		
		articleDate = simpleFormat.format(cal.getTime());
	}
	
	//Crawling Content Setting
	public void setArticleContent(Map<String, Object> map){
		ele = doc.select(map.get("CONTENT_POSITION").toString());
		articleContent = ele.text();
		System.out.println(ele.text());
		
		ele = doc.select(map.get("TITLE_POSITION").toString());
		articleTitle = ele.text();
		System.out.println(ele.text());
	}
	
	/*############################################Crawling END###########################################*/

	/**
	 * Get News List
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getNewsList(JSONObject reqJsonObject, JSONObject resJsonObject) throws java.lang.Exception {
		commonController.setListData(newsDao.getNewsList(reqJsonObject), resJsonObject);
	}
	
	/**
	 * Get Index News List
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getIndexNewsList(JSONObject reqJsonObject, JSONObject resJsonObject) throws java.lang.Exception {
		commonController.setListData(newsDao.getIndexNewsList(reqJsonObject), resJsonObject);
	}
	
	/**
	 * 뉴스 상세보기
	 * @param value
	 * @param resJsonObject
	 * @throws java.lang.Exception
	 */
	public void getNewsDetail(Map<String, Object> value, JSONObject resJsonObject) throws java.lang.Exception {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonBeanProcessor(java.sql.Date.class, new JsDateJsonBeanProcessor());
		
		Map<String, Object> resultMap = newsDao.getNewsDetail(value);
		
		resJsonObject.put("data", JSONObject.fromObject(resultMap, jsonConfig));
	}

}







