package com.trade.project.framework;

public class YhtException extends Exception {
	private String msgCode;
	private String msgAlertYn;
	private String msgAfter;
	
	public YhtException(){
		super();
	}
	
	public YhtException(String msgCode){
		super();
		
		this.msgCode = msgCode;
	}
	
	public YhtException(String msgCode, String msgAlertYn){
		super();
		
		this.msgCode    = msgCode;
		this.msgAlertYn = msgAlertYn;
	}
	
	public YhtException(String msgCode, String msgAlertYn, String msgAfter){
		super();
		
		this.msgCode    = msgCode;
		this.msgAlertYn = msgAlertYn;
		this.msgAfter   = msgAfter;
	}
	
	public void setMsgCode(String msgCode){
		this.msgCode = msgCode;
	}
	
	public String getMsgCode(){
		return msgCode;
	}
	
	public void setMsgAlertYn(String msgAlertYn){
		this.msgAlertYn = msgAlertYn;
	}
	
	public String getMsgAlertYn(){
		return msgAlertYn;
	}

	public String getMsgAfter() {
		return msgAfter;
	}

	public void setMsgAfter(String msgAfter) {
		this.msgAfter = msgAfter;
	}
}
