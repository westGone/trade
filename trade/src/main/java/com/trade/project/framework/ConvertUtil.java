package com.trade.project.framework;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
public class ConvertUtil {
	protected static final Log logger = LogFactory.getLog(ConvertUtil.class);
 
    /**
     * 한글 
     */
    private static final String LANGUAGE_KOR = "KOR";
    
    /**
     * 영문
     */
    private static final String LANGUAGE_ENG = "ENG";
    
    /**
     * 한자 
     */
    private static final String LANGUAGE_CHN = "CHN";
    
    /**
     * 한글 금액 접두어 
     */
    private static final String LANGUAGE_KOR_PRE = "금";
    
    /**
     * 영문 금액 접두어 
     */
    private static final String LANGUAGE_ENG_PRE = "THE SUM OF KOREAN WON";
    
    /**
     * 한자 금액 접두어 
     */
    private static final String LANGUAGE_CHN_PRE = "金";
    
    /**
      * 한글 금액 접미어 
     */
    private static final String LANGUAGE_KOR_POST = "원정";
    
    /**
     * 영문 금액 접두어 
     */
    private static final String LANGUAGE_ENG_POST = "ONLY.";
    
    /**
     * 한자 금액 접두어 
     */
    private static final String LANGUAGE_CHN_POST = "";
    /**
     * 
     * Obejct Type 을 int Type으로 Casting하여 Return한다.
     * 
     * @param value
     * @return int
     */
    public static int castint(Object value) throws Exception {
    	int out = 0;
    	if (value == null || "".equals(value)) {
    		out = 0;
    	} else if (value instanceof Number) {
    		out = ((Number) value).intValue();
    	} else {
    		out = Integer.parseInt(value.toString().trim());
    	}
    	
    	return out;     
    }
    
    /**
     * 
     * Obejct Type 을 Integer Type으로 Casting하여 Return한다.
     * 
     * @param value
     * @return java.lang.Integer
     */
    public static Integer castInteger(Object value) throws Exception {
    	Integer out = null;
    	if (value == null || "".equals(value)) {
    		out = new Integer(0);
    	} else if (value instanceof Integer) {
    		out = (Integer) value;
    	} else if (value instanceof Number) {
    		out = new Integer(((Number) value).intValue());
    	} else {
    		out = new Integer(value.toString().trim());
    	}
    	
    	return out;     
    }
    
    /**
     * 
     * Obejct Type 을 long Type으로 Casting하여 Return한다.
     * 
     * @param value
     * @return long
     */
    public static long castlong(Object value) throws Exception {
    	long out = 0;
    	if (value == null || "".equals(value)) {
    		out = 0;
    	} else if (value instanceof Number) {
    		out = ((Number) value).longValue();
    	} else {
    		out = Long.parseLong(value.toString().trim());
    	}
    	
    	return out;     
    }
    
    /**
     * 
     * Obejct Type 을 Long Type으로 Casting하여 Return한다.
     * 
     * @param value
     * @return java.lang.Long
     */
    public static Long castLong(Object value)  throws Exception {
    	Long out = null;
    	if (value == null || "".equals(value)) {
    		out = new Long(0);
    	} else if (value instanceof Long) {
    		out = (Long) value;
    	} else if (value instanceof Number) {
    		out = new Long(((Number) value).longValue());
    	} else {
    		out = new Long(value.toString().trim());
    	}
    	
    	return out;     
    }
    
    /**
     * 
     * Obejct Type 을 Long Type으로 Casting하여 Return한다.
     * 
     * @param value
     * @return float
     */
    public static float castfloat(Object value) throws Exception {
    	float out = 0;
    	if (value == null || "".equals(value)) {
    		out = 0;
    	} else if (value instanceof Number) {
    		out = ((Number) value).floatValue();
    	} else {
    		out = Float.parseFloat(value.toString().trim());
    	}
    	
    	return out;     
    }
    
    /**
     * 
     * Obejct Type 을 Float Type으로 Casting하여 Return한다.
     * 
     * @param value
     * @return java.lang.Float
     */
    public static Float castFloat(Object value)  throws Exception {
    	Float out = null;
    	if (value == null || "".equals(value)) {
    		out = new Float(0);
    	} else if (value instanceof Float) {
    		out = (Float) value;
    	} else if (value instanceof Number) {
    		out = new Float(((Number) value).floatValue());
    	} else {
    		out = new Float(value.toString().trim());
    	}
    	
    	return out;     
    }
    
    /**
     * 
     * Obejct Type 을 double Type으로 Casting하여 Return한다.
     * 
     * @param value
     * @return double
     */
    public static double castdouble(Object value) throws Exception {
    	double out = 0;
    	if (value == null || "".equals(value)) {
    		out = 0;
    	} else if (value instanceof Number) {
    		out = ((Number) value).doubleValue();
    	} else {
    		out = Double.parseDouble(value.toString().trim());
    	}
    	
    	return out;     
    }
    
    /**
     * 
     * Obejct Type 을 Double Type으로 Casting하여 Return한다.
     * 
     * @param value
     * @return java.lang.Double
     */
    public static Double castDouble(Object value)  throws Exception {
    	Double out = null;
    	if (value == null || "".equals(value)) {
    		out = new Double(0);
    	} else if (value instanceof Double) {
    		out = (Double) value;
    	} else if (value instanceof Number) {
    		out = new Double(((Number) value).doubleValue());
    	} else {
    		out = new Double(value.toString().trim());
    	}
    	
    	return out;     
    }
    
    /**
     * 
     * Obejct Type 을 BigDecimal Type으로 Casting하여 Return한다.
     * 
     * @param value
     * @return java.math.BigDecimal
     */
    public static BigDecimal castBigDecimal(Object value)  throws Exception {
    	BigDecimal out = null;
    	if (value == null || "".equals(value)) {
    		out = new BigDecimal(0);
    	} else if (value instanceof BigDecimal) {
    		out = (BigDecimal) value;
    	} else if (value instanceof Double) {
    		out = new BigDecimal(  new DecimalFormat("#0.0#################").format(((Double) value).doubleValue())  );
    	} else {
    		out = new BigDecimal(value.toString().trim());
    	}
    	
    	return out;     
    }
    
    /**
     * 
     * Obejct Type 을 String Type으로 Casting하여 Return한다.
     * 
     * @param value
     * @return java.lang.String
     */
    public static String castString(Object value) {
    	String out = null;
    	if (value == null || "".equals(value)) {
    		out = "";
    	} else if (value instanceof Double) {
    		out = new DecimalFormat("#0.0#################").format((Double) value);
    	} else {
    		out = value.toString();
    	}
    	
    	return out;     
    }
    
    /**
     * 
     *  Format(yyyy-MM-dd)된 입력 Obejct Type의 값을 Date Type으로 Casting하여 Return한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * ConvertUtil.castDate("2002-11-23 11:33:02.001") ===> 2002-11-23
     * ConvertUtil.castDate("2002-11-23")      ===> 2002-11-23
     *  
     * </pre>
     * 
     * @param value
     * @return java.sql.Date
     */
    public static java.sql.Date castDate(Object value) throws Exception {
    	java.sql.Date out = null;
    	if (value == null || "".equals(value)) {
    		out = new java.sql.Date(0);
    	} else if (value instanceof Number) {
    		out = new java.sql.Date(((Number) value).longValue());
    	} else if (value instanceof java.util.Date) {
    		out = new java.sql.Date(((java.util.Date) value).getTime());
    	} else {
    		String v = value.toString().trim();
    		//포멧 yyyy-mm-dd
    		if (v.length() > 10)
    			v = v.substring(0, 10);
    		out = java.sql.Date.valueOf(v);
    	}
    	
    	return out;     
    }
    
    /**
     * 
     *  Format(hh:mm:ss)된 입력 Obejct Type의 값을 Time Type으로 Casting하여 Return한다.
     * 
     * @param value
     * @return java.sql.Time
     */
    public static java.sql.Time castTime(Object value) throws Exception {
    	java.sql.Time out = null;
    	if (value == null || "".equals(value)) {
    		out = new java.sql.Time(0);
    	} else if (value instanceof Number) {
    		out = new java.sql.Time(((Number) value).longValue());
    	} else if (value instanceof java.util.Date) {
    		out = new java.sql.Time(((java.util.Date) value).getTime());
    	} else {
    		String v = value.toString().trim();
    		//포멧 hh:mm:ss
    		if (v.length() >= 19)
    			v = v.substring(11, 19);
    		else if (v.length() > 8)
    			v = v.substring(0, 8);
    		out = java.sql.Time.valueOf(v);
    	}
    	
    	return out;     
    }
    
