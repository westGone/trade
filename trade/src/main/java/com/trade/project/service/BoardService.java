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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.project.controller.CommonController;
import com.trade.project.dao.BoardDao;
import com.trade.project.dao.CommonDao;
import com.trade.project.dao.SettingsDao;

@Service("boardService")
public class BoardService {
	protected static final Log logger = LogFactory.getLog(BoardService.class);

	@Resource(name="boardService")
	BoardService boardService;
	
	@Resource(name="boardDao")
	BoardDao boardDao;
	
	@Resource(name="commonController")
	CommonController commonController;
	
	/**
	 * Get Board List
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getBoardList(JSONObject reqJsonObject, JSONObject resJsonObject) throws java.lang.Exception {
		commonController.setListData(boardDao.getBoardList(reqJsonObject), resJsonObject);
	}
	
	/**
	 * Get Index Board List
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getIndexBoardList(Map<String, Object> value, JSONObject resJsonObject) throws java.lang.Exception {
		commonController.setListData(boardDao.getIndexBoardList(value), resJsonObject);
	}

	/**
	 * 게시물 저장
	 * @param value
	 * @param resJsonObject
	 * @throws java.lang.Exception
	 */
	public void saveContent(Map<String, Object> value, JSONObject resJsonObject) throws java.lang.Exception {
		boardDao.saveCodeDefine(value);
	}
	
	/**
	 * 게시물 상세보기
	 * @param value
	 * @param resJsonObject
	 * @throws java.lang.Exception
	 */
	public void getContent(Map<String, Object> value, JSONObject resJsonObject) throws java.lang.Exception {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonBeanProcessor(java.sql.Date.class, new JsDateJsonBeanProcessor());
		
		Map<String, Object> resultMap = boardDao.getContent(value);
		
		resJsonObject.put("data", JSONObject.fromObject(resultMap, jsonConfig));
	}
	
}







