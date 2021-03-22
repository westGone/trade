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

@Repository("settingsDao")
public class SettingsDao {
	protected static final Log logger = LogFactory.getLog(SettingsDao.class);
	
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
	public List<Map<String, Object>> getCodeDefineList(Map<String, Object> reqJsonObject) throws java.lang.Exception {
		StringBuffer where = new StringBuffer();
		
		String query = reader.getSAXDocument("settings", "settings", "getCodeDefineList").trim();
		
		SqlParameterSource namedParameters = new MapSqlParameterSource(reqJsonObject);
		return jdbcTemplate.queryForList(query, namedParameters);
	}
}
