package com.trade.project.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.trade.project.framework.XMLDocParser;

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
}
