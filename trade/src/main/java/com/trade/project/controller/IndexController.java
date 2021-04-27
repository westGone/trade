package com.trade.project.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trade.project.crawling.CrawlingNews;
import com.trade.project.framework.GlobalUtil;
import com.trade.project.framework.Util;
import com.trade.project.service.BoardService;
import com.trade.project.service.NewsService;

import net.sf.json.JSONObject;

@Controller
public class IndexController {
	@Resource(name="newsService")
	NewsService newsService;
	
	@Resource(name="boardService")
	BoardService boardService;
	
	/**
	 * Get News List
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/index/getNewsList.do")
	public void getNewsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject resJsonObject = new JSONObject();
		
		JSONObject reqJsonObject = JSONObject.fromObject(request.getParameter("params"));
		
		newsService.getIndexNewsList(reqJsonObject, resJsonObject);
		
		response.setContentType("application/x-json;  charset=UTF-8");
		response.getWriter().print(resJsonObject);
	}
	
	/**
	 * 뉴스 상세보기
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/index/getNewsDetail.do")
	public void getNewsDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject resJsonObject = new JSONObject();
		
		Map<String, Object> value = GlobalUtil.convertMapArrayToObject(request.getParameterMap());
		
		newsService.getNewsDetail(value, resJsonObject);
		
		response.setContentType("application/x-json;  charset=UTF-8");
		response.getWriter().print(resJsonObject);
	}
	
	
	/**
	 * Get News List
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/index/getBoardList.do")
	public void getBoardList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject resJsonObject = new JSONObject();
		
		Map<String, Object> value = GlobalUtil.convertMapArrayToObject(request.getParameterMap());
		
		boardService.getIndexBoardList(value, resJsonObject);
		
		response.setContentType("application/x-json;  charset=UTF-8");
		response.getWriter().print(resJsonObject);
	}
}
