package com.trade.project.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.trade.project.framework.CommonRowMapper;
import com.trade.project.framework.Util;
import com.trade.project.framework.XMLDocParser;

@Repository("commonDao")
public class CommonDao {
	protected static final Log logger = LogFactory.getLog(CommonDao.class);

	@Resource(name="namedParameterJdbcTemplate")
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Resource(name = "XMLDocParser")
	XMLDocParser reader;
//	
//	/**
//	 * Bsa 리스트 가져오기
//	 * @param search
//	 * @return
//	 * @throws java.lang.Exception
//	 */
//	public List<Map<String, Object>> getBsaList(JSONObject reqJsonObject) throws java.lang.Exception {
//		StringBuffer where = new StringBuffer();
//		StringBuffer order = new StringBuffer();
//		String query = "";
//		
//		// WHERE 조건문 생성
//		where.append(" AND ent_code = :srchEntCode");
//		where.append(" AND ent_business_code = :srchEntBusinessCode");
//		
//		if(!"".equals(Util.null2str(reqJsonObject.get("srchEtdDate")))) {
//			where.append(" AND ((etd_date >= TO_DATE(:srchEtdDate, :srchDateFormat) AND eta_date < TO_DATE(:srchEtaDate, :srchDateFormat) + 1) OR \n");
//			where.append("     (TO_DATE(:srchEtdDate, :srchDateFormat) between etd_date and eta_date) or \n");
//			where.append("     (TO_DATE(:srchEtaDate, :srchDateFormat) between etd_date and eta_date)) \n");
//		}
//		
//		if(!"".equals(Util.null2str(reqJsonObject.get("srchAgentCode2"))))	where.append(" AND airline_code2 = :srchAgentCode2");	// 항공사 CODE : 988
//		if(!"".equals(Util.null2str(reqJsonObject.get("srchFlightNo"))))	where.append(" AND flight_no = :srchFlightNo");
//		if(!"".equals(Util.null2str(reqJsonObject.get("srchOrigCode"))))	where.append(" AND orig_code = :srchOrigCode");
//		if(!"".equals(Util.null2str(reqJsonObject.get("srchDestCode"))))	where.append(" AND dest_code = :srchDestCode");
//		if(!"".equals(Util.null2str(reqJsonObject.get("srchStatus"))))		where.append(" AND status = :srchStatus");
//		
//		String lang = Util.null2str(reqJsonObject.get("srchLangType"));
//		if("".equals(lang))	lang = "default";
//		
//		// ORDER문 생성
//		JSONArray sort = (JSONArray)reqJsonObject.get("sort");
//		Common.setCommonOrderStatement(sort, " ORDER BY  airline_code ASC , ETD_DATE ASC ", order, lang);
//				
//		// GRID FILTER 적용
//		JSONObject filter = (JSONObject)reqJsonObject.get("filter");
//		Common.setCommonFilterStatement(filter, where, lang);
//				
//		// Query String 생성
//		// XML에서 Query문 가져오기
//		// '폴더명', 'xml파일명', 'sql id'
//		query = reader.getSAXDocument("fms", "bsa", "getBsaList").trim();
//		query = Common.bTypeSubstitutionQuery(query, Util.null2str(reqJsonObject.get("srchMode")), order, where);
//		
//		// Query Parameter 생성
//		@SuppressWarnings("unchecked")
//		SqlParameterSource namedParameters = new MapSqlParameterSource(reqJsonObject);
//		
//		return jdbcTemplate.queryForList(query, namedParameters);
//	}
	
	
	/**
	 * 사용자가 선택한 언어의 Message 호출
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public Map<String, Object> getMessageLang(Map<String, Object> map) throws java.lang.Exception {
		String query = "";
		
		CommonRowMapper mapper = new CommonRowMapper();
		
		query = reader.getSAXDocument("common", "common", "getMessageLang").trim();
		
		try{
			return jdbcTemplate.queryForObject(query, new MapSqlParameterSource(map), mapper);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}	
	
	/**
	 * DII CODE 저장
	 * @param value
	 * @throws java.lang.Exception
	 */
	public void saveCodeDefine(Map<String, Object> value) throws java.lang.Exception {
		// XML에서 Query문 가져오기
		String query = reader.getSAXDocument("common", "common", "saveCodeDefine").trim();
		// Query Parameter 생성
		SqlParameterSource namedParameters = new MapSqlParameterSource(value);
		jdbcTemplate.update(query, namedParameters);
	}
	
	/**
	* 다국어 메세지 가져오기
	* @param search
	* @return
	* @throws java.lang.Exception
	*/
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getMessage(JSONObject search) throws java.lang.Exception {
		// XML에서 Query문 가져오기
		// '폴더명', 'xml파일명', 'sql id'
		String query = reader.getSAXDocument("common", "common", "messageList").trim();
		 
		// 메시지 아이디 추출
		String messageIds = Util.null2str(search.get("srchMessageId"));
		
		messageIds = messageIds.replaceAll("[']", ""); // 홑따옴표 제거
		 
		String[] messageArr = messageIds.split("[,]");
		
		StringBuffer buf = new StringBuffer();
		String argName   = "argName";
		
		for(int i=0 ; i<messageArr.length ; i++) {
			if(i != 0){
				buf.append(", ");
			}
			buf.append(":" + argName + (i));
			search.put(argName + (i), messageArr[i].trim());
		}

		// 조건에 따른 값 치환
		query = query.replaceAll("#lang#", Util.null2str(search.get("srchLangType"), "DEFAULT"));
		query = query.replaceAll("#messageIdList#", buf.toString());
		 
		// Query Parameter 생성
		SqlParameterSource namedParameters = new MapSqlParameterSource(search);
		 
		return jdbcTemplate.queryForList(query, namedParameters);
	}
}

