    /**
     *    
     * Format(yyyy-MM-dd hh:mm:ss.fffffffff)된 입력 Obejct Type의 값을 Timestamp Type으로 Casting하여 Return한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * ConvertUtil.castTimestamp("2004-05-01 11:33:02.001") ===> 2004-05-01 11:33:02.001
  *
     * </pre>   
     * 
     * @param value
     * @return java.sql.Timestamp
     */
    public static java.sql.Timestamp castTimestamp(Object value) throws Exception {
    	java.sql.Timestamp out = null;
    	if (value == null || "".equals(value)) {
    		out = new java.sql.Timestamp(0);
    	} else if (value instanceof Number) {
    		out = new java.sql.Timestamp(((Number) value).longValue());
    	} else if (value instanceof java.sql.Timestamp) {
    		out = (java.sql.Timestamp) value;
    	} else if (value instanceof java.util.Date) {
    		out = new java.sql.Timestamp(((java.util.Date) value).getTime());
    	} else {
    		String v = value.toString().trim();
    		if (v.length() == 10) {
    			v = v + " 09:00:00";
    		} 
    		//포멧 yyyy-MM-dd hh:mm:ss.fffffffff
    		out = java.sql.Timestamp.valueOf(v);
    	}
    	
    	return out;     
    }
  
    /**
     * 
     * 입력 Object Type의 값을 요청한 Type으로 Casting하여 Return한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * ConvertUtil.castType("java.lang.String", new Integer(123))  ===> 123
     * 
     * </pre>
     * 
     * @param type
     * @param value
     * @return java.lang.Object
     */
    public static Object castType(String type, Object value)  throws Exception {
    	if ( type == null || type.equals("java.lang.String") ) {
    		return castString(value);
    	} else if ( type.equals("int") ) {
    		return castInteger(value);
    	} else if ( type.equals("java.lang.Integer") ) {
    		return castInteger(value);
    	} else if ( type.equals("Integer") ) {
    		return castInteger(value);
    	} else if ( type.equals("float") ) {
    		return castFloat(value);
    	} else if ( type.equals("java.lang.Float") ) {
    		return castFloat(value);
    	} else if ( type.equals("Float") ) {
    		return castFloat(value);
    	} else if ( type.equals("double") ) {
    		return castDouble(value);
    	} else if ( type.equals("java.lang.Double") ) {
    		return castDouble(value);
    	} else if ( type.equals("Double") ) {
    		return castDouble(value);
    	} else if ( type.equals("long") ) {
    		return castLong(value);
    	} else if ( type.equals("java.lang.Long") ) {
    		return castLong(value);
    	} else if ( type.equals("Long") ) {
    		return castLong(value);
    	} else if ( type.equals("java.math.BigDecimal") ) {
    		return castBigDecimal(value);
    	} else if ( type.equals("BigDecimal") ) {
    		return castBigDecimal(value);
    	} else if ( type.equals("java.sql.Date") ) {
    		return castDate(value);
    	} else if ( type.equals("java.sql.Time") ) {
    		return castTime(value);
    	} else if ( type.equals("java.sql.Timestamp") ) {
    		return castTimestamp(value);
    	}
    	
    	return value;     
    }
 
    /**
     * 영문 인코딩(8859-1)을  한글 인코딩(ksc5601)으로 Convert하여 Return한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * String value = new String("금융사업부".getBytes(), "8859_1");
     * convertEng2Kor(value)
     * ===> 금융사업부
     * 
     * </pre>
     * 
     * @param value
     * @return
     */
    public static String convertEng2Kor(String value) {
    	if(value == null) return value;
    	try{
    		return new String(value.getBytes("8859_1"), "ksc5601");
    	}catch(Exception e){
    		value = null;
    	}
    	return value;
    }
    
     /**
     * 한글 인코딩(ksc5601)을 영문 인코딩(8859-1)으로 Convert하여 Return한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * String value = new String("Financial Component".getBytes(), "ksc5601");
     * convertKor2Eng(value)
     * ===> Financial Component
     * 
     * </pre>
     * 
     * @param value
     * @return
     */
    public static String convertKor2Eng(String value) {
    	if(value == null) return value;
    	try{
    		return new String(value.getBytes("ksc5601"), "8859_1");
    	}catch(Exception e){
    		value = null;
    	}
    	return value;
    } 
 
    /**
     * 
     * 입력한 값을 %(Percent)값으로 Convert하고  %(Percent)형식으로 return한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * ConvertUtil.convertPercent("3.14")  ===> 314%
     * 
     * </pre>
     * 
     * @param value
     * @return
     */
    public static String convertPercent(String value)  {
    	double number = (new Double(value)).doubleValue();
    	return ConvertUtil.convertPercent(number);     
    }
    
     /**
     * 입력한 값을 %(Percent)값으로 Convert하고  %(Percent)형식으로 return한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * ConvertUtil.convertPercent(3.14)  ===> 314%
     * 
     * </pre>
     * 
     * @param value
     * @return
     */
    public static String convertPercent(double value) {
    	NumberFormat format = NumberFormat.getPercentInstance();
    	return format.format(value);     
    }
 
     /**
     * yyyyMMdd 또는 yyyyMMddhhmmss  Format 으로 입력된 String 값을 Timestamp Type으로 Convert하여 Return한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * ConvertUtil.convertString2Timestamp("20040501")    ===> 2004-05-01 14:37:32.531
     * ConvertUtil.convertString2Timestamp("20040501113302") ===> 2004-05-01 11:33:02.547
     *
     * </prev>
     * 
     * @param value
     * @return
     */
    public static java.sql.Timestamp convertString2Timestamp(String value) {
    	java.sql.Timestamp out = null;
    	if (value == null) {
    		out = new java.sql.Timestamp(0);
    	} else {
    		try {
    			String v = value.toString().trim();
    			if (v.length() == 8) {  // yyyyMMdd
    				String yearString = v.substring(0, 4);
    				String monthString = v.substring(4, 6);
    				String dayString = v.substring(6, 8);
    				int year = Integer.parseInt(yearString);
    				int month = Integer.parseInt(monthString) - 1;
    				int day = Integer.parseInt(dayString);
    				Calendar cal = new GregorianCalendar();
    				cal.set(year, month, day);
    				out = new Timestamp(cal.getTime().getTime());
          
    			} else if (v.length() == 14 || v.length() == 17) {  // yyyyMMddHHmmss
    				String yearString = v.substring(0, 4);
    				String monthString = v.substring(4, 6);
    				String dayString = v.substring(6, 8);
    				String hourString = v.substring(8, 10);
    				String minString = v.substring(10, 12);
    				String secString = v.substring(12, 14);
    				int year = Integer.parseInt(yearString);
    				int month = Integer.parseInt(monthString) - 1;
    				int day = Integer.parseInt(dayString);
    				int hour = Integer.parseInt(hourString);
    				int min = Integer.parseInt(minString);
    				int sec = Integer.parseInt(secString);
    				Calendar cal = new GregorianCalendar();
    				cal.set(year, month, day, hour, min, sec);
    				out = new Timestamp(cal.getTime().getTime());     
    			} 
    		} catch (Exception e) {
    			out = new java.sql.Timestamp(0);
    		}
    	}
    	
    	return out;     
    }
     
    /**
     * 
     * 숫자의 원화금액을 해당언어의 문자로 Convert하고 금액에 해당하는 접두어와 접미어를 붙여 Return한다. 
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * - 한자로 변환
     * convertWonMoney2Language("123456789","CHN","","") ===> 金 壹億貳阡參百四拾五萬六阡七百八拾九 
     * default prefix : 金
     * default postfix : 없음 
     * 
     * - 영문자로 변환
     * convertWonMoney2Language("12345","ENG","","")     ===> THE SUM OF KOREAN WON  TWELVE THOUSAND THREE HUNDRED FOURTY FIVE ONLY.
     * default prefix : THE SUM OF KOREAN WON
     * default postfix : ONLY.
     *  
     * - 한글로 변환
     * convertWonMoney2Language("1234567","KOR","","")  ===> 금 일백이십삼만사천오백육십칠 원정
     * default prefix : 금 
     * default postfix : 원정
     * 
     * </pre>
     * @param money
     * @param language
     * @param prefix
     * @param postfix
     * @return java.lang.String
     */
    public static String convertWonMoney2Language(String money, String language, String prefix, String postfix)  throws Exception {
  
    	String moneyChar =  null;
    	if (LANGUAGE_KOR.equals(language)) { 
    		if ( Util.isEmpty(prefix) )  prefix = LANGUAGE_KOR_PRE; 
    		if ( Util.isEmpty(postfix) )  postfix = LANGUAGE_KOR_POST;   
    		moneyChar= convertNumeral2Korean(money);
    
    	} else if (LANGUAGE_ENG.equals(language)) {
    		if ( Util.isEmpty(prefix) )  prefix = LANGUAGE_ENG_PRE;
    		if ( Util.isEmpty(postfix) )  postfix = LANGUAGE_ENG_POST; 
    		moneyChar= convertNumeral2English(money);
    
    	} else if (LANGUAGE_CHN.equals(language)) {
    		if ( Util.isEmpty(prefix) )  prefix = LANGUAGE_CHN_PRE;
    		if ( Util.isEmpty(postfix) )  postfix = LANGUAGE_CHN_POST;     
    		moneyChar= convertNumeral2Chinese(money);
    		
    	} else if (Util.isEmpty(language)) { 
    		if ( Util.isEmpty(prefix) )  prefix = LANGUAGE_KOR_PRE; 
    		if ( Util.isEmpty(postfix) )  postfix = LANGUAGE_KOR_POST;   
    		moneyChar= convertNumeral2Korean(money);      
    	}
    	StringBuffer formattedMoney = new StringBuffer(prefix);
    	formattedMoney.append(" ");
    	formattedMoney.append(moneyChar);
    	formattedMoney.append(" ");
    	formattedMoney.append(postfix); 
   
    	return formattedMoney.toString();     
    }
    
