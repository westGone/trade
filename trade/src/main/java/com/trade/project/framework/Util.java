package com.trade.project.framework;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;


public class Util {
	
	/**
	 * null을 공백문자로 리턴
	 * @param obj
	 * @return
	 */
	public static String null2str(Object src){
		return null2str(src, "");
	}
	public static String null2str(Object obj, String dValue){
		return (obj == null || "null".equals(obj.toString().trim()) || "".equals(obj.toString().trim())) ? dValue : obj.toString().trim();
	}
	public static String null2str(String src){
		return null2str(src, "");
	}
	public static String null2str(String src, String dValue){
		return (src == null || "null".equals(src.trim())) ? dValue : src.trim();
	}
	public static String null2strNoTrim(Object src){
		return null2strNoTrim(src, "");
	}
	public static String null2strNoTrim(Object obj, String dValue){
		return (obj == null || "null".equals(obj.toString()) || "".equals(obj.toString())) ? dValue : obj.toString();
	}
	
	/**
	 * null을 0로 리턴
	 * @param src
	 * @return
	 */
	public static int null2int(String src){
		return (src == null || "null".equals(src) || "".equals(src)) ? 0 : Integer.parseInt(src.replaceAll(",", "").trim());
	}
	public static int null2int(Object src){
		return (src == null || "null".equals(src.toString().trim()) || "".equals(src.toString().trim())) ? 0 : Integer.parseInt(src.toString().replaceAll(",", "").trim());
	}
	public static long null2long(String src){
		return (src == null || "null".equals(src) || "".equals(src)) ? 0 : Long.parseLong(src.replaceAll(",", "").trim());
	}
	public static long null2long(Object src){
		return (src == null || "null".equals(src.toString().trim()) || "".equals(src.toString().trim())) ? 0 : Long.parseLong(src.toString().replaceAll(",", "").trim());
	}
	public static double null2dbl(Object src){
		double rtnNum = 0.0;
		
		if(src == null || "null".equals(src.toString().trim()) || "".equals(src.toString().trim())) {
			rtnNum = 0.0;
		} else {
			rtnNum = Double.parseDouble(src.toString().replaceAll(",", "").trim());
		}
		
		return rtnNum;
	}
	public static double null2dbl(Object src, int digit){
		return null2dbl(src, digit, "U");
	}
	
	/**
	 * 
	 * @param src
	 * @param digit
	 * @param type : 변환 타입. (U-반올림, C-무조건 반올림, D-내림)
	 * @return
	 */
	public static double null2dbl(Object src, int digit, String type) {
		double rtnNum = 0.0;
		
		if(src == null || "null".equals(src.toString().trim()) || "".equals(src.toString().trim())) {
			rtnNum = 0.0;
		} else {
			rtnNum = Double.parseDouble(src.toString().replaceAll(",", "").trim());
			/*
			if(digit != 0) {
				double tmpNum = 1.0;
				for(int i=0; i<digit; i++) tmpNum *= 10;
				
				rtnNum = Math.round(rtnNum * tmpNum) / tmpNum;
			}
			*/
			// 반올림 지정 자리수가 0일 경우도 반올림 처리가 가능하도록 변경
			// 2016.08.01 by jupitz
			
			switch(type) {
				case "U" :
					rtnNum = Double.parseDouble(new BigDecimal(rtnNum).setScale(digit, BigDecimal.ROUND_HALF_UP).toString());
					break;
				case "C" :
					rtnNum = Double.parseDouble(new BigDecimal(rtnNum).setScale(digit, BigDecimal.ROUND_CEILING).toString());
					break;
				case "D" :
					rtnNum = Double.parseDouble(new BigDecimal(rtnNum).setScale(digit, BigDecimal.ROUND_DOWN).toString());
					break;
				default :
					rtnNum = Double.parseDouble(new BigDecimal(rtnNum).setScale(digit, BigDecimal.ROUND_FLOOR).toString());
					break;
					
			}
		}
		
		return rtnNum;
	}	
	
	/**
	 * null이거나 빈값이면 true 그렇지 않으면 false를 리턴
	 * @param src
	 * @return
	 */
	public static boolean isEmpty(String src){
		return (src == null || "null".equals(src) || "".equals(src)) ? true : false;
	}

