package com.trade.project.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trade.project.framework.Util;
import com.trade.project.service.SettingsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

@Controller
public class SettingsController {
	
	@Resource(name="settingsService")
	SettingsService settingsService;
	
	/**
	 * Get Code Administration List
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/settings/getCodeDefineList.do")
	public void getCodeDefineList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject resJsonObject = new JSONObject();
		
		JSONObject reqJsonObject = JSONObject.fromObject(request.getParameter("params"));
		
		settingsService.getCodeDefineList(reqJsonObject, resJsonObject);
		
		response.setContentType("application/x-json;  charset=UTF-8");
		response.getWriter().print(resJsonObject);
	}
	
}
