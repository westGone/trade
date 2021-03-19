package com.trade.project.framework;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

public class GlobalUtil {
	protected static final Log logger = LogFactory.getLog(GlobalUtil.class);
	
	public static void fileWrite(String fileStr, String filePath, String fileName) {
		fileWrite(fileStr, filePath, fileName, "UTF-8");
	 }
	
	public static void fileWrite(String fileStr, String filePath, String fileName, String encoding) {
		OutputStreamWriter out = null;
		File pathDir = new File(filePath);
		
		try{
			if(!pathDir.exists()){
				logger.debug("make dir :"+filePath);
				pathDir.mkdirs();
			}

			out = new OutputStreamWriter(new FileOutputStream(filePath+fileName), encoding);
			out.write(fileStr);
			
		} catch(IOException e) {
			logger.error(e.toString());
		}
		finally {
			try	{
				out.close();
			} catch(Exception e) {}
		}
	 }
	
	public static String fileRead(String filePath, String fileName) {
		return fileRead(filePath, fileName, "UTF-8");
	 }
	
	public static String fileRead(String filePath, String fileName, String encoding) {
		
		BufferedReader br = null;
		StringBuffer sf = new StringBuffer();
		String s = null;
		
		try{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath+fileName), encoding));
			
			while((s=br.readLine())!=null){
				//KTNET FHL문서 엔터값으로 인하여 추가 2016-03-31 HIT
				if(fileName.contains("KTNET_H") || fileName.contains("FNC_LINE") || fileName.contains("awb") ){
					sf.append(s+"\r\n");
				}else{
					sf.append(s);
				}
			}
			
		} catch(IOException e) {
			logger.error(e.toString());
		}
		finally {
			try	{
				if(br != null) try{br.close();}catch(IOException e){}
			} catch(Exception e) {}
		}
		return sf.toString();
	 }
	
//	public static void fileWrite(Workbook wb, String filePath, String fileName) {
//		FileOutputStream out = null;
//		File pathDir = new File(filePath);
//		
//		try{
//			if(!pathDir.exists()){
//				logger.debug("make dir :"+filePath);
//				pathDir.mkdirs();
//			}
//			out = new FileOutputStream(filePath+fileName);
//	        wb.write(out);
//	        out.close();			
//		} catch(IOException e) {
//			logger.error(e.toString());
//		}
//		finally {
//			try	{
//				out.close();
//			} catch(Exception e) {}
//		}
//	 }
	
	public static void imgFileWrite(byte[] bytes, String filePath, String fileName, Double form_width, Double form_height) {
		File pathDir = new File(filePath);

		try{
			if(bytes != null) {
				if(!pathDir.exists()){
					logger.debug("make dir :"+filePath);
					pathDir.mkdirs();
				}
				
				//InputStream in = new ByteArrayInputStream(bytes);
				BufferedImage bImageFromConvert = getResize(bytes, form_width, form_height);
				ImageIO.write(bImageFromConvert, "jpg", new File(filePath+fileName));
			}
		} catch(IOException e) {
			logger.error(e.toString());
		}
	 }
	