	/**
	 * 서블릿에서 한국어 처리
	 * @param src
	 * @return
	 */
	public static String null2strKorean(String src){
		String reeturnString = "";

		try{
			reeturnString = new String(src.getBytes("Cp1252"), "EUC-KR");
		}catch(Exception e){
			reeturnString = src;
		}
		return (src == null) ? "" : reeturnString; 
	}

	/**
	 * 첫 알파벳을 대문자로 리턴
	 * @param src
	 * @return
	 */
	public static String getFirstUpperCase(String src){
		String returnString = "";

		if(src == null || src.trim().equals("")){
			return "";
		}else{
			returnString  = src;
			byte [] bytes = src.getBytes();

			if(bytes[0] > 96 && bytes[0] < 128){
				returnString = String.valueOf(src.charAt(0)).toUpperCase().concat(src.substring(1));
			}
		}

		return returnString;
	}

	//Object를 넘겨받아 문자열로 변환 후 지정한 길이 이상일경우 자르는 함수
	public static String cutLength(Object src, int len) {
		return cutLength(src, len, "");
	}
	
	//Object를 넘겨받아 문자열로 변환 후 지정한 길이 이상일경우 자르는 함수
	public static String cutLength(Object src, int len, String dValue) {
		try {
			String str = null2str(src, dValue);
			
			if(str.length() > len) str = str.substring(0, len);
			
			return str;
		} catch (Exception e) {
			return src.toString();
		}
	}

	/**
	 * column 명칭 등을 받아 set, get 메소드 명칭을 만든다.
	 * @param src
	 * @param div
	 * @return
	 */
	public static String getSetGetMethodName(String src, String div){
		String returnString = "";

		if(src == null || src.trim().equals("")){
			return "";
		}else{
			StringBuffer buf  = new StringBuffer();
			String[] srcArray = src.split("_");

			for(int i=0 ; i<srcArray.length ; i++){
				buf.append(getFirstUpperCase(srcArray[i].toLowerCase()));
			}

			if("set".equals(div)){
				returnString = "set" + buf.toString();
			}else if("get".equals(div)){
				returnString = "get" + buf.toString();
			}
		}

		return returnString;
	}

	/**
	 * column 명칭 등을 받아 '_'를 없애고 다음 문자를 대문자로 만들어 리턴 
	 * @param src
	 * @return
	 */
	public static String getToVarName(String src){
		String returnString = "";

		if(src == null || src.trim().equals("")){
			return "";
		}else{
			StringBuffer buf  = new StringBuffer();
			String[] srcArray = src.split("_");

			for(int i=0 ; i<srcArray.length ; i++){
				if(i == 0){
					buf.append(srcArray[i].toLowerCase());
				}else{
					buf.append(getFirstUpperCase(srcArray[i].toLowerCase()));
				}
			}

			returnString = buf.toString();
		}

		return returnString;
	}

	/**
	 * 'null' 출력방지 및 문자열로 변환하여 반환
	 */
	public static String print(Object o)
	{
		if(o == null)
			return "";
		StringBuffer sb = new StringBuffer(o.toString());
		for(int i = 0; i < sb.length(); i++)
		{
			char ch = sb.charAt(i);
			if(ch == '"')
			{
				sb.replace(i, i + 1, "&quot;");
				i += 5;
			}
			if(ch == '\'')
			{
				sb.replace(i, i + 1, "&#39;");
				i += 4;
			}
		}

		return sb.toString();
	}


	public static String printDateOnly(String o) {
		if (o == null) {
			return "";
		}else{
			return print(DateTime.date2strDateOnly(DateTime.str2dateDateOnly(o)));
		}
	}

	/**
	 * 숫자(금액)를 #,##0 문자열로 변환하여 반환(단, 숫자는 Long Class type)
	 */
	public static String money(Long n) {
		if (n == null) {
			return "";
		}

		return money(n.longValue());
	}

	/**
	 * 금액표시 : 문자열(금액) 입력을 String type  #,##0 으로 반환  // CH02
	 */
	public static String money(String n) {
		if (n == null) {
			return null;
		}
		n = n.trim();

		long m = 0l;
		try {
			m = Long.parseLong(n);
		}
		catch (Exception e) {
			//e.printStackTrace();
			return "";
		}
		return money(m);
	}


