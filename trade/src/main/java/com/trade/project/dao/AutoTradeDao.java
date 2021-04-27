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

@Repository("autoTradeDao")
public class AutoTradeDao {
	protected static final Log logger = LogFactory.getLog(AutoTradeDao.class);
	
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

	/**
	 * 가장 최근에 등록한 Order의 Key값을 가지고 온다
	 * @param value
	 * @return
	 * @throws java.lang.Exception
	 */
	public Map<String, Object> getOrderKey(Map<String, Object> value) throws java.lang.Exception {
		// XML에서 Query문 가져오기
		String query = "";
		CommonRowMapper mapper = new CommonRowMapper();
		query = reader.getSAXDocument("autoTrade", "autoTrade", "getOrderKey").trim();
		
		try{
			return jdbcTemplate.queryForObject(query, new MapSqlParameterSource(value), mapper);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	/**
	 * 새로운 Order Data 생성
	 * @param orderInfo
	 * @throws java.lang.Exception
	 */
	public void setOrder(Map<String, Object> orderInfo) throws java.lang.Exception {
		// XML에서 Query문 가져오기
		String query = reader.getSAXDocument("autoTrade", "autoTrade", "setOrder").trim();
		// Query Parameter 생성
		SqlParameterSource namedParameters = new MapSqlParameterSource(orderInfo);
		jdbcTemplate.update(query, namedParameters);
	}

	/**
	 * 기존 Order Data 마감
	 * @param orderInfo
	 * @throws java.lang.Exception
	 */
	public void liquidationOrder(Map<String, Object> orderInfo) throws java.lang.Exception {
		// XML에서 Query문 가져오기
		String query = reader.getSAXDocument("autoTrade", "autoTrade", "setOrder").trim();
		// Query Parameter 생성
		SqlParameterSource namedParameters = new MapSqlParameterSource(orderInfo);
		jdbcTemplate.update(query, namedParameters);
	}
}
