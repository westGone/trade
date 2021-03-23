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
import org.springframework.stereotype.Service;

import com.trade.project.controller.CommonController;
import com.trade.project.dao.NewsDao;

@Service("newsService")
public class NewsService {
	protected static final Log logger = LogFactory.getLog(NewsService.class);

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
	
}