	/**
	 * 매개변수로 들어온 8703041231313 주민등록번호를
	 * 870304-1231313 문자열로 Return한다.
	 */
	public static String ssnHyphen(String juminNo) {
		String jumin1, jumin2;

		if (juminNo.length() < 7) {
			return juminNo;
		}
		else {
			jumin1 = juminNo.substring(0, 6);
			jumin2 = juminNo.substring(6, juminNo.length());
		}
		return jumin1 + "-" + jumin2;
	}

	/**
	 * 매개변수로 들어온 8703041231313 주민등록번호를
	 * 870304-1****** 문자열로 Return한다.
	 */
	public static String ssnAster(String juminNo) {
		StringBuffer sb = new StringBuffer(juminNo);
		for (int i = 7; i < juminNo.length() && juminNo.length() > 8; i++) {
			sb.replace(i, i + 1, "*");

		}
		return ssnHyphen(sb.toString());
	}

	/**
	 * 매개변수로 들어온 문자열에서 '-'을 찾아 제거하고    //CH03
	 * '-'이 없는 연결된 문자열을 Return한다.
	 */
	public static String stripHyphen(String org) {

		for (; ; ) {
			int index = org.indexOf("-");

			if (index >= 0) {
				org = org.substring(0, index) + org.substring(index + 1);
			}
			else {
				break;
			}
		}
		return org;
	}

	@SuppressWarnings("unused")
	public static String convertAmtToHanja(String ps_amt){

		String sArr_hanja[] = {"영", "壹", "貳", "參", "四", "五", "六", "七", "八", "九"};
		String sResult_hanja = "";
		String sAmt_hanja  = "";
		String sOne_num    = "";
		String sBack_amount= "";
		Float fCur_amount  = Float.valueOf(ps_amt);
		int   iAmt_length  = ps_amt.length();
		int   i = 0;

		for (i = iAmt_length; i > 0; i--) {
			int j = 0;
			j = i-1;
			sBack_amount = sBack_amount+ps_amt.charAt(j);
		}

		for (i=1; i <= iAmt_length ; i++) {
			int j = 0;
			j = i -1;
			sOne_num = "";
			sOne_num = sOne_num + sBack_amount.charAt(j);

			if ( sOne_num.equals("0") ) {
				sAmt_hanja = "";
			} else {
				Integer iTemp_hanja = null;
				sAmt_hanja = "";
				iTemp_hanja = Integer.valueOf(sOne_num);
				String sOne_hanja  = sArr_hanja[iTemp_hanja.intValue()];

				sAmt_hanja = sAmt_hanja + sOne_hanja;

				if ( (i == 4) || (i == 8) || (i == 12) ) {
					sAmt_hanja = sAmt_hanja + "阡";
				} else if ( (i == 3) || (i == 7) || (i == 11) || (i == 15) ) {
					sAmt_hanja = sAmt_hanja + "百";
				} else if ( (i == 2) || (i == 6) || (i == 10) || (i == 14) ) {
					sAmt_hanja = sAmt_hanja + "拾";
				}
			}
			if (i == 13) {
				sAmt_hanja = sAmt_hanja + "兆";
			} else if (i == 9) {
				sAmt_hanja = sAmt_hanja + "億";
			} else if (i == 5) {
				sAmt_hanja = sAmt_hanja + "萬";
			}

			sResult_hanja = sAmt_hanja+sResult_hanja;
		}

		return sResult_hanja;
	}