//	/*
//     * QR코드 생성 유틸
//     * @param url : QR 생성 URL
//     * @param width : 폭
//     * @param height : 높이
//     * @param filePath : 폴더
//     * @param fileName : 파일명
//     */
//    public static String makeQRCode(String qrCode, int width, int height, String filePath, String fileName){
//        File file = null;
//        String rtnfileName = filePath+fileName+".jpg";
//        
//        try {
//            // 경로가 존재하지 않으면 경로 생성
//            file = new File(filePath);
//            if(!file.exists()){
//                file.mkdirs();
//            }
//            // UTF-8로 인코딩된 문자열을 ISO-8859-1로 생성
//            qrCode = new String(qrCode.getBytes("UTF-8"),"ISO-8859-1");
//            // QRCodeWriter객체 생성
//            QRCodeWriter writer = new QRCodeWriter();
//            BitMatrix matrix = writer.encode(qrCode, BarcodeFormat.QR_CODE, width, height);
//
//            // BufferedImage 객체 생성
//            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);
//            // 이미지 저장
//            ImageIO.write(qrImage, "jpg", new File(rtnfileName));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (WriterException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return rtnfileName;
//    }
	
	private static BufferedImage getResize(byte[] bytes, Double form_width, Double form_height) throws IOException {
		int width = (int) (form_width/100*96);
		int height = (int) (form_height/100*96);
		
        ImageIcon ii = new ImageIcon(bytes);
        Image i = ii.getImage();
        Image resizedImage = null;
        resizedImage = i.getScaledInstance(width, height, Image.SCALE_DEFAULT);

        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
   
        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();
   
        // Clear background and paint the image.
        g.drawImage(temp, 0, 0, null);
        g.dispose();
   
        return bufferedImage;
	}

	
	public static void fileDelete(String filePath, String fileName) {
		File file = new File(filePath + fileName);
		file.delete();
	 }
	
	public static String chekcFileEncoding(String filePathName) {
		// 1. 파일 열기
		FileInputStream fis;
		String rtnStr = "";
		try {
			fis = new FileInputStream(filePathName);
			
			// 2. 파일 읽기 (4Byte)
			byte[] BOM = new byte[4];
			fis.read(BOM, 0, 4);
			fis.close();
			
			// 3. 파일 인코딩 확인하기
			if( (BOM[0] & 0xFF) == 0xEF && (BOM[1] & 0xFF) == 0xBB && (BOM[2] & 0xFF) == 0xBF ) rtnStr = "UTF-8";				
			else if( (BOM[0] & 0xFF) == 0xFE && (BOM[1] & 0xFF) == 0xFF ) rtnStr = "UTF-16BE";
			else if( (BOM[0] & 0xFF) == 0xFF && (BOM[1] & 0xFF) == 0xFE ) rtnStr = "UTF-16LE";
			else if( (BOM[0] & 0xFF) == 0x00 && (BOM[1] & 0xFF) == 0x00 && (BOM[0] & 0xFF) == 0xFE && (BOM[1] & 0xFF) == 0xFF )  rtnStr = "UTF-32BE";
			else if( (BOM[0] & 0xFF) == 0xFF && (BOM[1] & 0xFF) == 0xFE && (BOM[0] & 0xFF) == 0x00 && (BOM[1] & 0xFF) == 0x00 )  rtnStr = "UTF-32LE";
			else  rtnStr = "EUC-KR";
						
		} catch (Exception e) {			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rtnStr;
	 }
	
	public static int getByteLength(String strSource) {
		char[] charArray = strSource.toCharArray();
		int strIndex = 0;
		int byteLength = 0;
		for( ; strIndex < strSource.length(); strIndex++ ) {	          
	          int byteSize = 0;
	          if( charArray[ strIndex ] < 256) {
	             // 1byte character 이면
	             byteSize = 1; 
	          } else {
	             // 2byte character 이면
	             byteSize = 2;
	          }
	          byteLength += byteSize;
	       }
		return byteLength;
	}
	
	//request.getParameterMap()을 SqlParameterSource에서 사용하기 위하여
	//Map 파라메터의 배열형태의 첫번째 값을 object형태로 변환하여 리턴
	public static Map<String, Object> convertMapArrayToObject(Map<String, String[]> map) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		Iterator<String> iterator = map.keySet().iterator();
	    while (iterator.hasNext()) {
	        String key = (String)iterator.next();
	        rtnMap.put(key, map.get(key)[0]);
	        
	        //logger.debug(key + ":" + map.get(key)[0]);
	    }
		return rtnMap;
	}
	
	// M.B/L No Check
	public static boolean chkDigitMawb(String strMAWB)
    {
        // 1) 항공사코드(3자리)를 제외한 나머지 7자리를 7로 나눈다.
        // 2) 1의 결과를 소수2자리에서 7을 곱한다.
        // 3) 2의 결과에서 소수첫째자리에서 반올림하고 정수부분이 ChkDigit 됨.
        if (strMAWB.trim().length() != 11) return false;

        double dblMAWB = 0;
        long lngChkDigit = 0;
        double dblDivide = 0;

        try {
            dblMAWB = Double.parseDouble(strMAWB.substring(3, strMAWB.length()-1));

            if (dblMAWB % 7 != 0) {
                int nInStr = 0;

                dblDivide = dblMAWB / 7;
                nInStr = (dblDivide+"").indexOf('.');
                dblDivide = Double.parseDouble((dblDivide+"").substring(nInStr, nInStr+3)) * 7;
            }

            lngChkDigit = Math.round(dblDivide);

            if (lngChkDigit == Integer.parseInt(strMAWB.substring(strMAWB.length()-1, strMAWB.length())))
                return true;
            else
                return false;

    	} catch (Exception e) {
            return false;
        }
    }
	
	/**
	 * 디렉토리 확인 후 없을 시 해당 디렉토리 생성
	 * @param fullPath
	 * @return
	 */
	public static boolean directoryConfirmAndMake(String fullPath) {
		boolean result = true;
		File dir = null;
		
		String[] path = fullPath.split("/");
		if(path != null) {
			String newPath = "";
			for(int i = 0, n = path.length; i < n; i++) {
				if(!"".equals(path[i])) {
					if(i == 0)	newPath = path[i];
					else		newPath += "/" + path[i];

					dir = new File(newPath);
					
					if(!dir.isDirectory()) {
						if(!dir.mkdirs()) {
							logger.error(path[i] + " Directory create fail! Plz check permission.");
							result = false;
							break;
						} else {
							logger.debug(path[i] + " Directory create success!");
						}
					}
				}
			}
		} else {
			logger.error("Parameter not found! Plz check parameter.");
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 특정 디렉토리 내의 서브 디렉토리, 파일 리스트 가져오기
	 * @param path
	 * @return JSONArray
	 */
	public static JSONArray checkFileList(String path) {
		File dirFile = new File(path);
		File[] fileList = dirFile.listFiles();
		
		List<Map<String, Object>> checkFileList = new ArrayList<Map<String, Object>>();
		
		for(File tempFile : fileList) {
			Map<String, Object> fileInfo = new HashMap<String, Object>();
			
			if(tempFile.isFile()) {
				fileInfo.put("name", tempFile.getName());
				fileInfo.put("type", "f");
				fileInfo.put("size", tempFile.length());
				
			} else if(tempFile.isDirectory()){
				fileInfo.put("name", tempFile.getName());
				fileInfo.put("type", "d");
				fileInfo.put("size", tempFile.length());				
			}
			
			checkFileList.add(fileInfo);
		}
		
		return JSONArray.fromObject(checkFileList);
	}
	
	/**
	 * 파일, 폴더 삭제
	 * @param folder
	 */
	public static void deleteDirectory(File folder) {
	    File[] files = folder.listFiles();
	    if(files != null) {
	        for(File file: files) {
	            if(file.isDirectory()) {
	            	deleteDirectory(file);
	            } else {
	            	file.delete();
	            }
	        }
	    }
	    folder.delete();
	}
	
	/**
	 * Servlet으로 넘어오는 Parameter명 확인
	 * @param servletName
	 * @param request
	 */
	@SuppressWarnings("rawtypes")
	public static void checkParameters(String servletName, HttpServletRequest request) {
		Enumeration enums = request.getParameterNames();
		int i = 1;
		while(enums.hasMoreElements()) {  
		    String paramName = (String)enums.nextElement();
		    logger.debug("[" + servletName + "] Paramter " + i + " : " + paramName);
		    i++;
		}
	}
	
//	/**
//	 * TEST
//	 * @param args
//	 * @throws Exception
//	 */
//	public static void main(String[] args) throws Exception {  
//		directoryConfirmAndMake("C:/imagebrowser/upload/YHTXX/abc");
//	}
	
	/**
	  * 주어진 길이(iLength)만큼 주어진 문자(cPadder)를 strSource의 왼쪽에 붙혀서 보내준다.
	  * ex) lpad("abc", 5, '^') ==> "^^abc"
	  *     lpad("abcdefghi", 5, '^') ==> "abcde"
	  *     lpad(null, 5, '^') ==> "^^^^^"
	  *
	  * @param strSource
	  * @param iLength
	  * @param cPadder
	  */
	public static String lpad(String strSource, int iLength, char cPadder) throws Exception {
		StringBuffer sbBuffer = null;
		
		// 길이가 '0' 일 경우
		if(iLength == 0) {
			return strSource;
		}
				
		if (!"".equals(strSource)) {
			int iByteSize = getKor2ByteLength(strSource);
			
			// 파일길이
			if (iByteSize > iLength) {
				return lpad(getKOR2bytePassing(strSource, iLength), iLength, cPadder);
			} else if (iByteSize == iLength) {
				return strSource;
			} else {
				int iPadLength = iLength - iByteSize;
				sbBuffer = new StringBuffer();
				
				for (int j = 0; j < iPadLength; j++) {
					sbBuffer.append(cPadder);
				}
				
				sbBuffer.append(strSource);
				
				return sbBuffer.toString();
			}
		}
		
		sbBuffer = new StringBuffer();
		
		for (int j = 0; j < iLength; j++) {
			sbBuffer.append(cPadder);
		}
		return sbBuffer.toString();
	}
	
	/**
	  * 주어진 길이(iLength)만큼 주어진 문자(cPadder)를 strSource의 오른쪽에 붙혀서 보내준다.
	  * ex) lpad("abc", 5, '^') ==> "abc^^"
	  *     lpad("abcdefghi", 5, '^') ==> "abcde"
	  *     lpad(null, 5, '^') ==> "^^^^^"
	  *
	  * @param strSource
	  * @param iLength
	  * @param cPadder
	  */
	public static String rpad(String strSource, int iLength, char cPadder) throws Exception {
		StringBuffer sbBuffer = null;
		
		// 길이가 '0' 일 경우
		if(iLength == 0) {
			return strSource;
		}
		
		if (!"".equals(strSource)) {
			int iByteSize = getKor2ByteLength(strSource);
			
			if (iByteSize > iLength) {
				return rpad(getKOR2bytePassing(strSource, iLength), iLength, cPadder);
			} else if (iByteSize == iLength) {
				return strSource;
			} else {
				int iPadLength = iLength - iByteSize;
				sbBuffer = new StringBuffer(strSource);
				
				for (int j = 0; j < iPadLength; j++) {
					sbBuffer.append(cPadder);
				}
				return sbBuffer.toString();
			}
		}
		
		sbBuffer = new StringBuffer();
		
		for (int j = 0; j < iLength; j++) {
			sbBuffer.append(cPadder);
		}
		
		return sbBuffer.toString();
	}
	
	/**
	 * 한글일 경우 길이를 한글자의 길이를 '3' 로 계산후 리턴 
	 * 
	 * @param str			- 원본 문자열
	 * @param iLength	- 제한길이
	 * @return
	 */
	public static int getByteLengthForKOR(String str, int maxLength) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		
		int byteLength = 0;
		for(int index = 0 ; index < str.length(); ++index) {
			if(is1Byte(str.substring(index, index+1))) {
				// 1byte이면 길이에 1을 더한다.
				if(byteLength + 1 > maxLength) {
					break;
				}
				byteLength += 1;
			} else {
				// 1byte가 아니면 길이에 1을 더한다.
				if(byteLength + 3 > maxLength) {
					break;
				}
				byteLength += 3;
			}
		}
		
		return byteLength;
	}
	
	/**
	 * 한글일 경우 길이를 한글자의 길이를 '2' 로 계산후 리턴 
	 * 
	 * @param line
	 * @return
	 * @throws Exception
	 */
	public static int getKor2ByteLength(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		int byteLength = 0;
		for(int index = 0 ; index < str.length() ; ++index) {
			if(is1Byte(str.substring(index, index+1))) {
				//1byte이면 길이에 1을 더한다.
				byteLength += 1;
			} else {
				//2byte이면 길이에 2을 더한다.
				byteLength += 2;
			}
		}
		
		return byteLength;
	}
	
	/**
	 * 2byte 문자 여부
	 * 
	 * @param data	- 문자
	 * @return			- 1byte:false, 2byte:true
	 */
	public static boolean is1Byte(String data) {
		char[] charArray = data.toCharArray();
		
		if( charArray[ 0 ] < 256) {
			// 1byte character 이면
			return true;
		}
		else {
			// 2byte character 이면
			return false;
		}
	}
	
	/**
	 * 한글을 2byte로 계산해서 문자열 리턴
	 * 
	 * @param str			- 문자열
	 * @param cutByte	- 문자열 제한 byte 길이
	 * @return
	 * @throws Exception
	 */
	public static String getKOR2bytePassing(String str, int cutByte) throws Exception {
		char[] charArray = str.toCharArray();
		
		int itemMaxByteLength = cutByte;	// 파일 내용의 항목별 길이
		int lineReadIndex = 0;					// 문자열을 한문자씩 읽는 인덱스
		int readByteLengthByitem = 0;		// 현재 읽은 항목의 길이
		int readCharByteSize = 0;
		
		StringBuffer stringBuffer = new StringBuffer();
		
		for(; lineReadIndex < charArray.length;) {
			if(charArray[ lineReadIndex ] < 256) {
				// 1byte character 이면
				readCharByteSize = 1;				// 현재 읽은 문자 길이
			}
			else {
				// 2byte character 이면
				readCharByteSize = 2;				// 현재 읽은 문자 길이
			}
			
			readByteLengthByitem += readCharByteSize;
			
			// 읽은 길이가 제한 길이보다 크거나 같으면 종료
			if(readByteLengthByitem >= itemMaxByteLength) {
				// 종료전 길이가 같은 경우만 문자추가함.
				if(readByteLengthByitem == itemMaxByteLength) {
					stringBuffer.append(charArray[ lineReadIndex ]);
				}
				break;
			} else {
				stringBuffer.append(charArray[ lineReadIndex ]);
			}
			
			++lineReadIndex;
		}
		
		return stringBuffer.toString();
	}
	
	/**
	 * type, length에 맞춰서 문자열 리턴
	 * 		"String", "20", "한글1한글"		=> [한글1한글           ]
	 * 		"Number", "15", "12345"		=> [000000000012345]
	 * 		"Number", "15,2", "123.45"	=> [000000000123.45]
	 * )
	 * @param value
	 * @param type		- String, Number
	 * @param length		- Ex) '20', '15', '15,2'
	 * @return
	 * @throws Exception
	 */
	public static String getFileWriteData(String value, String type, String length) throws Exception {
		BigDecimal tempNumber = null;
		
		String retStr = "";
		String lengthGubun[] = null;
		
		if("String".equals(type)) {
			retStr = GlobalUtil.rpad(value, Integer.parseInt(length), ' ');
		} else if("Number".equals(type)) {
			// 소수점 포함된 자료일 경우
			if(length.indexOf(",") != -1) {
				lengthGubun = length.split(",");
				tempNumber = new BigDecimal(value).setScale(Integer.parseInt(lengthGubun[1]), BigDecimal.ROUND_DOWN);
				retStr = GlobalUtil.lpad(tempNumber.toString(), Integer.parseInt(lengthGubun[0]), '0');
			} else {
				retStr = GlobalUtil.lpad(value, com.trade.project.framework.Util.null2int(length), '0');
			}
		} else {
			throw new Exception(type.toString() + " 해당 항목의 타입이 존재하지 않습니다.");
		}
		
		return retStr;
	}
	
	
	/**
	 * type, length에 맞춰서 문자열 리턴
	 * 		"String", "20", "한글1한글"		=> [한글1한글           ]
	 * 		"Number", "15", "12345"		=> [000000000012345]
	 * 		"Number", "15,2", "123.45"	=> [000000000123.45]
	 * 
	 * -금액일 경우 오류로 인해 수정 2015-04-17
	 * )
	 * @param value
	 * @param type		- String, Number
	 * @param length		- Ex) '20', '15', '15,2'
	 * @return
	 * @throws Exception
	 */
	public static String getFileWriteMinusData(String value, String type, String length) throws Exception {
		BigDecimal tempNumber = null;
		
		String retStr = "";
		String lengthGubun[] = null;
		
		if("String".equals(type)) {
			retStr = GlobalUtil.rpad(value, Integer.parseInt(length), ' ');
		} else if("Number".equals(type)) {
			// 소수점 포함된 자료일 경우
			if(length.indexOf(",") != -1) {
				lengthGubun = length.split(",");
				tempNumber = new BigDecimal(value).setScale(Integer.parseInt(lengthGubun[1]), BigDecimal.ROUND_DOWN);
				retStr = GlobalUtil.lpad(tempNumber.toString(), Integer.parseInt(lengthGubun[0]), '0');
			} else {
				tempNumber = new BigDecimal(value);
				
				if(tempNumber.signum() == -1){
					retStr = GlobalUtil.lpad(tempNumber.negate().toString(), com.trade.project.framework.Util.null2int(length), '0');
				}else{
					
					retStr = GlobalUtil.lpad(value, com.trade.project.framework.Util.null2int(length), '0');
				}
				
			}
		} else {
			throw new Exception(type.toString() + " 해당 항목의 타입이 존재하지 않습니다.");
		}
		
		return retStr;
	}
	
	
	/**
	 * type, length에 맞춰서 문자열 리턴
	 * 		"String", "20", "한글1한글"		=> [한글1한글           ]
	 * 		"Number", "15", "12345"		=> [          12345]
	 * 		"Number", "15,2", "123.45"	=> [         123.45]
	 * )
	 * @param value
	 * @param type		- String, Number
	 * @param length		- Ex) '20', '15', '15,2'
	 * @return
	 * @throws Exception
	 */
	public static String getFileWriteDataWithNumLpadBlank(String value, String type, String length) throws Exception {
		BigDecimal tempNumber = null;
		
		String retStr = "";
		String lengthGubun[] = null;
		
		if("String".equals(type)) {
			retStr = GlobalUtil.rpad(value, Integer.parseInt(length), ' ');
		} else if("Number".equals(type)) {
			// 소수점 포함된 자료일 경우
			if(length.indexOf(",") != -1) {
				lengthGubun = length.split(",");
				tempNumber = new BigDecimal(value).setScale(Integer.parseInt(lengthGubun[1]), BigDecimal.ROUND_DOWN);
				retStr = GlobalUtil.lpad(tempNumber.toString(), Integer.parseInt(lengthGubun[0]), ' ');
			} else {
				retStr = GlobalUtil.lpad(value, com.trade.project.framework.Util.null2int(length), ' ');
			}
		} else {
			throw new Exception(type.toString() + " 해당 항목의 타입이 존재하지 않습니다.");
		}
		
		return retStr;
	}
	
	/**
	 * type, length에 맞춰서 문자열 리턴
	 * 		"String", "20", "한글1한글"		=> [한글1한글           ] => 공백제거후[한글1한글]
	 * 		"Number", "15", "12345"		=> [000000000012345]
	 * 		"Number", "15,2", "123.45"	=> [000000000123.45]
	 * )
	 * @param value
	 * @param type		- String, Number
	 * @param length		- Ex) '20', '15', '15,2'
	 * @return
	 * @throws Exception
	 */
	public static String getFileWriteDataWithRtrim(String value, String type, String length) throws Exception {
		return getFileWriteData(value, type, length).trim();
	}
	
	/**
	 * type, length에 맞춰서 문자열 리턴 // 101파일스펙 전용
	 * 		"String", "20", "한글1한글"		=> [한글1한글           ]
	 * 		"Number", "15", "12345"		=> [000000000012345]
	 * 		"Number", "15,3", "123.45"	=> [000000000123450]
	 * )
	 * @param value
	 * @param type		- String, Number
	 * @param length		- Ex) '20', '15', '15,2'
	 * @return
	 * @throws Exception
	 * 2016-04-21 지서희 추가
	 */
	public static String getFileWriteDataAisVat(String value, String type, String length) throws Exception {
		BigDecimal tempNumber = null;
		
		String retStr = "";
		String reTempNumber = "";
		String lengthGubun[] = null;
		
		if("String".equals(type)) {
			retStr = GlobalUtil.rpad(value, Integer.parseInt(length), ' ');
		} else if("Number".equals(type)) {
			// 소수점 포함된 자료일 경우
			if(length.indexOf(",") != -1) {
				lengthGubun = length.split(",");
				tempNumber = new BigDecimal(value).setScale(Integer.parseInt(lengthGubun[1]), BigDecimal.ROUND_DOWN);
				reTempNumber = tempNumber.toString().replace(".", ""); //소수점제거
				retStr = GlobalUtil.lpad(reTempNumber, Integer.parseInt(lengthGubun[0]), '0');
			} else {
				retStr = GlobalUtil.lpad(value, com.trade.project.framework.Util.null2int(length), '0');
			}
		} else {
			throw new Exception(type.toString() + " 해당 항목의 타입이 존재하지 않습니다.");
		}
		
		/*
		 * "Number", "9", "-12345" 의 경우  => [000-12345] 로 변환되는 문제 발생
		 *  [000-12345] => [-00012345] 변환
		 * 2017-04-17 수정
		 * VESSEL_FLIGHT 경우 HEUNG-A 가 -HEUNGA로 나오는 문제발생
		 * */
		if("Number".equals(type)){
			if(retStr.contains("-")){ 
				retStr = retStr.replace("-", "");
				retStr = "-" + retStr;
			}
		}
		
		return retStr;
	}
	
	/**
	 * type, length, start, end 에 맞춰서 문자열 리턴
	 * 
	 * @param readLine	- 문자열
	 * @param type		- String, Number
	 * @param length	- Ex) '20', '15', '15,2'
	 * @param start		- 읽기 시작위치
	 * @param end		- 일기 끝위치
	 * @return
	 * @throws Exception
	 */
	public static String getFileReadData(String readLine, String type, String length, int start, int end) throws Exception {
		char[] charArray = readLine.toCharArray();
		
		String lengthGubun[] = null;
		StringBuffer stringBuffer = new StringBuffer();
		
		if("String".equals(type)) {
			lengthGubun = length.split(",");
		} else if("Number".equals(type)) {
			// 소수점 포함된 자료일 경우
			if(length.indexOf(",") != -1) {
				lengthGubun = length.split(",");
			} else {
				lengthGubun = length.split(",");
			}
		} else {
			throw new Exception(type.toString() + " 해당 항목의 타입이 존재하지 않습니다.");
		}
		
		int itemMaxByteLength = Integer.parseInt(lengthGubun[0]);	// 파일 내용의 항목별 길이
		int lineReadIndex = 0;				// 문자열을 한문자씩 읽는 인덱스
		int readByteLengthByitem = 0;	// 현재 읽은 항목의 길이
		int readByteLengthByline = 0;	// 현재 읽은 항목의 전체 길이
		int readCharByteSize = 0;			// 현재 읽은 문자 하나의 byte 길이
		
		for(; lineReadIndex < charArray.length;) {
			if(charArray[ lineReadIndex ] < 256) {
				// 1byte character 이면
				readByteLengthByline += 1;		// 읽은 항목의 전체 길이
				readCharByteSize = 1;
			}
			else {
				// 2byte character 이면
				readByteLengthByline += 2;		// 항목의 전체 길이 구하기
				readCharByteSize = 2;
			}
			
			// 읽은 전체 길이가 시작길이, 끝길이 사이값일 경우 
			// start + 1 이유는 substring(4, 6) 일 경우 
			if((readByteLengthByline - 1) >= start && readByteLengthByline <= end) {
				stringBuffer.append(charArray[ lineReadIndex ]);
				
				readByteLengthByitem += readCharByteSize;
				
				// 읽은 길이가 제한 길이보다 크거나 같으면 종료
				if(readByteLengthByitem >= itemMaxByteLength) {
					break;
				}
			}
			
			++lineReadIndex;
		}
		
		BigDecimal tempNumber = null;
		
		if("String".equals(type)) {
			lengthGubun = length.split(",");
		} else if("Number".equals(type)) {
			// 소수점 포함된 자료일 경우
			if(length.indexOf(",") != -1) {
				tempNumber = new BigDecimal(stringBuffer.toString()).setScale(Integer.parseInt(lengthGubun[1]), BigDecimal.ROUND_DOWN);
				stringBuffer = new StringBuffer(tempNumber.toString());
			}
		} else {
			throw new Exception(type.toString() + " 해당 항목의 타입이 존재하지 않습니다.");
		}
		
		return stringBuffer.toString();
	}
	
	/**
	 * 중계사업자별 파일 레이아웃 가져오기
	 * 
	 * @param host				- 중계사업자
	 * @param fileLayoutType	- 레이아웃 타입
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> getFileLayOutType(String host, String fileLayoutType) throws Exception {
		List<Map<String, Object>> layout = null;
		
		if("FILE_LAY_OUT".equals(fileLayoutType)) {
			layout = new ArrayList<Map<String,Object>>();
			
			// Name, Type, Length, Desc
			setLayoutItem(layout, new String[]{"CODE"				, "String"	, "10"	, "기초코드"});
			setLayoutItem(layout, new String[]{"ETC2"				, "String"	, "10"	, "기초코드2"});
			setLayoutItem(layout, new String[]{"CODE_NAME"			, "String"	, "20"	, "기초코드"});
			setLayoutItem(layout, new String[]{"ETC10"				, "String"	, "20"	, "EDI 중개사업자(수출)"});
			setLayoutItem(layout, new String[]{"ETC11"				, "String"	, "20"	, "EDI 중개사업자(수입)"});
			setLayoutItem(layout, new String[]{"ETC9"				, "String"	, "20"	, "MASTER 하기장소"});
			setLayoutItem(layout, new String[]{"ETC12"				, "String"	, "20"	, "중개사업자(FWB)"});
			setLayoutItem(layout, new String[]{"ETC13"				, "String"	, "20"	, "항공사정정(KTNET식별자)"});
			setLayoutItem(layout, new String[]{"REMARKS"			, "String"	, "100"	, "비고"});
			setLayoutItem(layout, new String[]{"TEST_AMOUNT1"		, "Number"	, "15"	, "정수금액"});
			setLayoutItem(layout, new String[]{"TEST_AMOUNT2"		, "Number"	, "15,2", "소수금액"});
			
			// 마지막 라인은 파일 레이아웃 항목이 아닌 VO클래스명을 적는다.
			setLayoutItem(layout, new String[]{"TestVO"		, "Class"	, "", "VO클래스"});
		} else if("2".equals(fileLayoutType)) {
			throw new Exception(fileLayoutType + "해당 파일 레이아웃이 없습니다.");
		}
		
		return layout;
	}
	
	/**
	 * 레이아웃 항목 생성
	 * @param layout			- 레이아웃
	 * @param layOutItem	- 레이아웃 항목
	 * @throws Exception
	 */
	public static void setLayoutItem(List<Map<String, Object>> layout, String layOutItem[]) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("Name", layOutItem[0]);
		map.put("Type", layOutItem[1]);
		map.put("Length", layOutItem[2]);
		map.put("Desc", layOutItem[3]);
		
		layout.add(map);
	}
	
	/**
	 * 레이아웃과 VO 클래스의 메소드 매핑 확인
	 * @param methodName		- 매소드명
	 * @param voItemList			- 레이아웃 항목 리스트
	 * @return
	 * @throws Exception
	 */
	public static boolean isVoFieldName(String methodName, List<Map<String, Object>> voItemList) throws Exception {
		boolean isFieldName = false;
        
		Map<String, Object> layOutItem = null;
		
		// voItemList.size() 에 -1 이유는 마지막 항목 값이 VO클래스 명이기 때문 입니다.
        for (int i = 0; i < voItemList.size() - 1; i++) {
        	layOutItem = voItemList.get(i);
        	
        	if("get".equals(methodName.substring(0, 3))) {
        		if(methodName.equals("get"+(ConvertUtil.converToElementNameForVO(Util.null2str(layOutItem.get("Name")))))) {
            		isFieldName = true;
            		break;
            	}
        	} else if("set".equals(methodName.substring(0, 3))) {
        		if(methodName.equals("set"+(ConvertUtil.converToElementNameForVO(Util.null2str(layOutItem.get("Name")))))) {
            		isFieldName = true;
            		break;
            	}
        	}
        }
        
        if(!isFieldName) {
    		throw new Exception("파일 레이아웃 또는 VO 에 [" + methodName + "] 해당 항목 명 또는 함수가 없습니다.");
        }
        
        return isFieldName;
	}
	
	/**
	 * 파일에서 읽은 문자열을 레이아웃에 맞춰서 매핑 하기
	 * 
	 * @param host			- 중계사업자
	 * @param layOutType	- 파일 레이아웃
	 * @param lineData		- 문자열 데이터
	 * @param vo				- VO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object setVoMapping(String host, String layOutType, String lineData, Class vo) throws Exception {
		///////////////////////////////////////////////////
		/////////////////// 작 업 중   //////////////////
		///////////////////////////////////////////////////
		List<Map<String, Object>> voItemList = getFileLayOutType(host, layOutType);
		Map<String, Object> layOutItem = null;
		
		Class voClass = vo;									// VO 클래스 지정
        Object voObject = voClass.newInstance();		// VO 클래스 생성
        
        Method methodList[] = voClass.getDeclaredMethods();	// VO 클래스의 메소드 리스트
		
        // VO 필드명 존재 유무 확인
        for(int i = 0; i < methodList.length; ++i) {
        	isVoFieldName(methodList[i].getName(), voItemList);
        }
		///////////////////////////////////////////////////
		/////////////////// 작 업 중   //////////////////
		///////////////////////////////////////////////////
        
		Method method = null;
		Class[] methodParamClass = null;		// 메서드의 인자값 타입
        Object[] methodParamObject = null;	// 메소드에 인자 값
        
        String itemValue = null;					// 레이아웃 항목 값
        String lengthGubun[] = null;
		String type = null;
		String length = null;
		
		int startIndex = 1;
		int endIndex = 0;
		
		// voItemList.size() - 1 이유는 마지막 항목 값이 VO클래스 명이기 때문임.
        for(int n = 0; n < voItemList.size() - 1; ++n) {
        	layOutItem = voItemList.get(n);
        	
        	for (int i = 0; i < methodList.length; i++) {
            	if(("set" + ConvertUtil.converToElementNameForVO(Util.null2str(layOutItem.get("Name")))).equals(methodList[i].getName())) {
            		type = Util.null2str(layOutItem.get("Type"));			// String, Number 타입 값
            		length = Util.null2str(layOutItem.get("Length"));		// [20], [15], [1], [15,2] 형태의 길이 값
            		
            		lengthGubun = length.split(",");							// 15,2 형태 값을 15와 2로 나눠서 담기
            		
            		endIndex += Util.null2int(lengthGubun[0]);
            		itemValue = getFileReadData(lineData, type, lengthGubun[0], startIndex, endIndex);	// 레이아웃 항목의 값 가져오기
            		startIndex += Util.null2int(lengthGubun[0]);
            		
            		// 메소드 인자값 확인
                	methodParamClass =methodList[i].getParameterTypes();
                	
                	// 인자값 세팅
                	methodParamObject = new Object[]{itemValue.trim()};
                	
                	// 메소드 찾기
                	method = voClass.getMethod(methodList[i].getName(), methodParamClass);
                	
                	// 메소드 실행
            		method.invoke(voObject, methodParamObject);
            		
            		break;
            	}
            }	// end of inner for-loop.
        	
        }
		
        return voObject;
	}
	
	/**
	 * VO 값을 레이아웃 항목에 맞춰서 문자열로 반환
	 * 
	 * @param host			- 중계사업자
	 * @param layOutType	- 파일 레이아웃
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getVoDatas(String host, String layOutType, Object vo) throws Exception {
		StringBuffer lineData = new StringBuffer();
		
		///////////////////////////////////////////////////
		/////////////////// 작 업 중   //////////////////
		///////////////////////////////////////////////////
		Class voClass = vo.getClass();									// VO 클래스 지정
		Object voObject = vo;												// VO 클래스 생성
		Method methodList[] = voClass.getDeclaredMethods();	// VO 클래스의 메소드 리스트
		
		List<Map<String, Object>> voItemList = getFileLayOutType(host, layOutType);
		Map<String, Object> layOutItem = null;
		
		// 레이아웃과의 VO 일치여부 확인
		String voName = Util.null2str(voItemList.get(voItemList.size() - 1).get("Name"));	// VO 클래스명 가져오기
		if(!(voClass.getSimpleName()).equals(voName)) {
			throw new Exception("[" + voClass.getSimpleName() +"] VO가 레이아웃의 [" + voName + "] VO 와 일치하지 않습니다.");
		}
		
		// VO 필드명 존재 유무 확인
		for(int i = 0; i < methodList.length; ++i) {
        	isVoFieldName(methodList[i].getName(), voItemList);
        }
		///////////////////////////////////////////////////
		/////////////////// 작 업 중   //////////////////
		///////////////////////////////////////////////////
		
		Method method = null;
        Object returnObject = null;				// 리턴값
        
		String type = null;
		String lengths = null;
		
		// voItemList.size() - 1 이유는 마지막 항목 값이 VO클래스 명이기 때문임.
        for(int n = 0; n < voItemList.size() - 1; ++n) {
        	layOutItem = voItemList.get(n);
        	
        	for (int i = 0; i < methodList.length; i++) {
            	if(("get" + ConvertUtil.converToElementNameForVO(Util.null2str(layOutItem.get("Name")))).equals(methodList[i].getName())) {
            		type = Util.null2str(layOutItem.get("Type"));			// String, Number 타입 값
            		lengths = Util.null2str(layOutItem.get("Length"));		// [20], [15], [1], [15,2] 형태의 길이 값
            		
            		method = voClass.getMethod(methodList[i].getName());
    	    		returnObject = method.invoke(voObject);
    	    		
    	    		lineData.append(getFileWriteData(Util.null2str(returnObject), type, lengths));
            		break;
            	}
            }	// end of inner for-loop.
        	
        }
        
        return lineData.toString();
	}
	
	/**
	 * VO에 담겨진 항목들 출력
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getVoToString(Object vo) throws Exception {
		Class voClass = vo.getClass();									// VO 클래스 지정
		Object voObject = vo;												// VO 클래스 생성
		Method methodList[] = voClass.getDeclaredMethods();	// VO 클래스의 메소드 리스트
		
		Method method = null;
        
        StringBuffer stringBuffer = new StringBuffer();
        
		// VO에 담겨진 값 출력하기
		for (int i = 0; i < methodList.length; ++i) {
			if("get".equals(methodList[i].getName().substring(0, 3))) {
				method = voClass.getMethod(methodList[i].getName());
				
				stringBuffer.append(methodList[i].getName());
				stringBuffer.append(" : ");
				stringBuffer.append(method.invoke(voObject));
				stringBuffer.append("\r\n");
			}
		}
		
		return stringBuffer.toString();
	}
	
	//주소문자열을 받아서 len이내 주소리스트 리턴
	public static List<String> getSplitAddress(String strAddr, int len) {

		List<String> addrList;
	    if(strAddr.contains(",")) addrList = GlobalUtil.getStringToList(strAddr, len, ",");
		else if(strAddr.contains(" ")) addrList = GlobalUtil.getStringToList(strAddr, len, " ");
		else addrList = GlobalUtil.getStringToList(strAddr, len, "");
	    
    	return addrList;
	}

	//문자열을 받아서 len이내 지정된 문자로 구분하여 문자열리스트 리턴
	public static List<String> getStringToList(String str, int len, String seperator) {
		List<String> rtnList = new ArrayList<String>();
		String[] tempArr;
		String convertStr = "";
		String beforeConvertStr = "";
		boolean isTooLong = false;
		
		str = str.replaceAll(System.getProperty("line.separator"), " ").trim();
		//가져온 문자열이 원하는 길이보다 긴경우
		if(getByteLength(str) > len) {
			//구분자로 문자열 분리
			tempArr = str.split(seperator);
			//분리한 문자열 중에서 원하는 길이보다 긴경우 판단
			for(int i=0;i<tempArr.length;i++) {
				if(getByteLength(tempArr[i]) > len) isTooLong = true;
				//System.out.println("tempArr[i]) : " + tempArr[i]);
			}
			if(isTooLong) {
				seperator = "";
				tempArr = str.split(seperator);
			}
			
			//System.out.println("tempArr.length : " + tempArr.length+" / isTooLong : " + isTooLong);
			
			for(int i=0;i<tempArr.length;i++) {
				
				if(convertStr.length() == 0) convertStr = tempArr[i];
				else {
					beforeConvertStr = convertStr;
					convertStr += seperator + tempArr[i];
				}
								
				if(getByteLength(convertStr) >= len){
					rtnList.add(beforeConvertStr.trim());
					convertStr = tempArr[i];					
					if (i == tempArr.length-1) {
						rtnList.add(convertStr.trim());
					}					
				} else if (i == tempArr.length-1){
					rtnList.add(convertStr.trim());
				}
			}
			
		//가져온 문자열이 원하는 길이보다 짧은경우
		} else {
			rtnList.add(str.trim());
		}
		return rtnList;				
	}
	
	/**
	 * Unicode 문자 포함 여부 확인
	 * @param str
	 * @return
	 */
	public static boolean containsUnicode(String str) {
	    for(int i = 0 ; i < str.length() ; i++) {
	        char ch = str.charAt(i);
	        Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
	        
	        if(UnicodeBlock.HANGUL_SYLLABLES.equals(unicodeBlock) ||  
		        	UnicodeBlock.HANGUL_COMPATIBILITY_JAMO.equals(unicodeBlock) || 
		            UnicodeBlock.HANGUL_JAMO.equals(unicodeBlock) ||
		            UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(unicodeBlock) ||
		            UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A.equals(unicodeBlock) ||
		            UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B.equals(unicodeBlock) ||
		            UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS.equals(unicodeBlock) ||
		            UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT.equals(unicodeBlock) ||
		            UnicodeBlock.HIRAGANA.equals(unicodeBlock) ||
		            UnicodeBlock.KATAKANA.equals(unicodeBlock) ||
		            UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS.equals(unicodeBlock) ||
		            UnicodeBlock.BASIC_LATIN.equals(unicodeBlock) ||
		            UnicodeBlock.LATIN_EXTENDED_B.equals(unicodeBlock) ||
		            UnicodeBlock.LATIN_EXTENDED_ADDITIONAL.equals(unicodeBlock)
	                )
	            return true;
	    }
	    
	    return false;
	}
	
	public static int getDifMonths(String startDate, String endDate, String displayType) {
		int startYear = Integer.parseInt(startDate.substring(0,4));
		int startMonth = Integer.parseInt(startDate.substring(4,6));

		int endYear = Integer.parseInt(endDate.substring(0,4));
		int endMonth = Integer.parseInt(endDate.substring(4,6));

		return (endYear - startYear)* 12 + (endMonth - startMonth);
	}
	
	/**
	 * 도메인 주소 추출하기
	 * @param url
	 * @return
	 * @throws URISyntaxException
	 */
	public static String extractUrlParts(String url) throws URISyntaxException {
		URI uri = new URI(url);
	    String domain = uri.getHost();
	    return domain.startsWith("www.") ? domain.substring(4) : domain;
	}
	
	/**
	 * Parmater를 Binding한 Query를 출력(Debug Level에서만 작동)
	 * @param queryId
	 * @param query
	 * @param params
	 * 
	 * 호출방법
	 * GlobalUtil.logQueryBindParameter("codeDefineList", query, search);
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void logQueryBindParameter(String queryId, String query, Object params) {
		// Log4j가 Debug Mode일 경우에만 수행.
		if(logger.isDebugEnabled()) {
			if(params instanceof Map) {
				Map<String, Object> map = (Map<String, Object>)params;
				
				// 같은 이름으로 시작하는 변수일 경우가 있기 때문에
				// 내림차순으로 재정렬하여 변수명이 가장 긴 것 부터 처리하도록 함.
		        TreeMap<String, Object> treeMapReverse = new TreeMap<String, Object>(Collections.reverseOrder());
		        treeMapReverse.putAll(map);
		 
		        String keyAttribute = null;
		        
		        Iterator itr = treeMapReverse.keySet().iterator();
		        while(itr.hasNext()){
		            keyAttribute = (String) itr.next();
		            
		            if(map.get(keyAttribute) instanceof String) {
		            	if("".equals(Util.null2str(map.get(keyAttribute)))) {
		            		query = query.replaceAll(":" + keyAttribute, String.valueOf(map.get(keyAttribute)));
		            	} else {
		            		query = query.replaceAll(":" + keyAttribute, "'" + String.valueOf(map.get(keyAttribute)) + "'");
		            	}
		            } else {
		            	query = query.replaceAll(":" + keyAttribute, String.valueOf(map.get(keyAttribute)));
		            }
		        }
		        
		        logger.debug("\n" + queryId + " ================================== :\n" + query);
		        
			} else if(params instanceof Map[]) {
				Map<String, Object>[] map = (Map<String, Object>[])params;

				logger.debug("\n" + queryId + " ================================== :\n");
				
				for(int i = 0; i < map.length; i++) {
					String newQuery = query;
			        String keyAttribute = null;
			        
					// 같은 이름으로 시작하는 변수일 경우가 있기 때문에
					// 내림차순으로 재정렬하여 변수명이 가장 긴 것 부터 처리하도록 함.
			        TreeMap<String, Object> treeMapReverse = new TreeMap<String, Object>(Collections.reverseOrder());
			        treeMapReverse.putAll(map[i]);
			        
			        Iterator itr = treeMapReverse.keySet().iterator();
			        while(itr.hasNext()) {
			            keyAttribute = (String) itr.next();
			            
			            if(map[i].get(keyAttribute) instanceof String) {
			            	if("".equals(Util.null2str(map[i].get(keyAttribute)))) {
			            		newQuery = newQuery.replaceAll(":" + keyAttribute, String.valueOf(map[i].get(keyAttribute)) + "'");
			            	} else {
			            		newQuery = newQuery.replaceAll(":" + keyAttribute, "'" + String.valueOf(map[i].get(keyAttribute)) + "'");
			            	}
			            } else {
			            	newQuery = newQuery.replaceAll(":" + keyAttribute, String.valueOf(map[i].get(keyAttribute)));
			            }
			        }
			        
			        logger.debug("No " + (i+1) + " >>>>\n" + newQuery);
				}
			}
		}
	}
	
	//Object => Map
	public static Map ConverObjectToMap(Object obj){
		try {
			//Field[] fields = obj.getClass().getFields(); //private field는 나오지 않음.
			Field[] fields = obj.getClass().getDeclaredFields();
			Map resultMap = new HashMap();
			for(int i=0; i<=fields.length-1;i++){
				fields[i].setAccessible(true);
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
	
	//Map => Object
	public static Object convertMapToObject(Map map, Object objClass){
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
	

	@SuppressWarnings("static-access")
	public static Map<String, Object> fileUpload(MultipartFile mFile, String propSaveDir, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap();
        if (mFile != null && mFile.getSize() > 0) {
			String saveFileName = mFile.getOriginalFilename();
			
	        if (!saveFileName.equals("")) {
	        	String saveDir = Prop.getMessage(propSaveDir);
	        	// Directory 유무에 따라 폴더 생성
	    		GlobalUtil u = new GlobalUtil();
	    		
	    		u.directoryConfirmAndMake(saveDir);
	    		saveFileName = saveDir +"/"+ saveFileName;
	            
	            File file = new File(saveFileName);
	            OutputStream outputStream = new FileOutputStream(file);
	            
	            FileCopyUtils.copy(mFile.getInputStream(), outputStream);
	            outputStream.close();
	            
	            map.put("FILE_BYTES", mFile.getBytes());
	            map.put("FILE_SIZE",  mFile.getSize());
	            map.put("FILE_NAME", mFile.getOriginalFilename());
				
	            // 파일 처리 후 원본 파일을 서버에서 삭제
	            //file.delete();
	        }
        }        
        return map;
	}	
	
	@SuppressWarnings("static-access")
	public static Map<String, Object> pnsFileUpload(MultipartFile mFile, String propSaveDir, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap();
        if (mFile != null && mFile.getSize() > 0) {
			String saveFileName = mFile.getOriginalFilename();
			
	        if (!saveFileName.equals("")) {
	        	String saveDir = "";
//	        			Common.getInterfacePath(request);
	        	saveDir = saveDir+Prop.getMessage(propSaveDir);
	        	// Directory 유무에 따라 폴더 생성
	    		GlobalUtil u = new GlobalUtil();
	    		
	    		u.directoryConfirmAndMake(saveDir);
	    		saveFileName = saveDir +"/"+ saveFileName;
	            
	            File file = new File(saveFileName);
	            OutputStream outputStream = new FileOutputStream(file);
	            
	            FileCopyUtils.copy(mFile.getInputStream(), outputStream);
	            outputStream.close();
	            
	            map.put("FILE_BYTES", mFile.getBytes());
	            map.put("FILE_SIZE",  mFile.getSize());
	            map.put("FILE_NAME", mFile.getOriginalFilename());
				
	            // 파일 처리 후 원본 파일을 서버에서 삭제
	            //file.delete();
	        }
        }        
        return map;
	}	
	
	/**
	 * 파일 라인 단위로 읽기 위해 리스트 형으로 반환
	 * @param dir 디렉토리 경로
	 * @param fileName 파일명
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static List<String> fileLoad2List(String dir, String fileName) throws Exception{
	    List<String> lines = new ArrayList<String>();
	    File file = new File(dir, fileName);     
	    if(!file.exists() || !file.isFile()){
	        return null;
	    }
	    BufferedReader br  =  new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
	    String line = null;
	    while( (line = br.readLine()) != null){
	        lines.add(line);
	    }
	    return lines;
	}
	
	 /**
	  * attribute 값을 가져 오기 위한 method
	  * 
	  * @param String  attribute key name 
	  * @return Object attribute obj
	 */
	 public static Object getSessionAttribute(String name) throws Exception {
		 return (Object)RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	 }
	
	/**
	 * 그리드 목록조회와 엑셀다운로드를 하나의 함수로 처리하고 엑셀 다운로드의 방식을 5000건 이상인 경우 여러 번 나누어 쿼리하는 방식
	 * @param obj
	 * @param methodName
	 * @param reqJsonObject
	 * @param resJsonObject
	 * @param fileName
	 * @param request
	 * @throws Exception
	 */
	public static void executeMethodWithExcel(Object obj, String methodName, JSONObject reqJsonObject, JSONObject resJsonObject, String fileName, HttpServletRequest request) throws Exception {
		
		Method[] method = obj.getClass().getDeclaredMethods();
		
		int EXCEL_PAGE_SIZE = 5000;
		int PAGE            = 1;
		boolean isDone      = false;
		
		for(Method m : method){
			// 해당 메소드 명과 일치할 경우
			if(methodName.equals(m.getName()) && m.getGenericParameterTypes().length == 2){
				
				Type[] types = m.getGenericParameterTypes();
				
				if(!(types[0].equals(JSONObject.class) && types[1].equals(JSONObject.class))){
					throw new YhtException("Argument Exception!! needs JSONObject req, JSONObject res");
				}
				
				// 조회모드
				String srchMode = Util.null2str(reqJsonObject.get("srchMode"));
				
				// 엑셀 다운로드인 경우
				if("excel".equals(srchMode)) {
					// 페이징 쿼리로 바꿈
					reqJsonObject.put("srchMode", "server");
					
					reqJsonObject.put("take"     , EXCEL_PAGE_SIZE);
					reqJsonObject.put("pageSize" , EXCEL_PAGE_SIZE);
					reqJsonObject.put("page"     , PAGE);
					
					resJsonObject.put("Excel_CLEAR_YN" , "Y"); // 동일한 object를 사용하기 위해
					resJsonObject.put("Excel_NO_JSON"  , "Y"); // JSON으로 변환 필요 없을 때 사용
					
					m.invoke(obj, reqJsonObject, resJsonObject);
					
					int total = Util.null2int(resJsonObject.get("total"));
					
					// 건수가 페이징 건수보다 많을 때 여러 번 쿼리해서 엑셀에 담음
					if(total <= EXCEL_PAGE_SIZE){
//						Common.getJsonForExcelList(request, fileName, reqJsonObject, resJsonObject);
					}else{
						System.out.println("========== Excel 대용량 처리 ========== (" + total + ")");
						reqJsonObject.put("fileName", fileName);
//						Common.getMakeExcelFile(m, obj, reqJsonObject, resJsonObject, total);
					}
					
					reqJsonObject.put("srchMode", "excel");
				}
				// 그리드 조회인 경우
				else{
					//System.out.println(reqJsonObject);
					m.invoke(obj, reqJsonObject, resJsonObject);
				}
				
				isDone = true;
				break;
			}
		}
		
		// 수행된 메소드가 없음
		if(!isDone){
			throw new YhtException("No executable method!!!!");
		}
	}
}