package com.trade.project.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trade.project.framework.GlobalUtil;

import net.sf.json.JSONObject;

@Controller
public class TranslationController {
	protected static final Log logger = LogFactory.getLog(TranslationController.class);
	
	/**
	 * Papago API를 연동하여 다국어 작업을 처리.
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/common/translation.do")
	public void translation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject resJsonObject = new JSONObject();
		
		Map<String, Object> reqMap = GlobalUtil.convertMapArrayToObject(request.getParameterMap());
		
		String[] transList = {"en","zh-CN","ja","es","vi"};//번역할 언어값 설정 (zh-CN = 중국어 간체)
        String clientId = "Xv9lpWUKqRAs_INxoic1";//애플리케이션 클라이언트 아이디값
        String clientSecret = "sRxRqeB2Ro";//애플리케이션 클라이언트 시크릿값
        
        for(int i=0; i<transList.length; i++) {
	        try {
	        	//번역할 내용
	            String text = URLEncoder.encode(reqMap.get("beforeTranslation").toString(), "UTF-8");
	            
	            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
	            URL url = new URL(apiURL);
	            HttpURLConnection con = (HttpURLConnection)url.openConnection();
	            
	            con.setRequestMethod("POST");
	            con.setRequestProperty("X-Naver-Client-Id", clientId);
	            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
	            
	            //한국어->선언된 배열의 언어
	            String transEng = "source=ko&target="+transList[i]+"&text=" + text;
	            con.setDoOutput(true);
	            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	            wr.writeBytes(transEng);
	            wr.flush();
	            wr.close();
	            int responseCode = con.getResponseCode();
	            BufferedReader br;
	            if(responseCode==200) { // 정상 호출
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            } else {  // 에러 발생
	                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            }
	            String inputLine;
	            StringBuffer responseTranslation = new StringBuffer();
	            while ((inputLine = br.readLine()) != null) {
	            	responseTranslation.append(inputLine);
	            }
	            //js에서 '-' 값을 인식하지 못해 변경
	            if(transList[i].equals("zh-CN")) {
	            	transList[i] = "cn";
	            }
	            resJsonObject.put(transList[i], responseTranslation.toString());
	            br.close();
		        con.disconnect();
	        } catch (Exception e) {
	        	logger.error(e);
	        }
        }
        //결과값 출력
        logger.debug("--------------------------------Translation Result");
        logger.debug(resJsonObject);
        
		response.setContentType("application/x-json;  charset=UTF-8");
		response.getWriter().print(resJsonObject);
        
    }
}