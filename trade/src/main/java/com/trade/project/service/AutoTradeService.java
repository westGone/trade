package com.trade.project.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonBeanProcessor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.project.controller.CommonController;
import com.trade.project.dao.AutoTradeDao;
import com.trade.project.dao.CommonDao;
import com.trade.project.dao.SettingsDao;

@Service("autoTradeService")
public class AutoTradeService {
	protected static final Log logger = LogFactory.getLog(AutoTradeService.class);

	@Resource(name="autoTradeService")
	AutoTradeService autoTradeService;
	
	@Resource(name="autoTradeDao")
	AutoTradeDao autoTradeDao;
	
	@Resource(name="commonController")
	CommonController commonController;
	
	/**
	 * 파이썬 API 를 통해 DMI의 가격을 가지고 오고, 전 가격과 비교하여 매수, 매도 진행
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void autoTradeSystem(JSONObject reqJsonObject, JSONObject resJsonObject) throws java.lang.Exception {
		String [] coinList = {"Btc","Eth","Xrp"};
		
		for (int i=0; i<coinList.length; i++) {
			String dimValue = this.buffer("C:/Users/jiwon/git/trade/trade/src/main/java/com/trade/project/python/"+coinList[i]+"DmiCheck.py");
			
			dimValue 			= dimValue.replaceAll(" ", "");
	        String [] cuVaArr 	= dimValue.split(",");
			
	        if(cuVaArr.length == 6) {
		        double prePlusDi 	= Double.valueOf(cuVaArr[0]);
		        double curPlusDi 	= Double.valueOf(cuVaArr[1]);
		        double preMinusDi 	= Double.valueOf(cuVaArr[2]);
		        double curMinusDi 	= Double.valueOf(cuVaArr[3]);
		        double preAdx 		= Double.valueOf(cuVaArr[4]);
		        double curAdx 		= Double.valueOf(cuVaArr[5]);
		        
		        // DMI +상향 돌파
		        if(curPlusDi > curMinusDi && prePlusDi < preMinusDi) {
		        	String buyValue 	= this.buffer("C:/Users/jiwon/git/trade/trade/src/main/java/com/trade/project/python/Buy"+coinList[i]+".py");
		        	buyValue			= buyValue.replaceAll(" ", "");
		        	String [] buyArray 	= buyValue.split(",");
		        	
		        	if(buyArray.length != 0) {
		        		reqJsonObject.put("orderType", "S");
		        		Map<String, Object> orderInfo = new HashMap<>();
		        		orderInfo.put("coinType", coinList[i]);
		        		if("LB".equals(buyArray[0].toString())) {
		        			//Sell 청산 이력
		        			orderInfo = autoTradeDao.getOrderKey(reqJsonObject);
		        			orderInfo.put("orderKey"	, orderInfo.get("ORDER_KEY"));
		        			orderInfo.put("orderType"	, "S");
		        			orderInfo.put("price"		, buyArray[1]);
		        			autoTradeDao.setOrder(orderInfo);
		        			
		        			//Buy 생성 이력
		        			long time = System.currentTimeMillis();
		        			orderInfo.put("orderKey"	, "DII_"+time);
		        			orderInfo.put("orderType"	, "B");
		        			orderInfo.put("price"		, buyArray[1]);
		        			autoTradeDao.setOrder(orderInfo);
		        		}else if("BU".equals(buyArray[0].toString())) {
		        			//Buy 생성 이력
		        			long time = System.currentTimeMillis();
		        			orderInfo.put("orderKey"	, "DII_"+time);
		        			orderInfo.put("orderType"	, "B");
		        			orderInfo.put("price"		, buyArray[1]);
		        			autoTradeDao.setOrder(orderInfo);
		        		}
		        	}
		        }
		        
		        // DMI +하향 돌파
		        if(curPlusDi < curMinusDi && prePlusDi > preMinusDi) {
		        	String sellValue 	= this.buffer("C:/Users/jiwon/git/trade/trade/src/main/java/com/trade/project/python/Sell"+coinList[i]+".py");
		        	sellValue			= sellValue.replaceAll(" ", "");
		        	String [] sellArray = sellValue.split(",");
		        	
		        	if(sellArray.length != 0) {
		        		reqJsonObject.put("orderType", "B");
		        		Map<String, Object> orderInfo = new HashMap<>();
		        		orderInfo.put("coinType", coinList[i]);
		        		if("LS".equals(sellArray[0].toString())) {
		        			//Buy 청산 이력
		        			orderInfo = autoTradeDao.getOrderKey(reqJsonObject);
		        			orderInfo.put("orderKey"	, orderInfo.get("ORDER_KEY"));
		        			orderInfo.put("orderType"	, "B");
		        			orderInfo.put("price"		, sellArray[1]);
		        			autoTradeDao.setOrder(orderInfo);
		        			
		        			//Sell 생성 이력
		        			long time = System.currentTimeMillis();
		        			orderInfo.put("orderKey"	, "DII_"+time);
		        			orderInfo.put("orderType"	, "S");
		        			orderInfo.put("price"		, sellArray[1]);
		        			autoTradeDao.setOrder(orderInfo);
		        		}else if("SE".equals(sellArray[0].toString())) {
		        			//Sell 생성 이력
		        			long time = System.currentTimeMillis();
		        			orderInfo.put("orderKey"	, "DII_"+time);
		        			orderInfo.put("orderType"	, "S");
		        			orderInfo.put("price"		, sellArray[1]);
		        			autoTradeDao.setOrder(orderInfo);
		        		}
		        	}
		        }
	        }
		}
//		commonController.setListData(settingsDao.getCodeDefineList(reqJsonObject), resJsonObject);
	}
	
	/**
	 * 파이썬 API 를 통해 MA의 가격을 가지고 오고, 전 가격과 비교하여 매수, 매도 진행
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void maAutoTradeSystem(JSONObject reqJsonObject, JSONObject resJsonObject) throws java.lang.Exception {
		String [] coinList = {"Xrp"};
		
		for (int i=0; i<coinList.length; i++) {
			String dimValue = this.buffer("C:/Users/jiwon/git/trade/trade/src/main/java/com/trade/project/python/"+coinList[i]+"MaCheck.py");
			
			dimValue 			= dimValue.replaceAll(" ", "");
			String [] cuVaArr 	= dimValue.split(",");
			
			if(cuVaArr.length == 4) {
				double pre5Ma 	= Double.valueOf(cuVaArr[0]);
				double cur5Ma 	= Double.valueOf(cuVaArr[1]);
				double pre20Ma 	= Double.valueOf(cuVaArr[2]);
				double cur20Ma 	= Double.valueOf(cuVaArr[3]);
				
				// 5일선 20일선 상향 돌파
				if(cur5Ma > cur20Ma && pre5Ma < pre20Ma) {
					String buyValue 	= this.buffer("C:/Users/jiwon/git/trade/trade/src/main/java/com/trade/project/python/BuyMa"+coinList[i]+".py");
					buyValue			= buyValue.replaceAll(" ", "");
					String [] buyArray 	= buyValue.split(",");
//					
//					if(buyArray.length != 0) {
//						reqJsonObject.put("orderType", "S");
//						Map<String, Object> orderInfo = new HashMap<>();
//						orderInfo.put("coinType", coinList[i]);
//						if("LB".equals(buyArray[0].toString())) {
//							//Sell 청산 이력
//							orderInfo = autoTradeDao.getOrderKey(reqJsonObject);
//							orderInfo.put("orderKey"	, orderInfo.get("ORDER_KEY"));
//							orderInfo.put("orderType"	, "S");
//							orderInfo.put("price"		, buyArray[1]);
//							autoTradeDao.setOrder(orderInfo);
//							
//							//Buy 생성 이력
//							long time = System.currentTimeMillis();
//							orderInfo.put("orderKey"	, "DII_"+time);
//							orderInfo.put("orderType"	, "B");
//							orderInfo.put("price"		, buyArray[1]);
//							autoTradeDao.setOrder(orderInfo);
//						}else if("BU".equals(buyArray[0].toString())) {
//							//Buy 생성 이력
//							long time = System.currentTimeMillis();
//							orderInfo.put("orderKey"	, "DII_"+time);
//							orderInfo.put("orderType"	, "B");
//							orderInfo.put("price"		, buyArray[1]);
//							autoTradeDao.setOrder(orderInfo);
//						}
//					}
				}
				// 5일선 20일선 하향 돌파
				if(cur5Ma < cur20Ma && pre5Ma > pre20Ma) {
					System.out.println(coinList[i]);
					String sellValue 	= this.buffer("C:/Users/jiwon/git/trade/trade/src/main/java/com/trade/project/python/SellMa"+coinList[i]+".py");
					sellValue			= sellValue.replaceAll(" ", "");
					String [] sellArray = sellValue.split(",");
//					
//					if(sellArray.length != 0) {
//						reqJsonObject.put("orderType", "B");
//						Map<String, Object> orderInfo = new HashMap<>();
//						orderInfo.put("coinType", coinList[i]);
//						if("LS".equals(sellArray[0].toString())) {
//							//Buy 청산 이력
//							orderInfo = autoTradeDao.getOrderKey(reqJsonObject);
//							orderInfo.put("orderKey"	, orderInfo.get("ORDER_KEY"));
//							orderInfo.put("orderType"	, "B");
//							orderInfo.put("price"		, sellArray[1]);
//							autoTradeDao.setOrder(orderInfo);
//							
//							//Sell 생성 이력
//							long time = System.currentTimeMillis();
//							orderInfo.put("orderKey"	, "DII_"+time);
//							orderInfo.put("orderType"	, "S");
//							orderInfo.put("price"		, sellArray[1]);
//							autoTradeDao.setOrder(orderInfo);
//						}else if("SE".equals(sellArray[0].toString())) {
//							//Sell 생성 이력
//							long time = System.currentTimeMillis();
//							orderInfo.put("orderKey"	, "DII_"+time);
//							orderInfo.put("orderType"	, "S");
//							orderInfo.put("price"		, sellArray[1]);
//							autoTradeDao.setOrder(orderInfo);
//						}
//					}
				}
			}
		}
//		commonController.setListData(settingsDao.getCodeDefineList(reqJsonObject), resJsonObject);
	}
	/**
	 * 파이썬 실행 후 Print 내용 리턴
	 * @param path
	 * @return
	 * @throws java.lang.Exception
	 */
	public String buffer(String path) throws java.lang.Exception {
		String currentValue = "";
		
		ProcessBuilder pb = new ProcessBuilder("C:/Python39/python.exe", path);
		Process p = pb.start();
		
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuilder buffer = new StringBuilder();
        String line = null;
        while ((line = in.readLine()) != null){
            buffer.append(line);
        }
        int exitCode = p.waitFor();
        
        currentValue = buffer.toString();
        
        System.out.println("Value is: "+buffer.toString());
        System.out.println("Process exit value:"+exitCode);
        in.close();
        
		return currentValue;
	}
	