	@SuppressWarnings("unused")
	public static String convertAmtToHangul(String ps_amt){

		String sArr_hanja[] = {"영", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"};
		String sResult_hanja = "";
		String sAmt_hanja  = "";
		String sOne_num    = "";
		String sBack_amount= "";
		Float fCur_amount  = Float.valueOf(ps_amt);
		int   iAmt_length  = ps_amt.length();
		int   i = 0;

		for (i = iAmt_length; i > 0; i--) {
			int j = 0;
			j = i-1;
			sBack_amount = sBack_amount+ps_amt.charAt(j);
		}

		for (i=1; i <= iAmt_length ; i++) {
			int j = 0;
			j = i -1;
			sOne_num = "";
			sOne_num = sOne_num + sBack_amount.charAt(j);

			if ( sOne_num.equals("0") ) {
				sAmt_hanja = "";
			} else {
				Integer iTemp_hanja = null;
				sAmt_hanja = "";
				iTemp_hanja = Integer.valueOf(sOne_num);
				String sOne_hanja  = sArr_hanja[iTemp_hanja.intValue()];

				sAmt_hanja = sAmt_hanja + sOne_hanja;

				if ( (i == 4) || (i == 8) || (i == 12) ) {
					sAmt_hanja = sAmt_hanja + "천";
				} else if ( (i == 3) || (i == 7) || (i == 11) || (i == 15) ) {
					sAmt_hanja = sAmt_hanja + "백";
				} else if ( (i == 2) || (i == 6) || (i == 10) || (i == 14) ) {
					sAmt_hanja = sAmt_hanja + "십";
				}
			}
			if (i == 13) {
				sAmt_hanja = sAmt_hanja + "조";
			} else if (i == 9) {
				sAmt_hanja = sAmt_hanja + "억";
			} else if (i == 5) {
				sAmt_hanja = sAmt_hanja + "만";
			}

			sResult_hanja = sAmt_hanja+sResult_hanja;
		}

		return sResult_hanja;
	}	

	public static String getOnlyNumberChar(String org){
		StringBuffer buf = new StringBuffer();

		if(org == null) org = "";

		for(int i=0 ; i<org.length() ; i++){

			char ch = org.charAt(i);
			if(ch >= '0' && ch <= '9'){
				buf.append(ch);
			}
		}

		return buf.toString();
	}

	// password return
	//    public static String getPassword(String password){
	//    	String hash = password == null ? "" : password;
	//    	
	//    	if("".equals(hash)) return "";
	//    	
	//    	try{
	//			MessageDigest md = MessageDigest.getInstance("SHA1");
	//			MessageDigest md = MessageDigest.getInstance("SHA-256");
	//			
	//			md.update(password.getBytes("UTF-8"));
	//			
	//			byte[] raw = md.digest();
	//			
	//			hash = (new BASE64Encoder()).encode(raw);
	//    	}catch(Exception e){
	//    		e.printStackTrace();
	//    	}
	//    	
	//    	return hash;
	//    }

	// 문자열을 이미지로
	@SuppressWarnings("unused")
	public static BufferedImage imgText(String text, String fontUrl){
		BufferedImage buffer = null;

		try{
			float size           = 20.0f;
			String fontName      = "";
			Color background     = Color.lightGray;
			Color color          = Color.black;

			Font font            = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(fontUrl));
			font                 = font.deriveFont(size);

			buffer               = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2        = buffer.createGraphics();

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			FontRenderContext fc = g2.getFontRenderContext();
			Rectangle2D bounds   = font.getStringBounds(text, fc);

			int width            = (int)bounds.getWidth();
			int height           = (int)bounds.getHeight();

			buffer               = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			g2                   = buffer.createGraphics();

			g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setFont          (font);
			g2.setColor         (background);
			g2.fillRect         (0, 0, width, height);
			g2.setColor         (color);
			g2.drawString       (text, 0, (int)-bounds.getY());

		}catch(Exception e){
			e.printStackTrace();
		}

		return buffer;
	}

	// 랜덤 숫자를 자리 수에 맞게 리턴
	public static int getRandomNum(int size){
		if(size == 0 || size > 9) return 0;

		int maxInt   = 10;
		int smallInt = 10;

		for(int i=0 ; i<size - 1 ; i++){
			maxInt = maxInt * 10;
		}
		for(int i=0 ; i<size - 2 ; i++){
			smallInt = smallInt * 10;
		}

		Random random = new Random();
		int num       = 0;

		do{
			num = random.nextInt(maxInt - 1);
		}while(size != 1 && num < smallInt);

		return num;
	}

