//매일 신문 전용
package com.trade.project.crawling;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.trade.project.service.NewsService;

class CSVFormat{
	public String articleContent;
	public String articleCategory;
	public String articleDate;	
	public String articleTitle;	
	
	public void resetData(){
		this.articleCategory 	= null;
		this.articleContent 	= null;
		this.articleDate 		= null;
		this.articleTitle 		= null;
	}
}

public class CrawlingNews{
	
	public String field 		= "날짜, URL, 분류, 내용 \r\n";
	public String csvFileName 	= "C:/Users/jiwonseo/newsTest/eco2015.csv";
	public String dateTag 		= "BUILD";
	public String category 		= "KEYWORDS";
	private CSVFormat format 	= new CSVFormat();
	public String url;
	private Document doc;
	private Elements ele;// = doc.select("div.article");
	private BufferedWriter writer;
	
	NewsService newsService = new NewsService();
	
	List<Map<String, Object>> crawlingInfo = newsService.getNewsCrawlingInfo();
	
	SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date now = new Date();
	String nowDate = simpleFormat.format(now);
	
	public CrawlingNews() throws Exception{
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFileName), "MS949"));
		writer.write(field);
		
	}
	
	//Crawling Thread
	public void run(){
		
		//DB에 저장되어 있는 News 크롤링 정보 List
		for(int k=0; k<crawlingInfo.size(); k++) {
			for(int i = 500000 ; i< 500050 ; i++){
				format.resetData();
				try{
					setURL(k, i);
					setArticleCategory();
					if(format.articleCategory != null){
						Map<String,String> newsInfo = new HashMap<String, String>();
						
						System.out.println(i);
						setArticleDate();
						setArticleContent(k);
						
						newsInfo.put("title"	, format.articleTitle);
						newsInfo.put("content"	, format.articleContent);
						newsInfo.put("url"		, url);
						newsInfo.put("regDate"	, nowDate);
						
//						writer.write(format.articleDate+" , "+i+" , "+format.articleCategory +" , "+format.articleContent +"\r\n");
					}

				}catch(Exception e){

				}
			}
		}
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setURL(int k, int i) throws Exception{
		url = crawlingInfo.get(k).get("NEWS_URL").toString()+i;
		doc = Jsoup.connect(url).get();
	}
	
	public void setArticleCategory(){
		String temp = null;
		ele = doc.select("body");

		for(Element e: ele){
			temp = e.getElementsByTag("body").iterator().next().text();
			if(temp.contains(" 비트코인")){
				format.articleCategory = "비트코인";
			}
			else if(temp.contains(" 이더리움")){
				format.articleCategory = "이더리움";
			}
			else if(temp.contains(" 리플")){
				format.articleCategory = "리플";
			}
		}
	}
	
	public void setArticleDate(){
		ele = doc.select("META");
		for(Element e : ele)
			if(e.attr("Name").equals(dateTag))
				format.articleDate = e.attr("Content");
	}
	
	public void setArticleContent(int k){
		ele = doc.select(crawlingInfo.get(k).get("CONTENT_POSITION").toString());
		format.articleContent = ele.text();
		System.out.println(ele.text());
		
		ele = doc.select(crawlingInfo.get(k).get("TITLE_POSISION").toString());
		format.articleTitle = ele.text();
		System.out.println(ele.text());
	}
	
	public void concatArticleData(){
		
		
	}
}
