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
import com.trade.project.dao.CommonDao;
import com.trade.project.dao.SettingsDao;

@Service("settingsService")
public class SettingsService {
	protected static final Log logger = LogFactory.getLog(SettingsService.class);

	@Resource(name="settingsService")
	SettingsService settingsService;
	
	@Resource(name="settingsDao")
	SettingsDao settingsDao;
	
	@Resource(name="commonController")
	CommonController commonController;
	
	/**
	 * Get Code Administration List
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getCodeDefineList(JSONObject reqJsonObject, JSONObject resJsonObject) throws java.lang.Exception {
		commonController.setListData(settingsDao.getCodeDefineList(reqJsonObject), resJsonObject);
	}
	
}