	public static void main(String[] args){
		//Util.getFirstUpperCase("a가나다");
		//		System.out.println(Util.getToVarName("GOODS_NAME"));

		//		String msg = "c:/abc/cd/";
		//		String filenmae = "abc.java";
		//		
		//		System.out.println(msg.lastIndexOf('/'));
		//		System.out.println(msg.length());
		//		System.out.println(filenmae.indexOf("."));

		//getOnlyNumberChar("123-456-78-90가/!@#!@$$%#&#%^*^&(*%)^");
		try{
			//			System.out.println(getRandomNum(1));
			System.out.println(EncryptionUtils.encrypt_sha2("1"));
			System.out.println(null2dbl(34.627, 2, "C"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//현재시간
	public static String getNow(){
		return getNow(null);
	}

	public static String getNow(String format){
		String date = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));

		if(format == null || format == ""){
			format = "yyyy-MM-dd HH:mm:ss";
		}

		date = new SimpleDateFormat(format).format(cal.getTime());

		return date;
	}

	//String 날짜 Date타입 변환
	public static Date strToDate(String strDate, String format){
		SimpleDateFormat f = null;
		if(format == null || format == ""){
			f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}else{
			f = new SimpleDateFormat(format);
		}

		Date dateTime 	   = new Date();
		try {
			dateTime = f.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateTime;

	}

	//String 데이터타입 format변경
	public static String dateStrToStr(String strDate, String format){
		SimpleDateFormat f = null;
		Date dateTime = new Date();
		String rtnStr = "";
		
		if(format == null || format == ""){
			f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}else{
			f = new SimpleDateFormat(format);
		}
		 
		try {
			dateTime = f.parse(strDate);
			rtnStr = f.format(dateTime);
		} catch (ParseException e) {
			// 2014.09.12 blue
			try{
				rtnStr =  dateStrToStr2(strDate, "yyyy-MM-dd", format);
            }catch(Exception e2){
                e2.printStackTrace();
            }
		}
		return rtnStr;

	}
	
	//String 데이터타입 format변경
	public static String dateStrToStr(String strDate, String format, Locale locale){
		SimpleDateFormat f = null;
		Date dateTime = new Date();
		
		if(format == null || format == ""){
			f = new SimpleDateFormat("yyyy-MM-dd", new Locale("ko", "KR"));
		}else if(locale == null){
			f = new SimpleDateFormat(format);
		}else{
			f = new SimpleDateFormat(format, locale);
		}
		dateTime.parse(strDate);

		return f.format(strDate);

	}
	
	//String 데이터타입 format변경
	public static String dateStrToStr2(String strDate, String oldFormat, String newFormat){
		SimpleDateFormat oldF = null;
		SimpleDateFormat newF = null;
		new SimpleDateFormat("yyyy-MM-dd"); 
		Date dateTime = new Date();
		if(oldFormat == null || oldFormat == "")	oldF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		else										oldF = new SimpleDateFormat(oldFormat);
		
		if(newFormat == null || newFormat == "")	newF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		else										newF = new SimpleDateFormat(newFormat);
		 
		try {
			dateTime = oldF.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newF.format(dateTime);

	}
	
	//String 데이터타입 format변경
	public static String dateStrToStr2(String strDate, String oldFormat, String newFormat, Locale locale){
		SimpleDateFormat oldF = null;
		SimpleDateFormat newF = null;
		new SimpleDateFormat("yyyy-MM-dd"); 
		Date dateTime = new Date();
		if(oldFormat == null || oldFormat == "")	oldF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		else										oldF = new SimpleDateFormat(oldFormat);
		
		if(newFormat == null || newFormat == "")	newF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
		else										newF = new SimpleDateFormat(newFormat, locale);
		 
		try {
			dateTime = oldF.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newF.format(dateTime);
	}

	public static int getInt(String str){
		try{
			if(str == null){
				return 0;
			}else{
				return Integer.parseInt(str);
			}
		}catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * 숫자형인지 판단한다.
	 * 
	 * @param str
	 * @return
	 */
    public static boolean isNumber(String str) {
        if (str == null)
            return false;
        Pattern p = Pattern.compile("([\\p{Digit}]+)(([\\p{Digit}]+))?");
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    /**
	 * 날짜 타입 체크
	 */
	public static boolean checkDateFormat(String date, String dateFormat){
		boolean checkResult = false;
		
		if(date == null || "".equals(date)){
			checkResult = true;
		}else{
			try{
				date = date.replaceAll("[^0-9]+", "");
				
				SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
				sf.parse(date);
				
				checkResult = true;
			}catch(Exception e){
				checkResult = false;
			}
		}
		
		return checkResult;
	}
	
	/**
	 * 날짜 타입 체크
	 */
	public static void removeSpText(Map<String, Object> map, String keyName){
		if(map != null){
			String date = Util.null2str(map.get(keyName));
			
			if(!"".equals(date)){
				map.put(keyName, date.replaceAll("[^0-9]+", ""));
			}
		}
	}
	
    /**
     * In조건 Key List Setting
     * 
     * 1. 메소드명 : setKeyList
     * 2. 작성일 : 2017. 5. 25.
     * 3. 작성자 : bwSeo
     * 4. 설명 : 
     * 
     * @param objectList
     * @param where
     * @param dbColumn
     * @return
     * @throws java.lang.Exception
     */
    public static StringBuffer setKeyList(Object objectList, StringBuffer where, String dbColumn) throws java.lang.Exception {
        JSONObject jsonObjectList = JSONObject.fromObject(objectList);
        return setKeyList(jsonObjectList, where, dbColumn);
    }
 
    /**
     * In조건 Key List Setting
     * 
     * 1. 메소드명 : setKeyList
     * 2. 작성일 : 2017. 5. 25.
     * 3. 작성자 : bwSeo
     * 4. 설명 : 
     * 
     * @param jsonObjectList
     * @param where
     * @param dbColumn
     * @return
     * @throws java.lang.Exception
     */
    public static StringBuffer setKeyList(JSONObject jsonObjectList, StringBuffer where, String dbColumn) throws java.lang.Exception {
        Map<String, Object> mapList = ConvertUtil.convertJsonObjectToMap(jsonObjectList);
        Map<String, Object> tmpMapList = new HashMap<String, Object>();
        
        for( String key : mapList.keySet() ) {
            String stringArray[] = ((String)mapList.get(key)).split("&");
            for(int n = 0; n < stringArray.length; ++n) {
                tmpMapList.put(stringArray[n], stringArray[n]);
            }
        }
        
        if(tmpMapList != null && tmpMapList.size() > 0) {
            int index = 0;
            // 2015-04-07 IN 쿼리 1000건 이상 바인드 하기
            where.append(" AND (0, " + dbColumn + ") IN(");
            
            for( String key : tmpMapList.keySet() ) {
                if(index == 0) {
                    where.append(" (0, '"+tmpMapList.get(key) + "') ");
                } else {
                    where.append(" , (0, '"+tmpMapList.get(key) + "') ");
                }
                
                ++index;
            }
            
            where.append(" )    \n");
        } else {
             where.append(" AND " + dbColumn + " IN('') ");
        }
        
        return where;
    }
    
    public static String getByteString(String str, int bytes){
        return new String(str.getBytes(), 0, bytes);
    }
    
    /**
     * 특정 년도 첫 날 
     * @param year 
     * @return Date
     */
    public static String getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();        
        SimpleDateFormat f = new SimpleDateFormat("YYYYMMDD");
        String sDate = f.format(currYearFirst);        
        return sDate;
    }
    
    /**
     * 특정 년도 마지막 날 
     * @param year 
     * @return Date
     */
    public static String getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        String sDate ="";        
        SimpleDateFormat recvSimpleFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat tranSimpleFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
 
        try {
            Date data = recvSimpleFormat.parse(currYearLast.toString());
            sDate = tranSimpleFormat.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sDate;
    }
    
    /**
     * 기본적으로 "," 로 구분된 문자열 in 조건 문자열로 리턴
     * @param param
     * @return
     * @throws Exception
     */
    public static String getInString(String param){
    	return getInString(param, ",", true);
    }
    
    /**
     * 특정 문자 열을 in 조건 문자열로 리턴
     * @param param
     * @param delim
     * @param spChar
     * @return
     * @throws Exception
     */
    public static String getInString(String param, String delim, boolean spChar){
    	if(param == null){
    		return null;
    	}
    	
		StringBuffer buf = new StringBuffer();
		
		String[] arr1 = spChar ? param.split("["+delim+"]") : param.split(delim); 
		
		for(int i=0 ; i<arr1.length ; i++){
			if(arr1[i] == null || "".equals(arr1[i].trim()) ){
				continue;
			}
			
			if(i != 0){
				buf.append(", ");
			}
			buf.append("'").append(arr1[i].trim()).append("'");
		}
		
		return buf.toString();
    }
    
    /**
     * camel 표기법 >> Snake 로 변환
     * @param str
     * @return
     */
	public static String camelToSnake(String str) {
		String result = "";

		char c = str.charAt(0);
		result = result + Character.toLowerCase(c);

		for (int i = 1; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (Character.isUpperCase(ch)) {
				result = result + '_';
				result = result + Character.toLowerCase(ch);
			} else {
				result = result + ch;
			}
		}
		return result.toUpperCase();
	}
}
