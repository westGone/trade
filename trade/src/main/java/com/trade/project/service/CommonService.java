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

import com.trade.project.dao.CommonDao;

@Service("commonService")
public class CommonService {
	protected static final Log logger = LogFactory.getLog(CommonService.class);

	@Resource(name="commonService")
	CommonService commonService;
	
	@Resource(name="commonDao")
	CommonDao commonDao;
	
//	/**
//	 * Bsa 리스트 가져오기
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	public void getBsaList(JSONObject reqJsonObject, JSONObject resJsonObject) throws java.lang.Exception {
//		Common.setListData(bsaDao.getBsaList(reqJsonObject), resJsonObject);
//	}
	
	
	/**
	 * 사용자가 선택한 언어의 Message 호출
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getMessageLang(Map<String, Object> map, JSONObject resJsonObject) throws java.lang.Exception {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonBeanProcessor(java.sql.Date.class, new JsDateJsonBeanProcessor());
		
		Map<String, Object> resultMap = commonDao.getMessageLang(map);
		
		resJsonObject.put("data", JSONObject.fromObject(resultMap, jsonConfig));
	}
	
	/**
	 * DII CODE 저장
	 * @param value
	 * @throws java.lang.Exception
	 */
	public void saveCodeDefine(Map<String, Object> value) throws java.lang.Exception {
		commonDao.saveCodeDefine(value);
	}
	
	/**
	 * 다국어 메세지 가져오기
	 * @param search
	 * @param jsonObject
	 * @throws java.lang.Exception
	 */
	public void getMessage(JSONObject search, JSONObject jsonObject) throws java.lang.Exception {
		jsonObject.put("data", JSONArray.fromObject(commonDao.getMessage(search)));
	}
	
}







