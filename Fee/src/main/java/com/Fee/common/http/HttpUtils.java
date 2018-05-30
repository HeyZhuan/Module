package com.Fee.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class HttpUtils {
	/**
	 * 获得对应的xx=yy&&aa==bb 字符串 字典排序
	 * 
	 * @param sendReqMap
	 * @return
	 */
	public static String getStrByMapOrderByABC(Map sendReqMap) {
		String sign = "";
		Object[] key_arr = sendReqMap.keySet().toArray();
		Arrays.sort(key_arr);
		for (Object k : key_arr) {
			Object value = sendReqMap.get(k.toString());
			sign += "&" + k + "=" + value;
		}
		if (sign.length() > 0) {
			sign = sign.substring(1, sign.length());
		}
		return sign;
	}
	
	/**
	 * 获取请求的参数信息
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getReqParams(String reqParams) {
		String[] arge = StringUtils.split(reqParams, "&");
		if (arge.length > 0) {
			Map<String, Object> map = new HashMap<>();
			for (String str : arge) {
				String[] param = StringUtils.split(str, "=");
				if (param.length == 2) {
					map.put(param[0], param[1]);
				}
			}
			return map;
		}
		return null;
	}
	
	
	/**
	 * 获取请求的参数信息
	 * @param request
	 * @return
	 */
	public static Map<String,String> getReqParams(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Enumeration<String> paramNames = request.getParameterNames();
		if (paramNames.hasMoreElements()) {
			Map<String,String> map = new HashMap<String,String>();
			String paramName = paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
			while(paramNames.hasMoreElements()){
				paramName = paramNames.nextElement();
				paramValues = request.getParameterValues(paramName);
				if (paramValues.length == 1) {
					String paramValue = paramValues[0];
					if (paramValue.length() != 0) {
						map.put(paramName, paramValue);
					}
				}
			}
			return map;
		}
		return null;
	}
	
	/**
	 * 获取Post数据
	 * @param request
	 * @return
	 */
	public static String getReqPostString(HttpServletRequest request,Logger log){
		log.info("开始接受参数");
		StringBuilder reqData = new StringBuilder();
        String line = null;
        BufferedReader reader = null;
        try{
            reader = request.getReader();
            while((line = reader.readLine()) != null) {
            	reqData.append(line);
            }
            if(reqData.length() <= 0) {
                return null; 
            }
            String strJson = reqData.toString();
            reader.close();
            log.info("获取post数据: strJson=" +strJson);
            return strJson;
        }catch(IOException e) {
        	log.info("获取post数据错误:"+e.getMessage(),e);
        	log.error("获取post数据错误:" + e.getMessage(),e);
        }
        return null;
	}
	
	public static String sendGet(String url,Map<String, String> map) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            for (Map.Entry<String, String> entry: map.entrySet()) {
            	connection.setRequestProperty(entry.getKey(), entry.getValue());
			}
            // 建立实际的连接
            connection.connect();
            
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"GBK"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
	
	
	public static String sendPost(String url, String param,String encode) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("content-type", "application/json");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), encode));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	
	public static String sendPost(String url, String param,Map<String, String> map) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			for (Map.Entry<String, String> entry: map.entrySet()) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
			
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "GBK"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

}
