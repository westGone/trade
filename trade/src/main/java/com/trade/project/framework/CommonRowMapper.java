package com.trade.project.framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;

public class CommonRowMapper implements RowMapper<Map<String, Object>> {
	protected static final Log logger = LogFactory.getLog(CommonRowMapper.class);
	
	@Override
	public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		Map<String, Object> mapping = new HashMap<String, Object>();
		ResultSetMetaData rsmd = rs.getMetaData();
		
		int cnt = rsmd.getColumnCount();
		
		for(int loop = 0; loop < cnt; loop++) {
			mapping.put(rsmd.getColumnName(loop+1).toUpperCase(), getResultValue(rsmd.getColumnType(loop+1), rsmd.getColumnName(loop+1), rs));
		}
		
		return mapping;
	}
	
	/**
	 * Column Type별 Column Value 가져오기
	 * @param columnType
	 * @param columnName
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public Object getResultValue(int columnType,String columnName, ResultSet rs) throws SQLException {
	    if(columnType == Types.NUMERIC) {
	    	return rs.getBigDecimal(columnName);
	    } else if(columnType == Types.BIGINT || columnType == Types.INTEGER || columnType == Types.BIT) {
	        return rs.getInt(columnName);
	        
	    } else if(columnType == Types.CHAR || columnType == Types.VARCHAR || columnType == Types.NVARCHAR) {
	    	
	        String result = rs.getString(columnName); 
	        if(result == null)
	            return "";
	        return result;
	        
	    } else if(columnType == Types.CLOB) {
	    	
	        StringBuilder sb = new StringBuilder();
	        Clob clob = rs.getClob(columnName);
	        if(clob==null) {
                logger.debug("[ Clob is Null ] ");
                logger.debug("  Null Column : "+columnName);
                
	        	return "";
	        }
	        Reader reader = clob.getCharacterStream();
	        char[] buff = new char[1024];
	        int nchar = 0;
	        try {
	            while((nchar = reader.read(buff))!= -1){
	                sb.append(buff,0,nchar);
	            }
	        } catch (IOException e) {
	            throw new RuntimeException(e.getMessage());
	        } finally{
	            if(reader!=null){
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    throw new RuntimeException(e.getMessage());
	                }
	            }//end if
	        }
	        return sb.toString();
	        
	    } else if(columnType == Types.BLOB) {
	    	
	        StringBuilder sb = new StringBuilder();
	    	Blob blob = rs.getBlob(columnName);
	    	if(blob==null) {
                logger.debug("[ Blob is Null ] ");
                logger.debug("  Null Column : "+columnName);
                
	        	return "";
	    	}
	        InputStream is = rs.getBlob(columnName).getBinaryStream();
	        byte[] buff = new byte[1024];
	        int nchar = 0;
	        try {
	            while((nchar = is.read(buff))!= -1){
	                sb.append(new String(buff,0,nchar));
	            }
	        } catch (IOException e) {
	            throw new RuntimeException(e.getMessage());
	        } finally{
	            if(is!=null)
	                try {
	                    is.close();
	                } catch (IOException e) {
	                    throw new RuntimeException(e.getMessage());
	                }
	        }
	        return sb.toString();
	        
	    } else if(columnType == Types.DATE || columnType == Types.TIMESTAMP) {
	        return rs.getTimestamp(columnName);
	    } else if(columnType == Types.TIME) {
	    	return rs.getTime(columnName);
	    }else{
	        logger.debug("[ Unsupported Column Type ] ");
	        logger.debug("  unsupported Column : "+columnName);
	        logger.debug("  unsupported Column Type : "+columnType);
	        
	        return "";
	    }
	}
}