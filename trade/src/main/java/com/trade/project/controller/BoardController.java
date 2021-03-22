package com.trade.project.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trade.project.framework.GlobalUtil;
import com.trade.project.framework.Util;
import com.trade.project.service.BoardService;
import com.trade.project.service.SettingsService;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

@Controller
public class BoardController {
	
	@Resource(name="boardService")
	BoardService boardService;
	
	/**
	 * Get Code Administration List
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/board/getBoardList.do")
	public void getCodeDefineList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject resJsonObject = new JSONObject();
		
		JSONObject reqJsonObject = JSONObject.fromObject(request.getParameter("params"));
		
		boardService.getBoardList(reqJsonObject, resJsonObject);
		
		response.setContentType("application/x-json;  charset=UTF-8");
		response.getWriter().print(resJsonObject);
	}
	
	/**
	 * 게시물 저장
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/board/saveContent.do")
	public void saveContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject resJsonObject = new JSONObject();
		
		Map<String, Object> value = GlobalUtil.convertMapArrayToObject(request.getParameterMap());
		
		boardService.saveContent(value, resJsonObject);
		
		response.setContentType("application/x-json;  charset=UTF-8");
		response.getWriter().print(resJsonObject);
	}
}