    /**
     *  
     * 숫자의 원화금액을 한글로 Convert 한다.  
     * 
     * <pre>
     * 
     * [사용 예제]
     *
     * convertNumeral2Korean("123456789")  ===> 일억이천삼백사십오만육천칠백팔십구
     * 
     * </pre>
     * 
     * @param money
     * @return java.lang.String
     */
    public static String convertNumeral2Korean(String money)  throws Exception {
    	if(!"".equals(Util.null2str(money)))	money = money.split("\\.")[0];
    	
    	String[] bigMonetaryUnit = { "만", "억", "조", "경" };
    	String[] monetaryUnit = { "천", "백", "십"  };
    	StringBuffer tempMoney = new StringBuffer();
    	String rtnMoney = null;
    	String [] moneyArray = {};
    	String tempString = null;
    	int moneyLength = money.length();
    	int unitPos = money.length();
    	int arrInx = (moneyLength%4 == 0) ?  moneyLength/4 : moneyLength/4+1;
    	moneyArray = new String [arrInx];
  
    	// 4자리 마다 끊어서 배열 생성
    	for (int inx = 0; unitPos>0; inx++) {
    		if (unitPos-4 >0) {
    			tempString = money.substring(unitPos-4, unitPos);
    			moneyArray[inx] = tempString;
    			unitPos = unitPos-4;         
    		} else {
    			tempString = money.substring(0, unitPos);
//    			moneyArray[inx] = StringMng.lpad(tempString, '0', 4);
    			unitPos = 0;
    		}
    	}
    	
    	//숫자를 한글로 변경 및 단위 추가
    	for (int jnx = arrInx-1; jnx>=0; jnx--) {
    		String tempMon = moneyArray[jnx];
    		int tempMonLen = tempMon.length();
    		char num = 0;
   
    		for (int knx=0;knx<tempMonLen;knx++){
    			num = tempMon.charAt(knx);
    			tempMoney.append(convertBaseMonetary2Korean(num));
    
    			if (num != '0') {
    				switch (knx) {
	    				case 0 : //천 단위
	    					tempMoney.append(monetaryUnit[knx]);
	    					break;
	    				case 1 : //백 단위
	    					tempMoney.append(monetaryUnit[knx]);
	    					break;
	    				case 2 : //십 단위
	    					tempMoney.append(monetaryUnit[knx]);
	    					break;
	    				default :
	    					break;
    				}
    			}
    		}
   
    		//만단위 이상 단위 붙이기
    		if (ConvertUtil.castint(tempMon)>0 && jnx>0) {
    			tempMoney.append(bigMonetaryUnit[jnx-1]);
    		}
    	}
    	rtnMoney = tempMoney.toString();
    	
    	return rtnMoney;     
    }
    
