package com.trade.project.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trade.project.framework.EncryptionUtils;
import com.trade.project.framework.GlobalUtil;
import com.trade.project.framework.Prop;
import com.trade.project.service.CommonService;

import net.sf.json.JSONObject;

@Controller
public class CommonController {
	
	protected static final Log logger = LogFactory.getLog(CommonController.class);
	
	@Resource(name="commonService")
	CommonService commonService;
	
	/**
	 * DII CODE 저장
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/common/saveCodeDefine.do")
	public void saveCodeDefine(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> value = GlobalUtil.convertMapArrayToObject(request.getParameterMap());
		
		JSONObject jsonObject = new JSONObject();
		
		commonService.saveCodeDefine(value);
			
        response.setContentType("application/x-json;  charset=UTF-8");
        response.getWriter().print(jsonObject);
	}
	
	/**
	 * 사용자가 선택한 언어의 Message 호출
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/common/getMessageLang.do")
	public void getMessageLang(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, Object> value = GlobalUtil.convertMapArrayToObject(request.getParameterMap());
		
		JSONObject jsonObject = new JSONObject();
		
		commonService.getMessageLang(value, jsonObject);
		
		response.setContentType("application/x-json;  charset=UTF-8");
		response.getWriter().print(jsonObject);
	}
	
	/**
	 * Oracle 컬럼 암복호화 Key값 가져오기
	 * @return
	 * @throws Exception
	 */
	public static String getSaultKey() throws Exception {
		return EncryptionUtils.decryptAES(Prop.getMessage("encryptAES"), EncryptionUtils.getRawKey("g-onesecurity"));
	}
	
	/**
	 * Query문 Default 치환작업
	 * @param query
	 * @param srchMode
	 * @return
	 */
	public static String defaultSubstitutionQuery(String query, String srchMode) {
		return defaultSubstitutionQuery(query, srchMode, "", new StringBuffer(), new StringBuffer());
		
	}
	
	/**
	 * Query문 Default 치환작업
	 * @param query
	 * @param srchMode
	 * @param lang
	 * @return
	 */
	public static String defaultSubstitutionQuery(String query, String srchMode, String lang) {
		return defaultSubstitutionQuery(query, srchMode, lang, new StringBuffer(), new StringBuffer());
		
	}
	
	/**
	 * Query문 Default 치환작업
	 * @param query
	 * @param srchMode
	 * @param order
	 * @param where
	 * @return
	 */
	public static String defaultSubstitutionQuery(String query, String srchMode, StringBuffer order, StringBuffer where) {
		return defaultSubstitutionQuery(query, srchMode, "", order, where);
	}
	
	/**
	 * Query문 Default 치환작업
	 * @param query
	 * @param srchMode
	 * @param lang
	 * @param order
	 * @param where
	 * @return
	 */
	public static String defaultSubstitutionQuery(String query, String srchMode, String lang, StringBuffer order, StringBuffer where) {
				
		query = query.replaceAll("#lang#", lang);
		query = query.replaceAll("#where#", where.toString());
		query = query.replaceAll("#order#", order.toString());
		 
		if("server".equals(srchMode)) {
			query = query.replaceAll("#rownum#", "(:take * (:page-1)) + ROWNUM AS NUM ");
			query = query.replaceAll("#page#", "CEIL((ROW_NUMBER() OVER (" + order.toString() + ")) / :take ) AS page, ");
			query = query.replaceAll("#endWhere#", "WHERE page = :page ");
		} else {
			query = query.replaceAll("#rownum#", "ROWNUM AS NUM ");
			query = query.replaceAll("#page#", "");
			query = query.replaceAll("#endWhere#", "");
		}
		
		query = query.replaceAll("#grid_start#", "");
		query = query.replaceAll("#grid_end#", "");
		
		if(!srchMode.equalsIgnoreCase("server") && !srchMode.equalsIgnoreCase("client") && srchMode.length() > 0) {
			logger.info("=======================================");
			logger.info("1.EXCEL DOWNLOAD srchMode : " + srchMode + " : defaultSubstitutionQuery()");
			logger.info("2.QUERY :");
			logger.info(query);
			logger.info("=======================================");
		}

		return query;
	}
}
