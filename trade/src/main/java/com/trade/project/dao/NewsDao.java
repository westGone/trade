package com.trade.project.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.trade.project.framework.CommonRowMapper;
import com.trade.project.framework.XMLDocParser;

import net.sf.json.JSONObject;

@Repository("newsDao")
public class NewsDao {
	protected static final Log logger = LogFactory.getLog(NewsDao.class);
	
	@Resource(name="namedParameterJdbcTemplate")
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Resource(name = "XMLDocParser")
	XMLDocParser reader;
	
	/**
	 * Get Crawling Info
	 * @return
	 * @throws java.lang.Exception
	 */
	public List<Map<String, Object>> getNewsCrawlingInfo() throws java.lang.Exception {
		StringBuffer where = new StringBuffer();
		
		String query = reader.getSAXDocument("news", "news", "getNewsCrawlingInfo").trim();
		
		SqlParameterSource namedParameters = new MapSqlParameterSource();
		return jdbcTemplate.queryForList(query, namedParameters);
	}
	
	/**
	 * Crawling News Save
	 * @param value
	 * @param resJsonObject
	 * @throws java.lang.Exception
	 */
	public void saveCrawlingNews(Map<String, Object> value) throws java.lang.Exception {
		// XML에서 Query문 가져오기
		String query = reader.getSAXDocument("news", "news", "saveCrawlingNews").trim();
		// Query Parameter 생성
		SqlParameterSource namedParameters = new MapSqlParameterSource(value);
		jdbcTemplate.update(query, namedParameters);
	}
	
	/**
	 * Get News List
	 * @param map
	 * @return
	 * @throws java.lang.Exception
	 */
	public List<Map<String, Object>> getNewsList(Map<String, Object> reqJsonObject) throws java.lang.Exception {
		StringBuffer where = new StringBuffer();
		
		String query = reader.getSAXDocument("news", "news", "getNewsList").trim();
		
		SqlParameterSource namedParameters = new MapSqlParameterSource(reqJsonObject);
		List<Map<String, Object>> getNe = jdbcTemplate.queryForList(query, namedParameters);
		return jdbcTemplate.queryForList(query, namedParameters);
	}
	
	/**
	 * 뉴스 상세보기
	 * @param value
	 * @return
	 * @throws java.lang.Exception
	 */
	public Map<String, Object> getNewsDetail(Map<String, Object> value) throws java.lang.Exception {
		// XML에서 Query문 가져오기
		String query = "";
		CommonRowMapper mapper = new CommonRowMapper();
		query = reader.getSAXDocument("news", "news", "getNewsDetail").trim();
		
		try{
			return jdbcTemplate.queryForObject(query, new MapSqlParameterSource(value), mapper);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

}