    /**
     * @param num
     * @return java.lang.String
     */
    private static String convertBaseMonetary2Korean(char num) {
    	String[] baseMonetaryUnit =
    		{ "", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구" };
    	String rtnBaseMonetary = null;
    	switch (num) {
	    	case '0' :
	    		rtnBaseMonetary = baseMonetaryUnit[0];
	    		break;
	    	case '1' :
	    		rtnBaseMonetary = baseMonetaryUnit[1];
	    		break;
	    	case '2' :
	    		rtnBaseMonetary = baseMonetaryUnit[2];
	    		break;
	    	case '3' :
	    		rtnBaseMonetary = baseMonetaryUnit[3];
	    		break;
	    	case '4' :
	    		rtnBaseMonetary = baseMonetaryUnit[4];
	    		break;
	    	case '5' :
	    		rtnBaseMonetary = baseMonetaryUnit[5];
	    		break;
	    	case '6' :
	    		rtnBaseMonetary = baseMonetaryUnit[6];
	    		break;
	    	case '7' :
	    		rtnBaseMonetary = baseMonetaryUnit[7];
	    		break;
	    	case '8' :
	    		rtnBaseMonetary = baseMonetaryUnit[8];
	    		break;
	    	case '9' :
	    		rtnBaseMonetary = baseMonetaryUnit[9];
	    		break;
	    	default :
	    		break;
    	}
    	
    	return rtnBaseMonetary;     
    }
    
    /**
     *  
     * 숫자의 원화금액을 한자로 Convert 한다.  
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * convertNumeral2Chinese("123456789") ===> 壹億貳阡參百四拾五萬六阡七百八拾九
     * 
     * </pre>
     * 
     * @param money
     * @return java.lang.String
     */
    public static String convertNumeral2Chinese(String money)  throws Exception {
    	if(!"".equals(Util.null2str(money)))	money = money.split(".")[0];
    	
    	String[] bigMonetaryUnit = { "萬", "億", "兆", "京" };
    	String[] monetaryUnit = { "阡", "百", "拾"  };
    	String rtnMoney = null;
    	StringBuffer tempMoney = new StringBuffer();
    	String [] moneyArray = {};
    	String tempString = null;
    	int moneyLength = money.length();
    	int unitPos = money.length();
    	int arrInx = (moneyLength%4 == 0) ?  moneyLength/4 : moneyLength/4+1;
    	moneyArray = new String [arrInx];
  
    	// 4자리 마다 끊어서 배열 생성
    	for (int inx = 0; unitPos>0; inx++) {
    		if (unitPos-4 >0) {
    			tempString = money.substring(unitPos-4, unitPos);
    			moneyArray[inx] = tempString;
    			unitPos = unitPos-4;         
    		} else {
    			tempString = money.substring(0, unitPos);
//    			moneyArray[inx] = StringMng.lpad(tempString, '0', 4);
    			unitPos = 0;
    		}
    	}
    	// 숫자를 한자로 변경 및 단위 추가
    	for (int jnx = arrInx-1; jnx>=0; jnx--) {
    		String tempMon = moneyArray[jnx];
    		int tempMonLen = tempMon.length();
    		char num = 0;
   
    		for (int knx=0;knx<tempMonLen;knx++) {
    			num = tempMon.charAt(knx);
    			tempMoney.append(convertBaseMonetary2Chinese(num));
    
    			if (num != '0') {
    				switch (knx) {
	    				case 0 : //천 단위
	    					tempMoney.append(monetaryUnit[knx]);
	    					break;
	    				case 1 : //백 단위
	    					tempMoney.append(monetaryUnit[knx]);
	    					break;
	    				case 2 : //십 단위
	    					tempMoney.append(monetaryUnit[knx]);
	    					break;
	    				default :
	    					break;
    				}
    			}
    		}
   
    		// 만단위 이상 단위 붙이기
    		if (ConvertUtil.castint(tempMon)>0 && jnx>0) {
    			tempMoney.append(bigMonetaryUnit[jnx-1]);
    		}
    	}
    	rtnMoney = tempMoney.toString();
    	
    	return rtnMoney;     
    }
    
    /**
     * @param num
     * @return java.lang.String
     */
    private static String convertBaseMonetary2Chinese(char num) {
    	String[] baseMonetaryUnit =
    		{ "", "壹", "貳", "參", "四", "五", "六", "七", "八", "九" };
    	String rtnBaseMonetary = null;
    	switch (num) {
	    	case '0' :
	    		rtnBaseMonetary = baseMonetaryUnit[0];
	    		break;
	    	case '1' :
	    		rtnBaseMonetary = baseMonetaryUnit[1];
	    		break;
	    	case '2' :
	    		rtnBaseMonetary = baseMonetaryUnit[2];
	    		break;
	    	case '3' :
	    		rtnBaseMonetary = baseMonetaryUnit[3];
	    		break;
	    	case '4' :
	    		rtnBaseMonetary = baseMonetaryUnit[4];
	    		break;
	    	case '5' :
	    		rtnBaseMonetary = baseMonetaryUnit[5];
	    		break;
	    	case '6' :
	    		rtnBaseMonetary = baseMonetaryUnit[6];
	    		break;
	    	case '7' :
	    		rtnBaseMonetary = baseMonetaryUnit[7];
	    		break;
	    	case '8' :
	    		rtnBaseMonetary = baseMonetaryUnit[8];
	    		break;
	    	case '9' :
	    		rtnBaseMonetary = baseMonetaryUnit[9];
	    		break;
	    	default :
	    		break;
    	}
    	
    	return rtnBaseMonetary;     
    }
    
    /**
     *  
     * 숫자의 원화금액을 영문자로 Convert 한다.  
     * 
     * <pre>
     *
     * convertNumeral2English("123456789")  
     * ===>  ONE HUNDRED TWENTY THREE MILLION FOUR HUNDRED FIFTY SIX THOUSAND SEVEN HUNDRED EIGHTY NINE
     * 
     * </pre>
     * 
     * @param money
     * @return java.lang.String
     */
    public static String convertNumeral2English(String money)  throws Exception {
    	String[] bigMonetaryUnit =
    		{
    			" HUNDRED",
    			" THOUSAND",
    			" MILLION",
    			" BILLION",
    			" TRILLION",
    			" QUADRILLION",
    		" QUINTILLION" };
    	String rtnMoney = null;
    	StringBuffer tempMoney = new StringBuffer();
    	String [] moneyArray = {};
    	String tempString = null;
    	int moneyLength = money.length();
    	int unitPos = money.length();
    	int arrInx = (moneyLength%3 == 0) ?  moneyLength/3 : moneyLength/3+1;
    	moneyArray = new String [arrInx];
  
    	// 3자리 마다 끊어서 배열 생성
    	for (int inx = 0; unitPos>0; inx++) {
    		if (unitPos-3 > 0) {
    			tempString = money.substring(unitPos-3, unitPos);
    			moneyArray[inx] = tempString;
    			unitPos = unitPos-3;         
    		} else {
    			tempString = money.substring(0, unitPos);
//    			moneyArray[inx] =  StringMng.lpad(tempString, '0', 3);
    			unitPos = 0;
    		}
    	}
    	
    	// 숫자를 영문으로 변경 및 단위 추가
    	for (int jnx = arrInx-1; jnx>=0; jnx--) {
    		String tempMon = moneyArray[jnx];
    		int tempMonLen = tempMon.length();
    		char num = 0;
   
    		for (int knx=0;knx<tempMonLen;knx++){
    			num = tempMon.charAt(knx);
    			if (knx == 0){ //HUNDRED 단위
    				tempMoney.append(convertBaseMonetary2English(num));
    				if (num != '0') tempMoney.append(bigMonetaryUnit[0]);
    			} else if (knx == 1){ //십단위
    				if (num == '1') { //1X인 경우 
    					tempMoney.append(convertTeenMonetary2English(tempMon.charAt(knx+1)));
    				} else if (num == '0'){ // 0인 경우
    					tempMoney.append(convertBaseMonetary2English(tempMon.charAt(knx+1)));
    				} else { //10보다 큰 경우
    					tempMoney.append(convertDecadeMonetary2English(num));
    					tempMoney.append(convertBaseMonetary2English(tempMon.charAt(knx+1)));
    				}
    			}
    		}
    		
    		// 천단위 이상 단위 붙이기
    		if (ConvertUtil.castint(tempMon)>0 && jnx>0) {
    			tempMoney.append(bigMonetaryUnit[jnx]);
    		}
    	}
    	rtnMoney = tempMoney.toString();
    	
    	return rtnMoney;     
    }
    
    /**
     * @param num
     * @return java.lang.String
     */
    private static String convertBaseMonetary2English(char num) {
    	String[] baseMonetaryUnit =
    		{
    			"",
    			" ONE",
    			" TWO",
    			" THREE",
    			" FOUR",
    			" FIVE",
    			" SIX",
    			" SEVEN",
    			" EIGHT",
    		" NINE" };
    	String rtnBaseMonetary = null;
    	
    	switch (num) {
	    	case '0' :
	    		rtnBaseMonetary = baseMonetaryUnit[0];
	    		break;
	    	case '1' :
	    		rtnBaseMonetary = baseMonetaryUnit[1];
	    		break;	
	    	case '2' :
	    		rtnBaseMonetary = baseMonetaryUnit[2];
	    		break;
	    	case '3' :
	    		rtnBaseMonetary = baseMonetaryUnit[3];
	    		break;
	    	case '4' :
	    		rtnBaseMonetary = baseMonetaryUnit[4];
	    		break;
	    	case '5' :
	    		rtnBaseMonetary = baseMonetaryUnit[5];
	    		break;
	    	case '6' :
	    		rtnBaseMonetary = baseMonetaryUnit[6];
	    		break;
	    	case '7' :
	    		rtnBaseMonetary = baseMonetaryUnit[7];
	    		break;
	    	case '8' :
	    		rtnBaseMonetary = baseMonetaryUnit[8];
	    		break;
	    	case '9' :
	    		rtnBaseMonetary = baseMonetaryUnit[9];
	    		break;
	    	default :
	    		break;
    	}
    	
    	return rtnBaseMonetary;     
    }
    
    /**
     * @param num
     * @return java.lang.String
     */
    private static String convertTeenMonetary2English(char num) {
    	String[] teenMonetaryUnit =
    		{
    			" TEN",
    			" ELEVEN",
    			" TWELVE",
    			" THIRTEEN",
    			" FOURTEEN",
    			" FIFTHTEEN",
    			" SIXTEEN",
    			" SEVENTEEN",
    			" EIGHTEEN",
    		" NINETEEN" };
    	String rtnTeenMonetary = null;
    	
    	switch (num) {
	    	case '0' :
	    		rtnTeenMonetary = teenMonetaryUnit[0];
	    		break;
	    	case '1' :
	    		rtnTeenMonetary = teenMonetaryUnit[1];
	    		break;
	    	case '2' :
	    		rtnTeenMonetary = teenMonetaryUnit[2];
	    		break;
	    	case '3' :
	    		rtnTeenMonetary = teenMonetaryUnit[3];
	    		break;
	    	case '4' :
	    		rtnTeenMonetary = teenMonetaryUnit[4];
	    		break;
	    	case '5' :
	    		rtnTeenMonetary = teenMonetaryUnit[5];
	    		break;
	    	case '6' :
	    		rtnTeenMonetary = teenMonetaryUnit[6];
	    		break;
	    	case '7' :
	    		rtnTeenMonetary = teenMonetaryUnit[7];
	    		break;
	    	case '8' :
	    		rtnTeenMonetary = teenMonetaryUnit[8];
	    		break;
	    	case '9' :
	    		rtnTeenMonetary = teenMonetaryUnit[9];
	    		break;
	    	default :
	    		break;
    	}
    	
    	return rtnTeenMonetary;     
    }
    
    /**
     * @param num
     * @return java.lang.String
     */
    private static String convertDecadeMonetary2English(char num) {
    	String[] decadeMonetaryUnit =
    		{
    			" TWENTY",
    			" THIRTY",
    			" FOURTY",
    			" FIFTY",
    			" SIXTY",
    			" SEVENTY",
    			" EIGHTY",
    		" NINETY" };
    	String rtnDecadeMonetary = null;
    	switch (num) {
	    	case '2' :
	    		rtnDecadeMonetary = decadeMonetaryUnit[0];
	    		break;
	    	case '3' :
	    		rtnDecadeMonetary = decadeMonetaryUnit[1];
	    		break;
	    	case '4' :
	    		rtnDecadeMonetary = decadeMonetaryUnit[2];
	    		break;
	    	case '5' :
	    		rtnDecadeMonetary = decadeMonetaryUnit[3];
	    		break;
	    	case '6' :
	    		rtnDecadeMonetary = decadeMonetaryUnit[4];
	    		break;
	    	case '7' :
	    		rtnDecadeMonetary = decadeMonetaryUnit[5];
	    		break;
	    	case '8' :
	    		rtnDecadeMonetary = decadeMonetaryUnit[6];
	    		break;
	    	case '9' :
	    		rtnDecadeMonetary = decadeMonetaryUnit[7];
	    		break;
	    	default :
	    		break;
    	}
    	
    	return rtnDecadeMonetary;     
    }
    
    /**
     * Format 되지 않은 입력한 값을 요청한 Format으로 Formatting한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * format(new Double("1234"), "##,###,###" ) ===> 1,234
     * format("123456", "###-###" )       ===> 123-456
     * 
     * </pre>
     * 
     * @param object
     * @param format
     * @return
     */
    public static String format(Object object, String format) {
    	if (object == null)
    		return "";
    	if (format == null)
    		return object.toString();
    	
    	// 데이타가 문자열인경우 ###-###형태를 사용합니다.
    	if (object instanceof String) {
    		String s = ( (String) object).trim();
    		int j = 0;
    		StringBuffer sb = new StringBuffer();
    		for (int i = 0; i < format.length(); i++) {
    			if (format.charAt(i) == '#') {
    				if (j >= s.length())
    					return sb.toString();
    				sb.append(s.charAt(j++));
    			} else {
    				sb.append(format.charAt(i));
    			}
    		}
    		return sb.toString();
    	}
    	
    	if (object instanceof Number || object instanceof BigDecimal) {
    		DecimalFormat df = new DecimalFormat(format);
    		return df.format(object);
    	}
    	
    	return object.toString();     
    }
    
    /**
     * 입력한 Date 값을 요청한 Format으로 Formatting한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * formatDate(new java.util.Date(),"yyyy년MM월dd일") ===> 2004년02월23일
     * 
     * </pre>
     * 
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(java.util.Date date, String format) {
    	SimpleDateFormat formatter = new SimpleDateFormat(format);
    	return formatter.format(date);    
    }
    
    /**
     * yyyyMMdd로 Format된  입력 String 값을 default Format (yyyy/MM/dd) 으로 Formatting한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * ConvertUtil.formatDate("20040225")
     * 
     * </pre>
     * 
     * @param value
     * @return
     */
    public static String formatDate(String value) {
    	if(Util.isEmpty(value)) return value;
    	return formatDate(value,"yyyy-MM-dd");
    }
    
    /**
     * yyyyMMdd로 Format 된 입력 String 값을 요청한 Format으로 Formatting한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * formatDate("20040225","yyyy년MM월dd일") ===> 2004년02월25일
     * formatDate("20040225","yyyy/MM/dd")   ===> 2004/02/25
     * 
     * </pre>
     * 
     * @param value
     * @param format
     * @return
     */
    public static String formatDate(String value, String format) {
    	if(Util.isEmpty(value)) return value;
    	return formatTimestamp(convertString2Timestamp(value), format, java.util.Locale.KOREA);  
    }

    /**
     * yyyyMMdd Format으로 입력된 날짜가 yyyyMMdd Format 인지 확인하고, 적합하면 java.util.Date Type을 Return한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * check("20040225") ===> Wed Feb 25 00:00:00 KST 2004
     * 
     * </pre>
     * 
     * @param date
     * @return
     * @throws Exception
     */
    public static java.util.Date formatValidDate(String date) throws Exception {  
    	return formatValidDate(date, "yyyyMMdd");
    }
 
     /**
     * 입력된 날짜가 입력한 Format 에 적합한지 확인하고, 적합하면 java.util.Date Type을 Return한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * check("2004-02-26","yyyy-MM-dd") ===> Thu Feb 26 00:00:00 KST 2004
     * 
     * </pre>
     * 
     * @param date
     * @param format
     * @return
     * @throws Exception
     */
    public static java.util.Date formatValidDate(String date, String format) throws Exception {
    	if (date == null || format == null) return null;
   
    	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
    	java.util.Date formattedDate = null;
    	formattedDate = formatter.parse(date);
    	return formattedDate;
    }
    
    /**
     * Timestamp 형식의 날짜를 입력한 Format 과 Locale 에 따라  Formatting 한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * formatTimestamp("2004-02-25 15:45:31.156","yyyy년MM월dd일",Locale) ===> 2004년02월25일
     * 
     * </pre>
     * 
     * @param timestamp
     * @param format
     * @param locale
     * @return
     */
    public static String formatTimestamp(Timestamp timestamp, String format, java.util.Locale locale) {
    	SimpleDateFormat formatter = new SimpleDateFormat(format, locale);
    	return formatter.format(timestamp);
    }
   
    /**
     * 입력한 값을 $ 로 Formatting한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * formatDollarSymbol(1400.02) ===> $1,400.02
     * formatDollarSymbol(1400)  ===> $1,400.00
     * 
     * </pre>
     * 
     * @param value
     * @return
     */
    public static String formatDollarSymbol(double value) {
    	NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
    	return format.format(value);     
    }

    /**
     * 입력한 값을 $ 로 Formatting팅한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * formatDollarSymbol("1400.02") ===> $1,400.02
     * formatDollarSymbol("1400")  ===> $1,400.00
     * 
     * </pre>
     * 
     * @param value
     * @return
     */
    public static String formatDollarSymbol(String value) {
    	double number = (new Double(value)).doubleValue();   
    	return formatDollarSymbol(number);     
    }
    
    /**
     * 입력한 Number를 요청한 Format으로 Formatting하고 전달된 Length만큼 Right로 정렬한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * formatNumber(new Long("123456"), "*#,###,###,###", 15) ===>        *123,456
     * 
     * </pre>
     * 
     * @param number
     * @param format
     * @param length
     * @return
     */
    public static String formatNumber(Number number, String format, int length) {
    	String retStr = "";
    	if (number == null || number.toString().equals("")) {
    	} else {
    		double dValue = number.doubleValue();
    		if (format == null || format.equals("")) {
    			retStr = number.toString();
    		} else {
    			DecimalFormat df = new DecimalFormat(format);
    			retStr = df.format(dValue);
    		}
    	}
    	
    	return retStr;
    }
    
    /**
     * 입력된 스트링["국가번호","지역번호","국+전화번호","내선번호"]을 전화번호 Format으로 Return한다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * formatPhoneNo("82","02","63636565","0001") ===>    82-02-6363-6565(0001)
     * 
     * </pre>
     * 
     * @param country
     * @param area
     * @param number
     * @param inline
     * @return
     */
    public static String formatPhoneNo(String country, String area, String number, String inline) {
    	StringBuffer sb = new StringBuffer();
    	boolean first = true;
    	if (country != null)
    		country = country.trim();
    	if (area != null)
    		area = area.trim();
    	if (number != null)
    		number = number.trim();
    	if (inline != null)
    		inline = inline.trim();

    	if ( (country != null) && !country.trim().equals("")) {
    		sb.append(country);
    		first = false;
    	}

    	if ( (area != null) && !area.trim().equals("")) {
    		if (first == false)
    			sb.append("-");
    		sb.append(area);
    		first = false;
    	}

    	if ( (number != null) && !number.trim().equals("")) {
    		if (first == false)
    			sb.append("-");
    		if (number.length() > 4) {
    			sb.append(number.substring(0, number.length() - 4));
    			sb.append("-");
    			sb.append(number.substring(number.length() - 4));
    		} else {
    			sb.append(number);
    		}
    		first = false;
    	}

    	if ( (inline != null) && !inline.trim().equals("")) {
    		sb.append("(" + inline + ")");
    	}
    	
    	return sb.toString();     
    }

    /**
     * 입력한 값을 원화로 Formatting 한다 ("\" 표시).
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * formatWonSymbol(105) ===> ￦105
     * 
     * </pre>
     * 
     * @param value
     * @return
     */
    public static String formatWonSymbol(double value) {
    	NumberFormat format = NumberFormat.getCurrencyInstance();
    	return format.format(value);     
    }
    
    /**
     * 입력한 값을 원화로 Formatting 한다 ("\" 표시).
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * formatWonSymbol("105") ===> ￦105
     * 
     * </pre>
     * 
     * @param value
     * @return
     */
    public static String formatWonSymbol(String value) {
    	double number = (new Double(value)).doubleValue();
    	return ConvertUtil.formatWonSymbol(number);     
    }
        
    /**
     * 입력한 문자열을 입력한 Length만큼 정렬한다.
     * Length의 기본 값은 0 이고, Length가 0 이면 data의 원래 크기가 적용된다.
     * 정렬의 기본 값은 Left 이다    
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * justify("*123,456", 15, "R") ===>  bbbbbbb*123,456
     * 
     * </pre>
     * 
     * @param data
     * @param length
     * @param justify
     * @return
     */
    public static String justify(String data, int length, String justify) {
    	byte[] byteData = data.getBytes();
    	int dataLen = byteData.length;
    	if (length == 0) {
    		length = dataLen;
    	}
    	int target_position = 0;
    	byte[] newByte = new byte[length];
    	for (int inx = 0; inx < newByte.length; inx++)
    		newByte[inx] = 32;
    	int differ = length - dataLen;
    	if (differ > 0) {
    		length = dataLen;
    		if (justify == null || justify.equals("")) {
    		} else if (justify.equals("R")) {
    			target_position = differ;
    		} else if (justify.equals("M")) {
    			target_position = differ / 2;
    		}
    	}
    	System.arraycopy(byteData, 0, newByte, target_position, length);
    	
    	return new String(newByte);     
    }
  
    /**
     * 입력한 값이 null 이면 null String 으로 Convert하여 Retrun 한다.
     * 
     * @param obj
     * @return
     */
    public static String NVL(Object obj) {
    	if(obj != null) return obj.toString();
    	else return "";
    }

    /**
     * 입력한 값이 null 이면 defaultVaue 로 Convert하여 Retrun 한다.
     * 
     * @param obj
     * @param defaultVaue
     * @return
     */
    public static String NVL(Object obj,String defaultVaue) {
    	if(obj != null) return obj.toString();
    	else return defaultVaue;
    }

    /**
     * 입력한 값이 null 이면 defaultVaue 로 Convert하여 Retrun 한다.
     * 
     * @param obj
     * @param defaultVaue
     * @return
     */
    public static int NVL(Object obj, int defaultVaue) {
    	if(obj != null) {
    		try{
    			return Integer.parseInt(obj.toString());
    		}catch(NumberFormatException e){
    			return defaultVaue;
    		}
    	} else 
    		return defaultVaue;
    }
 
    /**
     * 입력한 값이 null 이면 defaultVaue 로 Convert하여 Retrun 한다.
     * 
     * @param obj
     * @param defaultVaue
     * @return
     */
    public static double NVL(Object obj,double defaultVaue) {
    	if(obj != null) {
    		try{
    			return Double.parseDouble(obj.toString());
    		}catch(NumberFormatException e){
    			return defaultVaue;
    		}
    	} else 
    		return defaultVaue;
    }  
   
    /**
     * 입력된 금액을 소숫점 이하까지 출력하는 메소드
     * 
     * <pre>
     * 
     * [사용 예제] 
     * 
     * formatWonWithComma("1234567890", 2) ===> 12,345,678.90
     * formatWonWithComma("1234567890", 3) ===> 1,234,567.890
     * 
     * </pre>
     * 
     * @param value
     * @param length
     * @return
     */
    public static String formatWonWithComma(String value, int length) {
    	if(Util.isEmpty(value)) return value;
    	else if(value.length() < 2) return value;
    	else if(value.length() < length) return value;
  
    	if(length == 0) length = 2;  // Default : 소숫점 2째자리
    	StringBuffer sb = new StringBuffer();
  
    	sb.append(ConvertUtil.format(new Double(value.substring(0, value.length() - length)), "###,###,###,###,###,###" ));
    	sb.append(".");
    	sb.append(value.substring(value.length() - length));
  
    	return sb.toString();
    }
 
    /**
     * 변수명 형태를 DB column명으로 변환해준다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * converToDbColumn("bprTmpAmt) ===> BPR_TMP_AMT
     * 
     * </pre>
     * 
     * @param arg0
     * @return
     */
    public static String converToDbColumn(String arg0) {
    	StringBuffer sb = new StringBuffer();
    	for (int i = 0; i < arg0.length(); i++) {
    		char c = arg0.charAt(i);
    		if (Character.isUpperCase(c))
    			sb.append("_" + Character.toUpperCase(c));
    		else
    			sb.append(Character.toUpperCase(c));
    	}
    	
    	return sb.toString();
    }
        
    /**
     * DB colum형태를 변수명으로 변환해준다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * converToElementName("BPR_TMP_AMT") ===> bprTmpAmt
     * 
     * </pre>
     * 
     * @param arg0
     * @return
     */
    public static String converToElementName(String str) {
    	String tmpStr = "";
		String rtnStr = "";
		int leng =str.length();
		
		for (int i =0; i <leng; i++) {
			if (i==0) {
				tmpStr = str.substring(0,1).toLowerCase();			
			} else {
				if (str.charAt(i) == '_'){
					tmpStr+="_";
				} else {
					if (str.charAt(i-1 )== '_') {
						tmpStr+=str.substring(i,i+1).toUpperCase();
					} else {
						tmpStr+=str.substring(i,i+1).toLowerCase();
					}
				}  // if 종료
			}  // for 종료
		}
		for(String cha : tmpStr.split("_")){      //배열
			rtnStr += cha;
		}
		return rtnStr;
    }
    
    /**
     * DB colum형태를 변수명으로 변환해준다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * converToElementName("BPR_TMP_AMT") ===> bprTmpAmt
     * 
     * </pre>
     * 
     * @param arg0
     * @return
     */
    public static Map<String, Object> convertElementNames(Map<String, Object> map) {    	
    	return convertElementNames(map, null);
    }
    
    public static Map<String, Object> convertElementNames(Map<String, Object> map, HttpSession session) {
    	Map<String, Object> rtnMap = new HashMap<String, Object>();
    	
    	Iterator<String> iterator = map.keySet().iterator();
    	
    	while(iterator.hasNext()) {
    		String key = (String) iterator.next();
    		
    		if("".equals(Util.null2str(map.get(key))))
				rtnMap.put(ConvertUtil.converToElementName(key), null);
			else {
				if(key.contains("_DATE") && map.get(key) instanceof JSONObject) {
					if(session != null && Util.null2str(session.getAttribute("SESS_DATEFORMAT")) != "") {
						SimpleDateFormat dateFormat = new SimpleDateFormat(Util.null2str(session.getAttribute("SESS_DATEFORMAT")));
						rtnMap.put(ConvertUtil.converToElementName(key), DateTime.ConvertDate2String(new Date(((JSONObject)map.get(key)).getLong("time")), dateFormat));						
					} else {
						rtnMap.put(ConvertUtil.converToElementName(key), DateTime.date2strDateOnly(new Date(((JSONObject)map.get(key)).getLong("time"))));
					}
				}
				else rtnMap.put(ConvertUtil.converToElementName(key), map.get(key));
			}
    	}
    	return rtnMap;
    }
    
    /**
     * DB colum형태를 변수명으로 변환해준다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * converToElementName("BPR_TMP_AMT") ===> bprTmpAmt
     * 
     * </pre>
     * 
     * @param arg0
     * @return
     */
    public static List<Map<String, Object>> convertElementNames(List<Map<String, Object>> mapList) {
    	List<Map<String, Object>> rtnMapList = new ArrayList<>();
    	
    	for (Map<String, Object> map : mapList) {
    		rtnMapList.add(convertElementNames(map));
    	}
    	return rtnMapList;
    }
    
    /**
     * DB colum형태를 변수명으로 변환해준다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * converToElementName("BPR_TMP_AMT") ===> bprTmpAmt
     * 
     * </pre>
     * 
     * @param arg0
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object>[] converToElementName(Map<String, Object>[] mapArray) throws java.lang.Exception {
		Map<String, Object>[] rtnArray = new Map[mapArray.length];

		for(int i=0; i<mapArray.length; i++) {
			Map<String, Object> tmpMap = mapArray[i];
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			
			for(Map.Entry<String, Object> elem : tmpMap.entrySet()){
				if("".equals(Util.null2str(elem.getValue())))
					rtnMap.put(ConvertUtil.converToElementName(elem.getKey()), null);
				else {
					if(elem.getKey().contains("_DATE") && elem.getValue().toString().length() >= 10) {
						rtnMap.put(ConvertUtil.converToElementName(elem.getKey()), elem.getValue().toString().substring(0, 10)); 
					}
					else rtnMap.put(ConvertUtil.converToElementName(elem.getKey()), elem.getValue());
				}
	        }
			rtnArray[i] = rtnMap;
		}			
		return rtnArray;
	}
    
    /**
     * DB colum형태를 변수명으로 변환해준다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * converToElementName("BPR_TMP_AMT") ===> bprTmpAmt
     * 
     * </pre>
     * 
     * @param arg0
     * @return
     * 
     */
    public static String converToElementNameForVO(String str) {
    	StringBuffer tmpStr = new StringBuffer("");
    	StringBuffer rtnStr = new StringBuffer("");
		int leng =str.length();
		
		for (int i =0; i <leng; i++) {
			if (i==0) {
				tmpStr.append(str.substring(0,1).toUpperCase());			
			} else {
				if (str.charAt(i) == '_'){
					tmpStr.append("_");
				} else {
					if (str.charAt(i-1 )== '_') {
						tmpStr.append(str.substring(i,i+1).toUpperCase());
					} else {
						tmpStr.append(str.substring(i,i+1).toLowerCase());
					}
				}  // if 종료
			}  // for 종료
		}
		for(String cha : tmpStr.toString().split("_")){      //배열
			rtnStr.append(cha);
		}
		return rtnStr.toString();
    }
    
    @SuppressWarnings("unchecked")
	public static Map<String, Object>[] convertJsonArrayToMapArray(JSONArray jsonArray) throws java.lang.Exception {
    	//JSON Array는 null값이어도 length가 1
		Map<String, Object>[] rtnArray = new Map[jsonArray.size()];
		
		for(int i = 0;i < jsonArray.size(); i++) {
			rtnArray[i] = (Map<String, Object>)jsonArray.getJSONObject(i);
		}
		
		ConvertUtil.converToElementName(rtnArray);
		
		return rtnArray;    			
	}
    
    /**
     * Json Array 형태를 Map Array 형태로 변환해준다.
     * 
     * <pre>
     * 
     * </pre>
     * 
     * @param arg0
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object>[] convertJsonArrayToMapArray(JSONArray jsonArray, boolean convertName) throws java.lang.Exception {
    	//JSON Array는 null값이어도 length가 1
		Map<String, Object>[] rtnArray = new Map[jsonArray.size()];
		
		for(int i = 0;i < jsonArray.size(); i++) {
			rtnArray[i] = (Map<String, Object>)jsonArray.getJSONObject(i);
		}
		
		if(convertName)	rtnArray = ConvertUtil.converToElementName(rtnArray);
		
		return rtnArray;    			
	}
    
    /**
     * Object 형태를 Map Array 형태로 변환해준다.(요소들의 이름을 변형한다. ex : USER_ID-->userId)
     * 
     * <pre>
     * 
     * </pre>
     * 
     * @param arg0
     * @return
     */
	public static Map<String, Object>[] convertObjectToMapArray(Object obj) throws java.lang.Exception {
		
//    	if(obj != null) return convertJsonArrayToMapArray(JSONArray.fromObject(obj), true);    		
//    	else return null;
		
		// JSONArray.fromObject(obj) obj가 문자열(String)인 경우 부분이 큰 숫자에 소수점이 있는 경우 소수점 처리가 차이가 생겨
		// 문자열을 JSONArray로 만들어서  JSONArray.fromObject(obj) 처리 되도록 처리 2017.01.16 LKH
		if(obj != null){
			Object returnObj = null;
			
			// 파라미터가 문자열이면
			if(obj instanceof String){
				try{
					org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
					org.json.simple.JSONArray ob = (org.json.simple.JSONArray)parser.parse(String.valueOf(obj));
					JSONArray obj2               = new JSONArray();
					Set<String> keys             = null;
					JSONObject newObj            = null;
					
					// 배열 개수만큼 처리
					for(int i = 0 ; ob != null && i<ob.size() ; i++){
						newObj = new JSONObject();
						org.json.simple.JSONObject pObj = (org.json.simple.JSONObject)ob.get(i);
						
						keys = pObj.keySet();
						
						// 내용 복사
						for(String key : keys){
						    if(pObj.get(key) == null){
//						        newObj.put(key, "");
						        newObj.put(key, JSONNull.getInstance());
						    }else{
						        newObj.put(key, pObj.get(key));
						    }
						}
						
						obj2.add(newObj);
					}
					
					// 배열이 건수가 0보다 크면
					if(obj2.size() > 0){
						returnObj = obj2;
					}
				}
				// 파싱 오류가 발생하면 기존 로직을 탄다
				catch(Exception e){
					logger.debug("!!!!! parsing 오류 !!!!!" + obj.toString());
				}
			}
			
			if(returnObj == null){
				returnObj = obj;
			}
			
			return convertJsonArrayToMapArray(JSONArray.fromObject(returnObj), true);
			
		} else return null;
	}
	
	/**
	 * Object 형태를 Map Array 형태로 변환해준다.(요소들의 이름을 변형하지 않는다.)
	 * @param obj
	 * @return
	 * @throws java.lang.Exception
	 */
	public static Map<String, Object>[] convertObjectToMapArrayNoConvertName(Object obj) throws java.lang.Exception {
    	if(obj != null) return convertJsonArrayToMapArray(JSONArray.fromObject(obj), false);    		
    	else return null;
	}
	
	/**
	 * List<Map<String, Object>> 형태를 Map<String, Object>[] 형태로 변환해준다.(요소들의 이름을 변형하지 않는다.)
	 * @param List<Map<String, Object>>
	 * @return
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object>[] convertListToMapArrayNoConvertName(List<Map<String, Object>> mapList) throws java.lang.Exception {		
		if(mapList != null && mapList.size() > 0) return mapList.toArray(new HashMap[mapList.size()]);    		
    	else return null;
	}
    
	/**
     * Json Object 형태를 Map 형태로 변환해준다.
     * 
     * <pre>
     * 
     * </pre>
     * 
     * @param arg0
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object> convertJsonObjectToMap(JSONObject jsonObject) throws java.lang.Exception {
    	if(jsonObject == null)	return null;
    	
    	Map<String, Object> classMap = new HashMap<String, Object>();
    	Map<String, Object> rtnMap = null;
    	
    	rtnMap = (Map<String, Object>)JSONObject.toBean(jsonObject, java.util.HashMap.class, classMap);
    	
		return rtnMap;    			
	}
	
    /**
     * yyyyMMddHHmmss로 Format된  입력 String 값을 default Format (yyyy-MM-dd HH:mm:ss) 으로 Formatting한다.
     * 
     * <pre>
     * 
     * [사용 예제] 
     * 
     * ConvertUtil.formatDatetime("20040225012530")
     * 
     * </pre>
     * 
     * @param value
     * @return
     */
    public static String formatDatetime(String value) {
    	if(Util.isEmpty(value)) return value;
    	return formatDate(value,"yyyy-MM-dd HH:mm:ss");
    }
    
    private static String[] phoneCode = {"02","031","032","033","041","042","043","051","052","053","054","055","061","062","063","064","080","0502","0505","010","011","016","017","018","019","0130"};

    /**
     * 입력된 전화번호 스트링을 포맷하는 메소드
     * 구분자가 있는 데이터나 길이가 부적절한 경우는 마스크적용하지 않는다.
     * 
     * <pre>
     * 
     * [사용 예제]
     * 
     * formatPhoneNumber("0193239801") ===> 019-323-9801
     * formatPhoneNumber("0254369801") ===> 02-5436-9801
     * formatPhoneNumber("02-5436-9801")==> 02-5436-9801
     * 
     * </pre>
     * 
     * @param pNum
     * @return
     */
    public static String formatPhoneNumber(String pNum) { 
        //값이 없거나 구분자가 포함된 경우 그냥 반환
    	if( Util.isEmpty( pNum ) || pNum.indexOf("-") > -1)
    		return pNum;
  
    	String rNum = pNum;
  
    	if( pNum.length() < 9 || pNum.length() > 12 ){
    		rNum = pNum;
    	} else if( pNum.length() == 12 ){
    		rNum = pNum.substring(0, 4)+"-"+pNum.substring(4, 8)+"-"+pNum.substring(8, 12);
    	} else {
    		int totLength = pNum.length();
    		for( int idx = 0 ; idx <phoneCode.length ; idx++ ){
    			if( pNum.startsWith(phoneCode[idx])){
    				rNum = pNum.substring(0, phoneCode[idx].length())+"-"+
    						pNum.substring(phoneCode[idx].length(), totLength-4 )+"-"+
    						pNum.substring(totLength-4, totLength);
    				break;
    			}
    		}
    	}
    	
    	return rNum;
    }
    
    /**
	 * 외화에 대한 환율적용
	 * 
	 * @param curAmt			- 공급가액외화
	 * @param exchangeRate		- 환율
	 * @param currencyDecimal	- 소수점 자리수
	 * @param roundingMode		- 반올림 구분값( 1: 반올림, 2: 절상, 3: 절하 )
	 */
    public static String convertAmountCurToAmountLoc(String curAmt, String exchangeRate, int currencyDecimal, String roundingMode) {
		BigDecimal zero = new BigDecimal("0");
		BigDecimal amt = new BigDecimal("0");
		BigDecimal rate = new BigDecimal("0");
		BigDecimal result = new BigDecimal("0");
		
    	curAmt = String.valueOf(curAmt);
		exchangeRate = String.valueOf(exchangeRate);
    	
		// compareTo :::: -1은 작다, 0은 같다, 1은 크다
		if( new BigDecimal(curAmt).compareTo(zero) == 0 
				|| new BigDecimal(exchangeRate).compareTo(zero) == 0 ) {
			return "0";
		}
    		
		amt = new BigDecimal(curAmt);
		rate = new BigDecimal(exchangeRate);
		
		result = amt.multiply(rate);
		
		if ( "1".equals(roundingMode) ) {
    		result = result.setScale(currencyDecimal, BigDecimal.ROUND_HALF_UP);
    	}
    	else if ( "2".equals(roundingMode) ) {
    		result = result.setScale(currencyDecimal, BigDecimal.ROUND_UP);
    	}
    	else if ( "3".equals(roundingMode) ) {
    		result = result.setScale(currencyDecimal, BigDecimal.ROUND_DOWN);
    	}
    	else {
    		result = result.setScale(currencyDecimal, BigDecimal.ROUND_DOWN);
    	}
		
		return result.toString();
    }
    
    /**
	 * 원화에 대한 환율적용
	 * 
	 * @param locAmt				- 공급가액원화
	 * @param exchangeRate			- 환율
	 * @param currencyDecimalCur	- 소수점 자리수
	 * @param roundingMode			- 반올림구분( 1: 반올림, 2: 절상, 3: 절하 )
	 */
    public static String convertAmountLocToAmountCur(String locAmt, String exchangeRate, int currencyDecimalCur, String roundingMode) {
    	BigDecimal zero = new BigDecimal("0");
		BigDecimal amt = new BigDecimal("0");
		BigDecimal rate = new BigDecimal("0");
		BigDecimal result = new BigDecimal("0");
		
		if ( "".equals(locAmt) ) locAmt = "0";
		if ( "".equals(exchangeRate) ) exchangeRate = "0";
    	
		// compareTo :::: -1은 작다, 0은 같다, 1은 크다
		if( new BigDecimal(locAmt).compareTo(zero) == 0 
				|| new BigDecimal(exchangeRate).compareTo(zero) == 0 ) {
			return "0";
		}
				
		amt = new BigDecimal(locAmt);
		rate = new BigDecimal(exchangeRate);
		
		if ( "1".equals(roundingMode) ) {
    		result = amt.divide(rate, currencyDecimalCur, BigDecimal.ROUND_HALF_UP);
    	}
    	else if ( "2".equals(roundingMode) ) {
    		result = amt.divide(rate, currencyDecimalCur, BigDecimal.ROUND_UP);
    	}
    	else if ( "3".equals(roundingMode) ) {
    		result = amt.divide(rate, currencyDecimalCur, BigDecimal.ROUND_DOWN);
    	}
    	else {
    		result = amt.divide(rate, currencyDecimalCur, BigDecimal.ROUND_DOWN);
    	}
		
		return result.toString();
    }
 
    private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }

