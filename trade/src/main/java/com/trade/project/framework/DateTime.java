package com.trade.project.framework;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateTime {

    public static Timestamp str2timestamp(String str_date) {
        Timestamp timestamp = null;
        try {
            if(str_date == null || str_date.equals("")) return null;

            SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = smf.parse(str_date);
            timestamp = new java.sql.Timestamp(date.getTime());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    public static Timestamp str2timestamp(String str_date, String format) {
        Timestamp timestamp = null;
        try {
            if(str_date == null || str_date.equals("")) return null;

            SimpleDateFormat smf = new SimpleDateFormat(format);
            java.util.Date date = smf.parse(str_date);
            timestamp = new java.sql.Timestamp(date.getTime());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }
    
    public static Date str2dateDateOnly(String d) {
        if (d == null || d.equals("")) {
            return null;
        }
        try {
            SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
            return smf.parse(d, new ParsePosition(0));
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Date str2dateTimeOnly(String d) {
        if (d == null || d.equals("")) {
            return null;
        }
        try {
            SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return smf.parse(d, new ParsePosition(0));
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static Date str2date(String d, String format) {
    	if (d == null || d.equals("")) {
    		return null;
    	}
    	try {
    		SimpleDateFormat smf = new SimpleDateFormat(format);
    		return smf.parse(d, new ParsePosition(0));
    	}
    	catch (Exception e) {
    		return null;
    	}
    }

    public static String date(Date d) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        return smf.format(d);
    }

    public static String date2strDateOnly(Date d) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
        return smf.format(d);
    }

    public static String date2strTimeOnly(Date d) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return smf.format(d);
    }

    public static String getToday() {
        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
        return smf.format( (new GregorianCalendar()).getTime());
    }

    public static String getTodayTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd-HH:mm:ss:SSS", Locale.KOREA);
        return formatter.format(new Date());
    }

    public static String getCurrentTimeMiles() {
        SimpleDateFormat		SDF_YYYYMMDDHHMMSSM_DASH_DOT = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss a");
        Timestamp tsValue = new Timestamp(System.currentTimeMillis());
        return ConvertDate2String( tsValue, SDF_YYYYMMDDHHMMSSM_DASH_DOT);
    }

    public static String ConvertDate2String( Date dtDate, SimpleDateFormat sdfDateFormat) {
        if( dtDate == null ) return "";

        try {
            return sdfDateFormat.format(dtDate);
        }
        catch( Exception e ) { e.printStackTrace(); return "";}
    }

    public static String getDateForm(String src, String pattern1, String pattern2){
        String returnVal = null;
        try{
            SimpleDateFormat form1 = new SimpleDateFormat(pattern1); 
            SimpleDateFormat form2 = new SimpleDateFormat(pattern2); 
            ParsePosition pos = new ParsePosition(0);
            Date date = form1.parse(src, pos);

            returnVal = form2.format(date);
        }catch(Exception e){
        	// 2014.09.12 blue
        	// 무한 Recursion 현상이 발생하여 주석처리 함.
        	// Exception 근본적인 처리는 호출하는 부분에서 해결할 것.
        	/**
        	try{
        		returnVal = getDateForm(src, "yyyy-MM-dd", pattern2);
            }catch(Exception e2){
                e2.printStackTrace();
            }
            */
        	e.printStackTrace();
        }
        return returnVal;
    }

    public static String getAddOrBeforeMonth(String date, int month){
    	Calendar cal      		= Calendar.getInstance();
    	String [] dateArr 		= Converter.stringToArray(date, "-");
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	cal.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]) - 1, Integer.parseInt(dateArr[2]));
    	cal.add(Calendar.MONTH, month);

    	return format.format(cal.getTime());
    }


    public static String getAddOrBeforeDay(String date, int day){
    	return getAddOrBeforeDay(date, day, "yyyy-MM-dd");
    }
    
    public static String getAddOrBeforeDay(String date, int day, String dateFormat){
    	Calendar cal      		= Calendar.getInstance();
    	String [] dateArr 		= Converter.stringToArray(date, "-");
    	SimpleDateFormat format = new SimpleDateFormat(dateFormat);
    	cal.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]) - 1, Integer.parseInt(dateArr[2]));
    	cal.add(Calendar.DATE, day);

    	return format.format(cal.getTime());
    }

    public static String getAcctDivDateOnly(String date, String acctDiv){
        String   rtnCal         = "";
        Calendar cal      		= Calendar.getInstance();
        String [] dateArr 		= Converter.stringToArray(date, "-");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if( acctDiv.equals("1")) {
            if( (Integer.parseInt(dateArr[1]) -1) == 0) {
                cal.set(Integer.parseInt(dateArr[0]), 0, 1);
                rtnCal = format.format(cal.getTime());
            } else {
                rtnCal = getAddOrBeforeMonth( format.format(cal.getTime()), -1);
            }
        } else {
            if( (Integer.parseInt(dateArr[1]) -1) == 2) {
                cal.set(Integer.parseInt(dateArr[0]), 2, 1);
                rtnCal = format.format(cal.getTime());
            } else {
                rtnCal = getAddOrBeforeMonth( format.format(cal.getTime()), -1);
            }
        }

        return rtnCal;
    }


    public static String getLastDayOfMonth(String date)
    {
        int daysOfMonth[]	= {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        String [] dateArr 	= Converter.stringToArray(date, "-");
        int year	= Integer.parseInt(dateArr[0]);
        int month 	= Integer.parseInt(dateArr[1]) -1;

        if ( year % 4 == 0 )
            daysOfMonth[1] = 29;
        if ( year % 100 == 0 )
            daysOfMonth[1] = 28;
        if ( year % 400 == 0 )
            daysOfMonth[1] = 29;

        return Integer.toString( daysOfMonth[month]);
    }
    
    // 날짜를 받아서 해당 delemiter로 연도,월,일을 구분하여 리턴
    public static String getDateType(String date, String delimiter){
    	StringBuffer buf = new StringBuffer();
    	
    	date = Util.getOnlyNumberChar(date);
    	
    	if(date == null) date = "";
    	
    	if(date.length() > 4){
    		buf.append(date.substring(0, 4));
    		buf.append(delimiter);
    	}
    	
    	if(date.length() > 6){
    		buf.append(date.substring(4, 6));
    		buf.append(delimiter);
    		buf.append(date.substring(6));
    	}
    	
    	return buf.toString();
    }

    // 시간를 받아서 해당 delemiter로 시,분,초를 구분하여 리턴
    public static String getTimeType(String time, String delimiter){
    	StringBuffer buf = new StringBuffer();
    	
    	time = Util.getOnlyNumberChar(time);
    	
    	if(time == null) time = "";
    	
    	if(time.length() == 4){
    		buf.append(time.substring(0, 2));
    		buf.append(delimiter);
    		buf.append(time.substring(2));
    	}
    	
    	if(time.length() >= 6){
    		buf.append(time.substring(4, 6));
    		buf.append(delimiter);
    		buf.append(time.substring(6));
    	}
    	
    	return buf.toString();
    }

    /**
     * Method getDate. 현재날짜/시간을 정해진 포맷에 따라 리턴
     * @param strFormat 포맷
     * @return 포맷에 따른 날짜/시간 문자열
     */
    public static String getDate(String strFormat) {
        Date d = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(strFormat, Locale.KOREAN);

        return formatter.format(d);
    }
    
    /**
     * Method getDateGmt. Gmt 시간 받아와서 계산
     * @param strFormat 포맷 strGmtTime (Ex : +09:00)
     * @return 포맷에 따른 날짜/시간 문자열
     */
    public static String getDateGmt(String strFormat, String strGmtTime) {
    	String returnVal = "";
    	
		Date dateTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(strFormat, Locale.KOREAN);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT" + strGmtTime));
        
        returnVal = formatter.format(dateTime);
	
        return returnVal;
    }
    
    
    /**
     * Method getIsfSendEstDateTime. ISF 최초송신 문서 일시 표현 (10/28/2015 13:20 PM EST)
     * @param strFormat 포맷
     * @return 포맷에 따른 날짜/시간 문자열
     */
    public static String getIsfSendEstDateTime() {
    	String returnVal = "";
    	
		Date dateTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm a z", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("EST"));
        
        returnVal = formatter.format(dateTime);
	
        return returnVal;
    }
    
    /**
     * Method getIsfRecvEstDateTime. ISF 정정문서 / 수신문서 일시 표현 (10/28/2015 13:20 PM EST)
     * @param strFormat 포맷
     * @return 포맷에 따른 날짜/시간 문자열
     */
    public static String getIsfRecvEstDateTime(String dateTime) {
    	String returnVal = dateTime;
    	
		try {
			SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.ENGLISH);
			formatter1.setTimeZone(TimeZone.getTimeZone("EST"));
	        SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy HH:mm a z", Locale.ENGLISH);
	        formatter2.setTimeZone(TimeZone.getTimeZone("EST"));
	        
	        Date date = formatter1.parse(dateTime, new ParsePosition(0));
	        returnVal = formatter2.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		
        return returnVal;
    }
    
    
    public static void main(String[] args) {
    	System.out.println(getDateType("2010-12-11", "-"));
    }
    
}
