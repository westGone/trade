package com.trade.project.controller;

import java.util.List;
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
import com.trade.project.framework.Util;
import com.trade.project.service.CommonService;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

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
	
	/**
	 * Query문 Default 치환작업 (B Type)
	 * @param query
	 * @param srchMode
	 * @param order
	 * @param where
	 * @return
	 */
	public static String bTypeSubstitutionQuery(String query) {
		return bTypeSubstitutionQuery(query, "", new StringBuffer(), new StringBuffer(), new StringBuffer());
	}
	
	/**
	 * Query문 Default 치환작업 (B Type)
	 * @param query
	 * @param srchMode
	 * @param order
	 * @param where
	 * @return
	 */
	public static String bTypeSubstitutionQuery(String query, String srchMode, StringBuffer order, StringBuffer where) {
		return bTypeSubstitutionQuery(query, srchMode, order, where, new StringBuffer());
	}
	
	
	/**
	 * Query문 Default 치환작업 with Having절 (B Type)
	 * @param query
	 * @param srchMode
	 * @param order
	 * @param where
	 * @return
	 */
	public static String bTypeSubstitutionQuery(String query, String srchMode, StringBuffer order, StringBuffer where, StringBuffer having) {
		return bTypeSubstitutionQuery(query, srchMode, order, where, having, new StringBuffer());
	}
	
	/**
	 * Query문 Default 치환작업 with Having절 (B Type)
	 * @param query
	 * @param srchMode
	 * @param order
	 * @param where
	 * @return
	 */
	public static String bTypeSubstitutionQuery(String query, String srchMode, StringBuffer order, StringBuffer where, StringBuffer having, StringBuffer filterWhere) {

		StringBuffer sql = new StringBuffer();
		boolean existWhere = false;
		
		if(order == null) order = new StringBuffer();
		
		if(where == null) where = new StringBuffer();
		if(having == null) having = new StringBuffer();
		if(filterWhere == null) filterWhere = new StringBuffer();
		
		if(where.indexOf("WHERE") < 0) where = new StringBuffer(" WHERE 1=1 " + where.toString());
		
		if(query.indexOf("#where#") > 0) {
			existWhere = true;
			query = query.replaceAll("#where#", where.toString());
		}
		
		if(query.indexOf("#grid_start#") > 0)	query = query.replaceAll("#grid_start#", "");
		if(query.indexOf("#grid_end#") > 0)		query = query.replaceAll("#grid_end#", "");
		if(query.indexOf("#having#") > 0)		query = query.replaceAll("#having#", having.toString());

		if("server".equals(srchMode)) {
			 // Paging
			 sql.append(" SELECT (:take * (:page-1)) + rownum AS NUM, A.* FROM");
			 sql.append(" (");			 
			 sql.append(" SELECT tbl1.*");
			 sql.append(" , CEIL((ROW_NUMBER() OVER (" + order.toString() + ")) / :take ) AS page");
			 sql.append(" , CEIL(COUNT(*) OVER()) AS total");
			 sql.append(" FROM (");
			 
			 sql.append(query);
			 
			 sql.append(" ) tbl1");
			 if(!existWhere) {
				 sql.append(where.toString());
				 sql.append(filterWhere.toString());
			 } else {
				 sql.append(" WHERE 1 = 1");
				 sql.append(filterWhere.toString());
			 }
			 sql.append(order.toString());
			 sql.append(") A");
			 sql.append(" WHERE page = :page");
		} 
		else if("bigExcel".equals(srchMode) || "bigExcelType".equals(srchMode)){
			sql.append(" SELECT A.* FROM");
			 sql.append(" (");			 
			 sql.append(" SELECT tbl1.*");
			 sql.append(" , CEIL(COUNT(*) OVER()) AS total");
			 sql.append(" , ROWNUM AS NUM ");
			 sql.append(" FROM (");
			 sql.append(query);
			 sql.append(order.toString());
			 sql.append(" ) tbl1");
			 sql.append(") A");
		}
		else{
			 // Non Paging
			 sql.append(" SELECT ROWNUM AS NUM");
			 sql.append(", CEIL(COUNT(*) OVER()) AS total");
			 sql.append(", A.* FROM");
			 sql.append(" (");			 
			 sql.append(" SELECT tbl1.*");
			 sql.append(" FROM (");
			 
			 sql.append(query);
			 
			 sql.append(" ) tbl1");
			 if(!existWhere) {
				 sql.append(where.toString());
				 sql.append(filterWhere.toString());
			 } else {
				 sql.append(" WHERE 1 = 1");
				 sql.append(filterWhere.toString());
			 }
			 sql.append(order.toString());
			 sql.append(" ) A");
		}
		
		if(!srchMode.equalsIgnoreCase("server") && !srchMode.equalsIgnoreCase("client") && srchMode.length() > 0) {
			logger.info("=======================================");
			logger.info("1.EXCEL DOWNLOAD srchMode : " + srchMode + " : bTypeSubstitutionQuery()");
			logger.info("2.QUERY :");
			logger.info(sql);
			logger.info("=======================================");
		}
		
		return sql.toString();
	}
	
	/**
	 * JSON 데이터 가져오기
	 * @param json
	 * @param key
	 * @return
	 */
	public static Object getJSON(String json, String key) {
        JSONObject jsonObj = JSONObject.fromObject(JSONSerializer.toJSON(json));
        
        if (!(jsonObj.get(key) instanceof JSONNull))	return jsonObj.get(key);
        else											return null;
	}
	/**
	 * 리스트 객체 생성
	 * @param list
	 * @param jsonObject
	 * @return 
	 */
	public static void setListData(List<Map<String, Object>> list, JSONObject jsonObject) {
        if(list != null && list.size() > 0)	{
        	
        	String noJson = Util.null2str(jsonObject.get("Excel_NO_JSON"));
        	
        	// Total Count 세팅
        	Map<String, Object> map = list.get(0);
        	jsonObject.put("total", map.get("total"));
        	if("Y".equals(noJson)){
        		jsonObject.put("data", list);
        	}else{
        		jsonObject.put("data", JSONArray.fromObject(list));
        	}
        } else {
        	jsonObject.put("total", 0);
        	jsonObject.put("data", new JSONArray());
        }
	}
	
	/**
	 * 다국어 메세지 리스트
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/common/common/message.do")
	public void getMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> value 	= GlobalUtil.convertMapArrayToObject(request.getParameterMap());
		String reqParams 		= request.getParameter("params");
		JSONArray params 		= (JSONArray) CommonController.getJSON(reqParams, "param");
		String userLanguage 	= (String) CommonController.getJSON(reqParams, "userLangType");
		
		JSONObject search 		= new JSONObject();
		JSONObject jsonObject 	= new JSONObject();
		
		String messageId 		= "";
		
		if(params != null) {
			for(int i = 0; i < params.size(); i++) {
				JSONObject param = (JSONObject) params.get(i);
				if(i == 0)	messageId = "'" + param.getString("id") + "'";
				else		messageId += ", '" + param.getString("id") + "'";
			}
			// 검색 조건 세팅
			search.put("srchLangType", userLanguage);
			search.put("srchMessageId", Util.null2str(messageId));
			commonService.getMessage(search, jsonObject);
		}
		
		response.setContentType("application/x-json;  charset=UTF-8");
		response.getWriter().print(jsonObject);
	}
}