    private static char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * Unicode 문자열을 Escape 문자열로 변환
     * @param str
     * @return
     */
    public static String toUnicodeEscapeString(String str) {
    	// 문자열에 Unicode가 포함되어 있을 경우 Escape 문자로 변환한다.
//    	if(GlobalUtil.containsUnicode(str)) {
//	        // Modeled after the code in java.util.Properties.save()
//	        StringBuffer buf = new StringBuffer();
//	        int len = str.length();
//	        char ch;
//	        for (int i = 0; i < len; i++) {
//	            ch = str.charAt(i);
//	            
//	            switch (ch) {
//	            case '\\':
//	                buf.append("\\\\");
//	                break;
//	            case '\t':
//	                buf.append("\\t");
//	                break;
//	            case '\n':
//	                buf.append("\\n");
//	                break;
//	            case '\r':
//	                buf.append("\\r");
//	                break;
//	            default:
//	                if (ch >= ' ' && ch <= 127) {
//	                    buf.append(ch);
//	                } else {
//	                    buf.append('\\');
//	                    buf.append(toHex((ch >> 12) & 0xF));
//	                    buf.append(toHex((ch >> 8) & 0xF));
//	                    buf.append(toHex((ch >> 4) & 0xF));
//	                    buf.append(toHex((ch >> 0) & 0xF));
//	                }
//	            }
//	        }
//
//	        return buf.toString();
//
//    	} else {
//    		return str;
//    	}
    	return str;
    }
    
