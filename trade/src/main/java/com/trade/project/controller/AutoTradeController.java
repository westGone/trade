package com.trade.project.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trade.project.crawling.CrawlingNews;
import com.trade.project.framework.GlobalUtil;
import com.trade.project.framework.Util;
import com.trade.project.service.AutoTradeService;
import com.trade.project.service.NewsService;

import net.sf.json.JSONObject;

@Controller
public class AutoTradeController {
	
	@Resource(name="autoTradeService")
	AutoTradeService autoTradeService;
	
	private static PythonInterpreter interpreter;
	
	@RequestMapping(value="/autoTrade/autoTrade.do")
	public void autoTrade(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject resJsonObject = new JSONObject();
		
		JSONObject reqJsonObject = JSONObject.fromObject(request.getParameter("params"));
		
//		interpreter = new PythonInterpreter();
//		interpreter.execfile("C:/Users/jiwon/git/trade/trade/src/main/java/com/trade/project/python/DmiCheck.py");
//		interpreter.exec("print(cal_dmi())");
//		PyFunction pyFunction = (PyFunction) interpreter.get("cal_dmi", PyFunction.class);
//		PyObject pyObj = pyFunction.__call__(new pyInteger());
		
		autoTradeService.autoTradeSystem(resJsonObject, reqJsonObject);
		
		response.setContentType("application/x-json;  charset=UTF-8");
		response.getWriter().print(resJsonObject);
	}
	
	
	/**
	 * 현재 Coin들의 가격을 가지고온다.
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/autoTrade/getPriceData.do")
	public void getPriceData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject resJsonObject = new JSONObject();
		
		Map<String, Object> value = GlobalUtil.convertMapArrayToObject(request.getParameterMap());
		
		autoTradeService.getPriceData(value, resJsonObject);
		
		response.setContentType("application/x-json;  charset=UTF-8");
		response.getWriter().print(resJsonObject);
	}
	
	// 10분 주기로 3개의 코인(BTC,EHT,XRP)의 자동매매를 호출
	@Component
	public class Scheduler {
	   @Scheduled(fixedDelay = 600000)
	   public void cronJobSch() throws Exception {
			JSONObject resJsonObject = new JSONObject();
			JSONObject reqJsonObject = new JSONObject();
			autoTradeService.autoTradeSystem(resJsonObject, reqJsonObject);
	   }
	}
	
	// 1분 주기로 XRPUSD의 MA 자동매매를 호출
	@Component
	public class MaAutoTrade {
		@Scheduled(fixedDelay = 60000)
		public void cronJobSch() throws Exception {
			JSONObject resJsonObject = new JSONObject();
			JSONObject reqJsonObject = new JSONObject();
			autoTradeService.maAutoTradeSystem(resJsonObject, reqJsonObject);
		}
	}
	
}
