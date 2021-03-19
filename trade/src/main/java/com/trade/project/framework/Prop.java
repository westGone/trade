package com.trade.project.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;

public class Prop {
	
	private static MessageSourceAccessor messageSourceAccessor;
	
	@SuppressWarnings("static-access")
	@Autowired
	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor){
		this.messageSourceAccessor = messageSourceAccessor;
	}
	
	public static String getMessage(String key){
		String msg = null;
		
		try{
			msg = messageSourceAccessor.getMessage(key);
		}catch(Exception e){
			msg = null;
		}
		
		return msg;
	}
}
