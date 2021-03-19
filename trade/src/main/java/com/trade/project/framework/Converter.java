package com.trade.project.framework;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Converter {

    public static String E2K(String en) {
        if(en == null) return en;

        try{
            return new String(en.getBytes("8859_1"), "KSC5601");
        } catch(Exception e){
        }
        return en;
    }

    public static String K2E(String ko) {
        if(ko == null) return ko;

        try{
            return new String(ko.getBytes("KSC5601"), "8859_1");
        } catch(Exception e){
        }
        return ko;
    }

    public static String kToe(String en){
		try{
			if( en == null) return en;

			return new String(en.getBytes("EUC-KR"),"8859_1");
		}catch(Exception e){
			return en;
		}
	}

    public static String eTok(String ko){
		try{
			if( ko == null) return ko;
			
			return new String(ko.getBytes("8859_1"),"EUC-KR");
		}catch(Exception e){
			return ko;
		}
	}
    
    public static String lowerFirst(String s) {
        if (s == null || s.length() < 1)
            return s;
        return (s.substring(0, 1).toLowerCase() + s.substring(1));
    }

    public static String strip(String s, String delims) {
        if (s == null || s.length() == 0 || delims == null)
            return s;

        StringBuffer sb = new StringBuffer();

        StringTokenizer st = new StringTokenizer(s, delims);
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
        }

        return sb.toString();
    }
    public static String upperFirst(String s) {
        if (s == null || s.length() < 1)
            return s;
        return (s.substring(0, 1).toUpperCase() + s.substring(1));
    }
    public static String arrayToString(String[] values, String gubun) {
        StringBuffer sb = new StringBuffer();
        if (values == null || values.length < 1)
            return "";
        sb.append(values[0]);
        for (int i = 1; i < values.length; i++) {
            sb.append(gubun).append(values[i]);
        }
        return sb.toString();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] stringToArray(String text, String gubun) {
        ArrayList array = new ArrayList();
        String cur = text;
        while (cur != null) {
            int i = cur.indexOf(gubun);
            if (i < 0) {
                array.add(cur);
                cur = null;
            } else {
                array.add(cur.substring(0, i));
                cur = cur.substring(i + gubun.length());
            }
        }
        return (String[]) array.toArray(new String[array.size()]);
    }

    public static String str2alert(String s) {
        if (s == null)
            return null;
        StringBuffer buf = new StringBuffer();
        char[] c = s.toCharArray();
        int len = c.length;
        for (int i = 0; i < len; i++) {
            if (c[i] == '\n')
                buf.append("\\n");
            else if (c[i] == '\t')
                buf.append("\\t");
            else if (c[i] == '"')
                buf.append("'");
            else
                buf.append(c[i]);
        }
        return buf.toString();
    }
    public static String java2msg(String s) {
        if (s == null)
            return null;
        StringBuffer buf = new StringBuffer();
        char[] c = s.toCharArray();
        int len = c.length;
        for (int i = 0; i < len; i++) {
            if (c[i] == '\n')
                buf.append("\\n");
            else if (c[i] == '\t')
                buf.append("\\t");
            else
                buf.append(c[i]);
        }
        return buf.toString();
    }
    public static String java2html(String s) {
        if (s == null)
            return null;
        StringBuffer buf = new StringBuffer();
        char[] c = s.toCharArray();
        int len = c.length;
        for (int i = 0; i < len; i++) {
            if (c[i] == '&')
                buf.append("&amp;");
            else if (c[i] == '<')
                buf.append("&lt;");
            else if (c[i] == '>')
                buf.append("&gt;");
            else if (c[i] == '"')
                buf.append("&quot;");
            else if (c[i] == '\'')
                buf.append("&#039;");
            else
                buf.append(c[i]);
        }
        return buf.toString();
    }
    public static String java2html2(String s){
    	StringBuffer buf = new StringBuffer();
    	
    	for(int i=0 ; i<s.length() ; i++){
    		char ch = s.charAt(i);
    		Character character = new Character(ch);
    		
    		if(character.hashCode() == 10){
    			buf.append("<br>");
    		}else if(character.hashCode() == 32){
    			buf.append("&nbsp;");
    		}else{
    			buf.append(ch);
    		}
    	}
    	
    	return buf.toString();
    }
    
    public static String java2html3(String s) {
        if (s == null)
            return null;
        StringBuffer buf = new StringBuffer();
        char[] c = s.toCharArray();
        int len = c.length;
        for (int i = 0; i < len; i++) {
			if (c[i] == '\\')
                buf.append("\\\\");
            else if (c[i] == '"')
                buf.append("\\\"");
            else if (c[i] == '\'')
                buf.append("\\\'");
            else if (String.valueOf(c[i]).hashCode() == 10 || String.valueOf(c[i]).hashCode() == 13){
                buf.append("");
            }
            else
                buf.append(c[i]);
        }
        return buf.toString();
    }
    
    public static String java2html4(String s) {
    	if (s == null)
    		return null;
    	StringBuffer buf = new StringBuffer();
    	char[] c = s.toCharArray();
    	int len = c.length;
    	for (int i = 0; i < len; i++) {
    		if (c[i] == '\\')
    			buf.append("\\\\");
    		else if (c[i] == '"')
    			buf.append("\\\"");
    		else if (c[i] == '\'')
    			buf.append("\\\'");
    		else if (String.valueOf(c[i]).hashCode() == 10){
    			buf.append("");
    		}else if (String.valueOf(c[i]).hashCode() == 13) 
    			buf.append("\\n");
    		else
    			buf.append(c[i]);
    	}
    	return buf.toString();
    }
    
    public static String singleQuotTo2(String s) {
        if (s == null)
            return null;
        StringBuffer buf = new StringBuffer();
        char[] c = s.toCharArray();
        int len = c.length;
        for (int i = 0; i < len; i++) {
			if (c[i] == '\'')
                buf.append("''");
            else
                buf.append(c[i]);
        }
        return buf.toString();
    }
    
    public static String encodingExceptSpecialChar(String s) {
		s = s.replaceAll("\\+", "%20");
    	s = s.replaceAll("%21", "!");
		s = s.replaceAll("%23", "#");
		s = s.replaceAll("%24", "$");
		s = s.replaceAll("%25", "%");
		s = s.replaceAll("%26", "&");
		s = s.replaceAll("%28", "(");
		s = s.replaceAll("%29", ")");
		s = s.replaceAll("%2B", "+");
		s = s.replaceAll("%3B", ";");
		s = s.replaceAll("%3D", "=");
		s = s.replaceAll("%40", "@");
		s = s.replaceAll("%5B", "[");
		s = s.replaceAll("%5D", "]");
		s = s.replaceAll("%5E", "^");
    	
    	return s;
    }
}
