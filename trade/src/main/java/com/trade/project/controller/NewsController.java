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
import com.trade.project.service.NewsService;

import net.sf.json.JSONObject;

@Controller
public class NewsController {
	@Resource(name="newsService")
	NewsService newsService;
//	@RequestMapping(value="/g-one/fms/BookingOperation/setBookingInfo.do")
//	public void setBookingInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		JSONObject jsonObject = new JSONObject();
//		HttpSession session = request.getSession(true);
//		String messageId = "";
//		
//		String userId = Util.null2str((String) ((Map<String, Object>) session.getAttribute("SESS_USER")).get("USER_ID"));
//		String userName = Util.null2str((String) ((Map<String, Object>) session.getAttribute("SESS_USER")).get("USER_NAME"));
//		String gmtTime = Util.null2str( ((Map<String, Object>) session.getAttribute("SESS_BUSINESS")).get("GMT_TIME2"));
//		
//		// Booking Data 세팅
//		Map<String, Object> booking = GlobalUtil.convertMapArrayToObject(request.getParameterMap());
//		
//		//messageId = bookingOperationService.saveComOrderInfo(booking, session);
//		
//		response.setCharacterEncoding("UTF-8");
//		response.getWriter().print(messageId);
//	}
	
	@RequestMapping(value="/news/crawlingNews.do")
	public void crawlingNews(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CrawlingNews parser = new CrawlingNews();
//		parser.run();
		newsService.getNewsCrawlingInfo();
		JSONObject jsonObject = new JSONObject();
		
		response.setContentType("application/x-json;  charset=UTF-8");
		response.getWriter().print(jsonObject);
	}
	
	@Component
	public class Scheduler {
	   @Scheduled(cron = "59 59 23 * * ?")
	   public void cronJobSch() throws Exception {
			CrawlingNews parser = new CrawlingNews();
			parser.run();
	   }
	}
}