    /**
     * Object형을 HasMap형으로 변환
     * @param obj
     * @return
     */
    public static Map<String, Object> ConverObjectToMap(Object obj) {
    	try {
    		Field[] fields = obj.getClass().getDeclaredFields();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            
            for(int i=0; i<=fields.length-1;i++){
                fields[i].setAccessible(true);
                if(fields[i].get(obj) == null)
                	resultMap.put(fields[i].getName(), "");
                else
                	resultMap.put(fields[i].getName(), fields[i].get(obj));
            }
            
            return resultMap;
            
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HashMap형을 Object형으로 변환
     * @param map
     * @param objClass
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static Object convertMapToObject(Map<String, Object> map, Object objClass) {
        String keyAttribute = null;
        String setMethodString = "set";
        String methodString = null;
        Iterator itr = map.keySet().iterator();
        while(itr.hasNext()){
            keyAttribute = (String) itr.next();
            methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1);
            try {
                Method[] methods = objClass.getClass().getDeclaredMethods();
                for(int i=0;i<=methods.length-1;i++){
                    if(methodString.equals(methods[i].getName())){
                        methods[i].invoke(objClass, map.get(keyAttribute));
                    }
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return objClass;
    }

    /**
     * 날짜컬럼인데 문자열로 조회하는 경우 사용(국가에 따라 날짜 형식이 다르므로 년월일이 아닌 일월년로 조회되는 경우 sorting에 문제가 발생)
     * @param sort
     * @param colums
     * @param dateFormat
     */
    public static void convertDateSort(JSONArray sort, String[] columns, String dateFormat){
    	// 정렬정보와 날짜컬럼 정보가 있으면 수행
		if(sort != null && sort.size() > 0 && columns != null && columns.length > 0){
			int sortSize   = sort.size();
			JSONObject obj = null;
			String colName = null;
			
			List<JSONObject> addObj = new ArrayList<JSONObject>();
			
			outterLoop :
			for(int i=sortSize ; i>0 ; i--){
				obj = sort.getJSONObject(i - 1);
				
				// 날짜컬럼 수 만큼
				for(int j=0 ; j<columns.length ; j++){
					colName = columns[j];
					
					// 선택한 날짜 컬럼인 경우
					if(colName.equals(Util.null2str(obj.get("field")))){
						JSONObject newObj = new JSONObject();
						
						// 문자열을 날짜 형식으로 변환해서 order by 할 수 있도록 변경
						newObj.put("field", "TO_DATE("+colName+", :"+ dateFormat +")");
						newObj.put("dir"  , obj.get("dir"));
						
						// 새로운 sorting 정보를 담는다.
						addObj.add(newObj);
						
						// 기존 정보 삭제
						sort.remove(i - 1);
						
						// 컬럼 수 만큼 리스트가 차면 loop를 벗어난다.
						if(columns.length == addObj.size()){
							break outterLoop;
						}
					}
				}
			}
			
			// 새로운 날짜관련 sorting 정보 세팅
			for(JSONObject o : addObj){
				sort.add(o);
			}
		}
    }
    
    /**
     * 날짜컬럼인데 문자열로 조회하는 경우 사용(국가에 따라 날짜 형식이 다르므로 년월일이 아닌 일월년로 조회되는 경우 sorting에 문제가 발생)
     * @param sort
     * @param colums
     * @param dateFormat
     */
    public static void convertSortReplace(JSONArray sort, String[] columns, String[] replaceColumns){
    	// 정렬정보와 날짜컬럼 정보가 있으면 수행
    	if(sort != null && sort.size() > 0 && 
    			columns != null && columns.length > 0 &&
    			columns.length == replaceColumns.length){
    		
    		int sortSize   = sort.size();
    		JSONObject obj = null;
    		String colName = null;
    		
    		List<JSONObject> addObj = new ArrayList<JSONObject>();
    		
    			for(int i=sortSize ; i>0 ; i--){
    				obj = sort.getJSONObject(i - 1);
    				
    				// 날짜컬럼 수 만큼
    				for(int j=0 ; j<columns.length ; j++){
    					colName = columns[j];
    					
    					// 선택한 날짜 컬럼인 경우
    					if(colName.equals(Util.null2str(obj.get("field")))){
    						JSONObject newObj = new JSONObject();
    						
    						// 문자열을 날짜 형식으로 변환해서 order by 할 수 있도록 변경
    						newObj.put("field", replaceColumns[j]);
    						newObj.put("dir"  , obj.get("dir"));
    						
    						// 새로운 sorting 정보를 담는다.
    						addObj.add(newObj);
    						
    						// 기존 정보 삭제
    						sort.remove(i - 1);
    					}
    				}
    			}
    		
    		// 새로운 날짜관련 sorting 정보 세팅
    		for(JSONObject o : addObj){
    			sort.add(o);
    		}
    	}
    }
    
	/**
	 * Object를 Map으로 변환
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map convertObjectToMap(Object obj) throws Exception{
		JSONObject jObject = JSONObject.fromObject(obj);
		Map map = new HashMap();

		for(Iterator keys = jObject.keys(); keys.hasNext();){
			String key = (String)keys.next();

			try{
				map.put(key, jObject.get(key));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

		return map;

	}
}