	/**
	 * 현재 Coin들의 가격을 가지고온다.
	 * @param value
	 * @param resJsonObject
	 * @throws java.lang.Exception
	 */
	public void getPriceData(Map<String, Object> value, JSONObject resJsonObject) throws java.lang.Exception {
		String priceValue = this.buffer("C:/Users/jiwon/git/trade/trade/src/main/java/com/trade/project/python/GetCoinPrice.py");
		
		priceValue 			= priceValue.replaceAll(" ", "");
        String [] cuVaArr 	= priceValue.split(",");
        
        if(cuVaArr.length == 8) {
	        double btcPrice 	= Double.valueOf(cuVaArr[0]);
	        double btcPercent 	= Double.valueOf(cuVaArr[1]);
	        double ethPrice 	= Double.valueOf(cuVaArr[2]);
	        double ethPercent 	= Double.valueOf(cuVaArr[3]);
	        double eosPrice		= Double.valueOf(cuVaArr[4]);
	        double eosPercent 	= Double.valueOf(cuVaArr[5]);
	        double xrpPriec		= Double.valueOf(cuVaArr[6]);
	        double xrpPercent 	= Double.valueOf(cuVaArr[7]);
	        
	        resJsonObject.put("btcPrice"	, String.format("%.2f", btcPrice));
	        resJsonObject.put("ethPrice"	, String.format("%.2f", ethPrice));
	        resJsonObject.put("eosPrice"	, String.format("%.2f", eosPrice));
	        resJsonObject.put("xrpPriec"	, String.format("%.2f", xrpPriec));
	        
	        resJsonObject.put("btcPercent"	, String.format("%.1f", btcPercent*100));
	        resJsonObject.put("ethPercent"	, String.format("%.1f", ethPercent*100));
	        resJsonObject.put("eosPercent"	, String.format("%.1f", eosPercent*100));
	        resJsonObject.put("xrpPercent"	, String.format("%.1f", xrpPercent*100));
        }
	}
}