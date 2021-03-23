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

@Repository("boardDao")
public class BoardDao {
	protected static final Log logger = LogFactory.getLog(BoardDao.class);
	
	@Resource(name="namedParameterJdbcTemplate")
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Resource(name = "XMLDocParser")
	XMLDocParser reader;
	
	/**
	 * Get Was Sub Consol House List
	 * @param map
	 * @return
	 * @throws java.lang.Exception
	 */
	public List<Map<String, Object>> getBoardList(Map<String, Object> reqJsonObject) throws java.lang.Exception {
		StringBuffer where = new StringBuffer();
		
		String query = reader.getSAXDocument("board", "board", "getBoardList").trim();
		
		SqlParameterSource namedParameters = new MapSqlParameterSource(reqJsonObject);
		return jdbcTemplate.queryForList(query, namedParameters);
	}

	/**
	 * 게시물 저장
	 * @param value
	 * @throws java.lang.Exception
	 */
	public void saveCodeDefine(Map<String, Object> value) throws java.lang.Exception {
		// XML에서 Query문 가져오기
		String query = reader.getSAXDocument("board", "board", "saveCodeDefine").trim();
		// Query Parameter 생성
		SqlParameterSource namedParameters = new MapSqlParameterSource(value);
		jdbcTemplate.update(query, namedParameters);
	}

	public Map<String, Object> getContent(Map<String, Object> value) throws java.lang.Exception {
		// XML에서 Query문 가져오기
		String query = "";
		CommonRowMapper mapper = new CommonRowMapper();
		query = reader.getSAXDocument("board", "board", "getContent").trim();
		
		try{
			return jdbcTemplate.queryForObject(query, new MapSqlParameterSource(value), mapper);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
}
